package com.faz.springbootshiro.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * @author  PZJ
 * @create  2021/4/24 22:02
 * @email   wuchzh0@gmail.com
 * @desc    生成各种字符串
 **/
public class Generate {

    final public static String SALT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+<>?:";
    final public static String UUID = "1234567890abcdefghijklmn1234567890opqrstuvwxyz1234567890";

    /**
     * 生成指定长度的乱序字符串
     * @param length
     * @return
     */
    public static String GenerateString(String type,int length){
        char[] chars = type.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for(int i=0;i<length;i++){
            sbf.append(chars[RandomUtil.randomInt(type.length())]);
        }
        return sbf.toString();
    }
}
