package org.pzz.modules.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pzz.modules.mapper.SysUserMapper;
import org.pzz.modules.entity.SysUser;
import org.pzz.modules.service.SysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserDao;

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

    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @Override
    public boolean updatePassword(SysUser sysUser,String newPassword) {
        //1、设置随机盐
        String salt = RandomUtil.randomString(8);
        sysUser.setSalt(salt);
        //2.生成加密密码
        Md5Hash md5Hash = new Md5Hash(newPassword,salt,16);
        sysUser.setPassword(md5Hash.toHex());
        if(sysUserDao.updateById(sysUser) != 0){
            return true;
        }
        return false;
    }
}
