package org.pzz.modules.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.pzz.modules.entity.SysUser;
import org.pzz.modules.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author  PZJ
 * @create  2021/5/24 14:52
 * @email   wuchzh0@gmail.com
 * @desc    system服务对其他模块提供的api接口类
 **/
@RestController
@RequestMapping("/sys/api")
public class SysBaseApiController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/getUserByUsername")
    public SysUser getUserByUsername(@RequestParam("username") String username){
        return sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
    }
}
