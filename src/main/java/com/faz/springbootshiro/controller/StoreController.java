package com.faz.springbootshiro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.service.StoreService;
import com.faz.springbootshiro.entity.Store;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import com.faz.springbootshiro.common.vo.Result;


/**
 * @author  Fazcube
 * @create  2021-05-10
 * @email   wuchzh0@gmail.com
 * @desc    前端控制器
 */

@Slf4j
@RestController
@RequestMapping("/springbootshiro/store")
public class StoreController {

    @Autowired
    public StoreService storeService;

    @GetMapping(value = "/list")
    public Result<?> list(Store store,
                          @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                          @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                          HttpServletRequest req){
        System.out.println(pageNo+"---"+pageSize);
        QueryWrapper<Store> queryWrapper = new QueryWrapper<Store>();
        Page<Store> page = new Page<Store>(pageNo, pageSize);
        IPage<Store> pageList = storeService.page(page,queryWrapper);
        return Result.OK(pageList);
    }

    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Store store){
        if(storeService.save(store)){
            return Result.OK("添加成功！");
        }
        return Result.error("添加失败！");
    }

}
