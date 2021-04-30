package com.faz.springbootshiro.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.faz.springbootshiro.dao.SysUserDao;
import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.service.SysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    /**
     * 用户注册
     * @param sysUser
     * @return
     */
    @Override
    public boolean register(SysUser sysUser) {
        //1、设置随机盐
        String salt = RandomUtil.randomString(8);
        sysUser.setSalt(salt);
        //2.生成加密密码
        Md5Hash md5Hash = new Md5Hash(sysUser.getPassword(),salt,16);
        sysUser.setPassword(md5Hash.toHex());
        //添加到数据库
        if(this.sysUserDao.insert(sysUser)>0){
            return true;
        }
        return false;
    }

    @Override
    public SysUser randomOne() {
        int count = sysUserDao.selectCount(new LambdaQueryWrapper<SysUser>());
        return null;
    }
}
