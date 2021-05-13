package com.faz.springbootshiro.config.shiro;

import com.faz.springbootshiro.config.jwt.JwtFilter;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
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

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    public static final int USER_INFO_TIME = 3600;

    /**
     * 创建shiroFilter
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        //负责拦截所有请求的类
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义未授权返回页面 不一定要设置为登陆页面 可以设置为无权限等页面
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");

        //配置系统的受限资源和公开资源
        Map<String,String> map = new HashMap<>();

        map.put("/login.jsp","anon");
        map.put("/reg.jsp","anon");

        map.put("/user/login","anon");
        map.put("/user/register","anon");

        //map.put("/springbootshiro/**","anon");

        //创建 加载自定义的 jwtFilter 并取名为 jwt
        Map<String, Filter> filterMap = new HashMap<String,Filter>(1);
        filterMap.put("jwt",new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //所有的资源都需要通过自定义的jwt过滤器认证 自上而下的执行顺序
        map.put("/**","jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * 创建安全管理器
     * @param realm
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(ShiroRealm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);

        //关闭session 不保存用户的登陆状态，保证每次用户操作都要经过filter
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);

        //自定义缓存实现,使用redis
        defaultWebSecurityManager.setCacheManager(redisCacheManager());

        return defaultWebSecurityManager;
    }

    //--------------------------------------------------------------------------------

    /**
     * cacheManager 缓存 使用redis实现
     * 使用shrio-redis插件
     * @return
     */
    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间 秒
        redisCacheManager.setExpire(USER_INFO_TIME);
        return redisCacheManager;
    }

    @Bean("redisManager")
    public RedisManager redisManager() {
        // redis 单机支持，在集群为空，或者集群无机器时候使用 add by jzyadmin@163.com
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(0);
        return redisManager;
    }

    /**
     * 下面的代码是添加注解支持
     * @return
     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        /**
//         * 解决重复代理问题 github#994
//         * 添加前缀判断 不匹配 任何Advisor
//         */
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
//        defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
//        return defaultAdvisorAutoProxyCreator;
//    }

    /**
     * 下面的代码是添加注解支持
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
