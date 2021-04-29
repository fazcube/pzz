package com.faz.springbootshiro.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/list")
    @RequiresRoles("admin")
    public void list(){
        System.out.println("查询订单成功！");
    }
}
