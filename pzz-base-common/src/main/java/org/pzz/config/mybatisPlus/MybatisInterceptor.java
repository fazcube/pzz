package org.pzz.config.mybatisPlus;

import cn.hutool.core.util.ObjectUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.shiro.SecurityUtils;
import org.pzz.common.vo.CommonSysUser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/******************************************************************************************
 * @author  PZJ
 * @create  2021/5/10 23:59
 * @email   wuchzh0@gmail.com
 * @desc    mybatis拦截器，自动注入创建时间和更新时间
 * **
 * @Intercepts注解 标记这个类是一个拦截器
 * @Signature注解 指明该拦截器需要拦截哪一个接口的哪一个方法
 *  type	四种类型接口中的某一个接口，如Executor.class。
 *  method	对应接口中的某一个方法名，比如Executor类的update，query，commit，rollback等方法
 *      update这个拦截器拦截Executor接口的update方法（其实也就是SqlSession的新增，删除，修改操作），所有执行executor的update方法都会被该拦截器拦截到。
 *  args	对应接口中的某一个方法的参数，比如Executor中query方法因为重载原因，有多个，args就是指明参数类型，从而确定是具体哪一个方法。
 ******************************************************************************************/
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
            CommonSysUser user = getUser();
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
                // insert命令下 注入创建时间
                if(field.getName().equals("createTime")){
                    field.setAccessible(true);
                    Object local_createTime = field.get(parameter);
                    field.setAccessible(false);
                    if(ObjectUtil.isEmpty(local_createTime)){
                        field.setAccessible(true);
                        field.set(parameter,new Date());
                        field.setAccessible(false);
                    }
                }
            }
        }
        //如果是update命令类型，执行以下操作
        if(SqlCommandType.UPDATE == sqlCommandType){
            //获取用户信息
            CommonSysUser user = getUser();
            //获取所有参数
            Field[] allFields = null;
            if (parameter instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap<?> p = (MapperMethod.ParamMap<?>) parameter;
                //update-begin-author:scott date:20190729 for:批量更新报错issues/IZA3Q--
                if (p.containsKey("et")) {
                    parameter = p.get("et");
                } else {
                    parameter = p.get("param1");
                }
                //update-end-author:scott date:20190729 for:批量更新报错issues/IZA3Q-

                //update-begin-author:scott date:20190729 for:更新指定字段时报错 issues/#516-
                if (parameter == null) {
                    return invocation.proceed();
                }
                //update-end-author:scott date:20190729 for:更新指定字段时报错 issues/#516-

                allFields = getAllFields(parameter);
            } else {
                allFields = getAllFields(parameter);
            }

            for (Field field : allFields) {
                System.out.println("取出字段信息："+field);
                // insert命令下 注入更新信息的人
                if(field.getName().equals("updateBy")){
                    field.setAccessible(true);// 先把字段的可访问属性设置为true
                    Object local_updateBy = field.get(parameter);
                    field.setAccessible(false);
                    // 如果传入的对象本身就带有createBy的值，那么直接不做操作，否则进行字段赋值
                    if(ObjectUtil.isEmpty(local_updateBy)){
                        field.setAccessible(true);
                        field.set(parameter,user.getUsername()); // 将用户名赋值
                        field.setAccessible(false);
                    }
                }
                // insert命令下 注入更新时间
                if(field.getName().equals("updateTime")){
                    field.setAccessible(true);
                    Object local_updateTime = field.get(parameter);
                    field.setAccessible(false);
                    if(ObjectUtil.isEmpty(local_updateTime)){
                        field.setAccessible(true);
                        field.set(parameter,new Date());
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
    public static CommonSysUser getUser(){
        CommonSysUser sysUser = null;
        try{
            sysUser = SecurityUtils.getSubject().getPrincipal() == null?null : (CommonSysUser) SecurityUtils.getSubject().getPrincipal();
        }catch (Exception e){
            log.error("获取用户信息失败！"+e.getMessage());
        }
        return sysUser;
    }
}
