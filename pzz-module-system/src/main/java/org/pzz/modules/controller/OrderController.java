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

//    public static void sudoku(int[][] yuan){
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//
//                if(yuan[i][j] == 0){
//                    for (int k = 1; k < 10; k++) {
//
//                    }
//                }
//                System.out.print(yuan[i][j]+" ");
//
//            }
//            System.out.println();
//        }
//    }

//    public static void main(String[] args) {
//        int[][] yuan = {
//                {4,0,0,7,0,0,9,0,3},
//                {3,0,0,0,0,0,8,0,0},
//                {0,0,5,0,2,8,0,0,0},
//                {0,0,0,2,4,0,5,0,0},
//                {0,4,0,0,0,5,0,0,0},
//                {0,0,0,8,6,0,4,0,0},
//                {0,0,1,0,7,9,0,0,0},
//                {9,0,0,0,0,0,3,0,0},
//                {8,0,0,5,0,0,2,0,1}
//        };
//        long startTime = System.currentTimeMillis();
//        sudoku(yuan);
//        System.out.println("所用时间："+(System.currentTimeMillis() - startTime));
//    }

}
