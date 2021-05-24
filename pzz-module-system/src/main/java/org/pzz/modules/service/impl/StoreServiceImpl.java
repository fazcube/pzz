package org.pzz.modules.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pzz.modules.entity.Store;
import org.pzz.modules.mapper.StoreMapper;
import org.pzz.modules.service.StoreService;
import org.springframework.stereotype.Service;

/**
 * @author  Fazcube
 * @create  2021-05-15
 * @email   wuchzh0@gmail.com
 * @desc    门店表接口实现类
 */

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

}
