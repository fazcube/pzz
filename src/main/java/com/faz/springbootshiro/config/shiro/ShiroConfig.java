package com.faz.springbootshiro.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  PZJ
 * @create  2021/4/22 22:58
 * @email   wuchzh0@gmail.com
 * @desc    shiro框架相关配置类
 **/
@Configuration
public class ShiroConfig {

    //1、创建shiroFilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        //负责拦截所有请求的类
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //自定义未授权返回页面
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        //配置系统的受限资源和公开资源
        Map<String,String> map = new HashMap<>();

        //map.put("/*.jsp","anon");
        map.put("/user/login","anon");
        map.put("/user/register","anon");
        //所有的页面都需要认证
        map.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }
    //2、创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        //自定义缓存实现,使用redis
        defaultWebSecurityManager.setCacheManager(redisCacheManager());

        return defaultWebSecurityManager;
    }

    //3、创建自定义realm
    @Bean
    public Realm getRealm(){
        MyRealm myRealm = new MyRealm();
        //告诉realm使用的是md5认证并且循环16次
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(16);
        myRealm.setCredentialsMatcher(credentialsMatcher);

        return myRealm;
    }

    //----------------

    /**
     * cacheManager 缓存 使用redis实现
     * 使用shrio-redis插件
     * @return
     */
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());

        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间 秒
        redisCacheManager.setExpire(1800);
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager() {
        // redis 单机支持，在集群为空，或者集群无机器时候使用 add by jzyadmin@163.com
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        redisManager.setTimeout(0);
        return redisManager;
    }

    /**
     * 下面的代码是添加注解支持
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        /**
         * 解决重复代理问题 github#994
         * 添加前缀判断 不匹配 任何Advisor
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 使授权注解起作用
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * Shiro生命周期处理器
     *
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
