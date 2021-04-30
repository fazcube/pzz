package com.faz.springbootshiro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.faz.springbootshiro.config.redis.RedisUtil;
import com.faz.springbootshiro.entity.Result;
import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.service.SysUserService;
import com.faz.springbootshiro.config.jwt.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/login")
    public Result<?> login(String username, String password){
        System.out.println(username+"-"+password);
        //验证账户名和密码是否有效
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
        if(user==null){
            return Result.error("用户不存在！");
        }
        //获取password的加密md5
        String md5 = new Md5Hash(password,user.getSalt(),16).toHex();
        //对比数据库中的密码
        if(!md5.equals(user.getPassword())){
            return Result.error("用户名或密码错误！");
        }
        //创建token
        String token = JwtUtil.sign(username,md5);
        // 设置token缓存有效时间
        redisUtil.set(JwtUtil.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(JwtUtil.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        Map<String,Object> map = new HashMap<>();
        map.put("token",token);

//        Subject subject = SecurityUtils.getSubject();
//        try {
//            //基于session的登录方式
//            //subject.login(new UsernamePasswordToken(username,password));
//            //基于token的登录方式
//            subject.login(new JwtToken(token));
//            return Result.OK(token);
//        }catch (IncorrectCredentialsException e){
//            System.out.println("密码错误！");
//            return Result.error("密码错误！");
//        }catch (UnknownAccountException e){
//            System.out.println("用户不存在！");
//            return Result.error("用户不存在！");
//        }catch (Exception e){
//            e.printStackTrace();
//            return Result.error(e.getMessage());
//        }

        return Result.OK("登录成功！",map);
    }


    @RequestMapping("/register")
    public String register(SysUser user){
        if(sysUserService.register(user)){
            return "redirect:/login.jsp";
        }
        return "redirect:/reg.jsp";
    }
}
