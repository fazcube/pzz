package com.faz.springbootshiro.config.mybatisPlus;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * @author  PZJ
 * @create  2021/5/10 23:59
 * @email   wuchzh0@gmail.com
 * @desc    mybatis拦截器，自动注入创建时间和更新时间
 **/

public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
