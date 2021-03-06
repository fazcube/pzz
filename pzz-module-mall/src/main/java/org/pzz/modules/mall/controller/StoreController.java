package org.pzz.modules.mall.controller;

import org.pzz.modules.mall.service.StoreService;
import org.pzz.modules.mall.entity.Store;
import org.pzz.common.vo.Result;
import org.pzz.common.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * @author  Fazcube
 * @create  2021-06-07
 * @email   wuchzh0@gmail.com
 * @desc    门店表前端控制器
 */

@Slf4j
@Api(tags = "门店表")
@RestController
@RequestMapping("/mall/store")
public class StoreController {

    @Autowired
    public StoreService storeService;

    /**
     * 分页列表查询
     *
     * @param store 查询参数
     * @param pageNo 页码数
     * @param pageSize 每页条数
     * @param req 请求携带的特殊参数
     * @return 查询结果
     */
    @ApiOperation(value = "门店表-分页列表条件查询", notes = "门店表-分页列表条件查询")
    @GetMapping(value = "/list")
    public Result<?> queryByList(Store store,
                          @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                          @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                          HttpServletRequest req){
        QueryWrapper<Store> queryWrapper = QueryGenerator.initQueryWrapper(store);
        Page<Store> page = new Page<>(pageNo, pageSize);
        IPage<Store> pageList = storeService.page(page,queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 通过id查询
     *
     * @param id 需要查询的id
     * @return 返回查询结果
     */
    @ApiOperation(value = "门店表-通过id查询", notes = "门店表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        Store store = storeService.getById(id);
        if(store==null) {
            return Result.error("未找到对应数据！");
        }
        return Result.OK(store);
    }

    /**
     * 添加
     *
     * @param store 添加参数
     * @return 返回添加结果
     */
    @ApiOperation(value = "门店表-添加", notes = "门店表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Store store){
        if(storeService.save(store)){
            return Result.OK("添加成功！");
        }
        return Result.error("添加失败！");
    }

    /**
     * 编辑
     *
     * @param store 需要编辑的对象信息
     * @return 返回编辑结果
     */
    @ApiOperation(value = "门店表-编辑", notes = "门店表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody Store store) {
        if(storeService.updateById(store)){
            return Result.OK("编辑成功！");
        }
        return Result.error("编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 需要删除的id
     * @return 返回删除结果
     */
    @ApiOperation(value = "门店表-通过id删除", notes = "门店表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        if(storeService.removeById(id)){
            return Result.OK("删除成功!");
        }
        return Result.error("删除失败!");
    }

    /**
     * 通过ids批量删除
     *
     * @param ids 需要批量删除的id，用逗号分割
     * @return 返回删除结果
     */
    @ApiOperation(value = "门店表-通过ids批量删除", notes = "门店表-通过ids批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        if(storeService.removeByIds(Arrays.asList(ids.split(",")))){
            return Result.OK("批量删除成功!");
        }
        return Result.OK("批量删除失败!");
    }

}
