package com.faz.springbootshiro.controller;

import com.faz.springbootshiro.entity.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/list")
    @RequiresRoles("admin")
    public Result<?> list(){
        return Result.OK("查询订单成功！");
    }

    @RequestMapping("/pay")
    @RequiresPermissions("user:find:*")
    public Result<?> pay(){
        System.out.println("进入方法");
        Subject subject = SecurityUtils.getSubject();
        return Result.OK(subject.getPrincipal());
    }

}
