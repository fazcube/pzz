package com.faz.springbootshiro.stringTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.service.SysUserService;
import com.faz.springbootshiro.utils.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringTest {

    @Autowired
    private SysUserService sysUserService;

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(Generate.GenerateString(Generate.UUID,32));
//        }
        //String [] strs = new String[]{"index","test"};
        //SplicingPath(strs);
        String str1 = "/test/2/list";
        String str2 = "list";
        //System.out.println(str1.contains(str2));
        System.out.println(SplicingPath(str1, str2));
    }

    // index test  => /index/test
//    public static String SplicingPath(String[] paths){
//        String connect = "/";
//        StringBuffer url = new StringBuffer();
//        for (int i=0;i<paths.length;i++) {
//            url.append(connect);
//            if(paths.length==1){
//                url.append(paths[i]);
//            }else{
//                if(i+1<=paths.length && paths[i].equals(paths[i+1])){
//                    continue;
//                }
//            }
//        }
//        return url.toString();
//
//    }

    public static String SplicingPath(String path1,String path2){
        String connect = "/";
        StringBuffer url = new StringBuffer();
        String[] split = path1.split("/");
        String lastStr = "";

        url.append(connect);

        if(split.length!=0){
            lastStr = connect + split[split.length-1];
            url.append(path1);
        }
        if(!lastStr.contains(path2)){
            url.append(connect);
            url.append(path2);
        }
        return url.toString().replace("//","/");

    }

}
