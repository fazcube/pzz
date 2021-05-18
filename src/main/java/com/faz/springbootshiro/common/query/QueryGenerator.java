package com.faz.springbootshiro.common.query;

import cn.hutool.Hutool;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.faz.springbootshiro.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author  PZJ
 * @create  2021/5/11 21:14
 * @email   wuchzh0@gmail.com
 * @desc    查询条件构造
 **/
@Slf4j
public class QueryGenerator {


    public static <T> QueryWrapper<T> initQueryWrapper(T obj){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        AssemblyParam(queryWrapper,obj);
        return queryWrapper;
    }

    /**
     * 组装mybatis-plus查询条件
     * @param queryWrapper
     * @param obj
     */
    public static void AssemblyParam(QueryWrapper<?> queryWrapper ,Object obj){
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(obj);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();// 获取字段名
            String type = propertyDescriptor.getPropertyType().toString();// 获取字段类型
            try {
                //如果本次循环的字段是一个无效字段，则跳出本次循环
                //isReadable 如果指定的属性名标识指定bean上的可读属性，则返回true；否则，返回false。
                if(ConvertUtils.judgedIsUselessField(name) || !PropertyUtils.isReadable(obj,name)){
                    continue;
                }
                Object value = PropertyUtils.getSimpleProperty(obj, name);

                if(ObjectUtil.isNotEmpty(value)){
                    System.out.println(name+"--"+type+"--"+value);
//                    Field field = obj.getClass().getDeclaredField(name);
//                    if (field != null) {
//                        TableField tableField = field.getAnnotation(TableField.class);
//                        System.out.println(tableField);
//                        if (tableField != null && tableField.exist() == false) {
//                            continue;
//                        }
//                    }
                    //将字段的驼峰写法转换成数据库相应的写法 如createBy => create_by
                    queryWrapper.eq(ConvertUtils.camelToUnderline(name),value);
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
    }

//    public static void main(String[] args) {
//        Store store = new Store();
//        store.setId("10086");
//        store.setStoreName("深圳福田");
//        store.setCount("");
//        initQueryWrapper(store);
//    }
}
