package com.faz.springbootshiro.controller;

import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.service.SysUserService;
import com.faz.springbootshiro.utils.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/login")
    public String login(String username,String password){
        System.out.println(username+"-"+password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username,password));
            return "redirect:/index.jsp";
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误！");
            return "redirect:/login.jsp";
        }catch (UnknownAccountException e){
            System.out.println("用户不存在！");
            return "redirect:/login.jsp";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/login.jsp";
        }
    }

    @RequestMapping("/register")
    public String register(SysUser user){
        if(sysUserService.register(user)){
            return "redirect:/login.jsp";
        }
        return "redirect:/reg.jsp";
    }
}
