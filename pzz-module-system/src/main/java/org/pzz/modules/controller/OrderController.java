package org.pzz.modules.controller;

import org.pzz.common.api.SysBaseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
//
//    @RequestMapping("/list")
//    @RequiresRoles("admin")
//    public Result<?> list(){
//        return Result.OK("查询订单成功！");
//    }
//
//    @RequestMapping("/pay")
//    @RequiresPermissions("user:find:*")
//    public Result<?> pay(){
//        System.out.println("进入方法");
//        Subject subject = SecurityUtils.getSubject();
//        return Result.OK(subject.getPrincipal());
//    }

    @Autowired
    private SysBaseAPI sysBaseAPI;

    @PostMapping("/test/{name}")
    public String testRest(@PathVariable("name") String name){
        System.out.println(name);
        System.out.println(sysBaseAPI.getUserByUsername(name));
        return "ok";
    }

}
