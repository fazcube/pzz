package com.faz.springbootshiro.service.impl;

import com.faz.springbootshiro.entity.Store;
import com.faz.springbootshiro.mapper.StoreMapper;
import com.faz.springbootshiro.service.StoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author  Fazcube
 * @create  2021-05-10
 * @email   wuchzh0@gmail.com
 * @desc    接口实现类
 */

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

}
