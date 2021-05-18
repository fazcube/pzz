package com.faz.springbootshiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faz.springbootshiro.config.redis.RedisUtil;
import com.faz.springbootshiro.common.vo.Result;
import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.service.SysUserService;
import com.faz.springbootshiro.config.jwt.JwtUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @CrossOrigin
    public Result<JSONObject> login(@RequestBody SysUser sysUser){
        Result<JSONObject> result = new Result<JSONObject>();
        System.out.println(sysUser.getUsername()+"-"+sysUser.getPassword());
        //验证账户名和密码是否有效
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,sysUser.getUsername()));
        if(user==null){
            result.error500("用户不存在！");
            return result;
        }
        //获取password的加密md5
        String md5 = new Md5Hash(sysUser.getPassword(),user.getSalt(),16).toHex();
        //对比数据库中的密码
        if(!md5.equals(user.getPassword())){
            result.error500("用户名或密码错误！");
            return result;
        }
        //创建token
        String token = JwtUtil.sign(sysUser.getUsername(),md5);
        // 设置token缓存有效时间
        redisUtil.set(JwtUtil.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(JwtUtil.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        JSONObject json = new JSONObject();
        json.put("token",token);

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
        json.put("userInfo",user);
        result.success("登陆成功！");
        result.setResult(json);
        return result;
    }


    @RequestMapping("/register")
    public String register(SysUser user){
        if(sysUserService.register(user)){
            return "redirect:/login.jsp";
        }
        return "redirect:/reg.jsp";
    }

    @GetMapping(value = "/list")
    public Result<?> list(SysUser sysUser,
                          @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                          @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                          HttpServletRequest req){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        Page<SysUser> page = new Page<>(pageNo, pageSize);
        IPage<SysUser> pageList = sysUserService.page(page,queryWrapper);
        return Result.OK(pageList);
    }

    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody SysUser sysUser){
        if(sysUserService.save(sysUser)){
            return Result.OK("添加成功！");
        }
        return Result.error("添加失败！");
    }
}