package org.pzz.modules.controller;

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

    @PostMapping("/test/{id}")
    public String testRest(@PathVariable("id") String id){
        System.out.println(id);
        return id;
    }

}
