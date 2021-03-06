package org.pzz.modules.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.pzz.common.vo.Result;
import org.pzz.config.jwt.JwtUtil;
import org.pzz.config.redis.RedisUtil;
import org.pzz.modules.entity.SysUser;
import org.pzz.modules.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户表接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "用户表-用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
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

        json.put("userInfo",user);
        result.success("登陆成功！");
        result.setResult(json);
        return result;
    }


    @ApiOperation(value = "用户表-用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(SysUser user){
        if(sysUserService.register(user)){
            return "redirect:/login.jsp";
        }
        return "redirect:/reg.jsp";
    }

    @PostMapping("/updatePassword")
    public Result<?> updatePassword(@RequestBody JSONObject json){
        String oldPassword = json.getString("oldPassword");
        String newPassword = json.getString("newPassword");
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //获取password的加密md5
        String md5 = new Md5Hash(oldPassword,user.getSalt(),16).toHex();
        //对比数据库中的密码
        if(!md5.equals(user.getPassword())){
            return Result.error("输入旧密码有误");
        }
        sysUserService.updatePassword(user,newPassword);
        return Result.OK("修改成功！");
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