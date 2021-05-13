package com.faz.springbootshiro.config.mybatisPlus;

import cn.hutool.core.util.ObjectUtil;
import com.faz.springbootshiro.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author  PZJ
 * @create  2021/5/10 23:59
 * @email   wuchzh0@gmail.com
 * @desc    mybatis拦截器，自动注入创建时间和更新时间
 *
 * @Intercepts注解 标记这个类是一个拦截器
 * @Signature注解 指明该拦截器需要拦截哪一个接口的哪一个方法
 *  type	四种类型接口中的某一个接口，如Executor.class。
 *  method	对应接口中的某一个方法名，比如Executor的query方法。
 *  args	对应接口中的某一个方法的参数，比如Executor中query方法因为重载原因，有多个，args就是指明参数类型，从而确定是具体哪一个方法。
 **/
@Slf4j
@Component
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class})})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();
        log.info("sqlId:"+sqlId);
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();// 获取sql的命令类型 如insert、update等等。
        log.info("sqlCommandType:"+sqlCommandType);
        Object parameter = invocation.getArgs()[1];
        log.info("parameter:"+parameter);
        System.out.println("取到的用户信息："+getUser());
        if(parameter == null){
            //如果parameter为空，代表没有，则放行
            System.out.println("没有参数！");
            return invocation.proceed();
        }
        //如果是insert命令类型，执行以下操作
        if(SqlCommandType.INSERT == sqlCommandType){
            //获取用户信息
            SysUser user = getUser();
            Field[] allFields = getAllFields(parameter);

            for (Field field : allFields) {
                // insert命令下 注入创建人
                if(field.getName().equals("createBy")){
                    // 取出的字段信息是 ：private java.lang.String com.faz.***.entity.***.createBy
                    //System.out.println("取出字段信息："+field);
                    field.setAccessible(true);// 先把字段的可访问属性设置为true
                    Object local_createBy = field.get(parameter); // 进入此时 field已经是createBy的那个字段，从对象中获取有无存在createBy字段的值
                    //System.out.println("local_createBy:"+local_createBy);
                    field.setAccessible(false);
                    // 如果传入的对象本身就带有createBy的值，那么直接不做操作，否则进行字段赋值
                    if(ObjectUtil.isEmpty(local_createBy)){
                        field.setAccessible(true);
                        field.set(parameter,user.getUsername()); // 将用户名赋值
                        field.setAccessible(false);
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        //调用插件
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 获取用户信息
     * @return
     */
    public static SysUser getUser(){
        SysUser sysUser = null;
        try{
            sysUser = SecurityUtils.getSubject().getPrincipal() == null?null : (SysUser) SecurityUtils.getSubject().getPrincipal();
        }catch (Exception e){
            log.error("获取用户信息失败！"+e.getMessage());
        }
        return sysUser;
    }
}
