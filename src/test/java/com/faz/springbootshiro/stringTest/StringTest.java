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
    }

}
