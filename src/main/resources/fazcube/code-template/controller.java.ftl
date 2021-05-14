package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import com.faz.springbootshiro.common.vo.Result;


/**
 * @author  ${author}
 * @create  ${date}
 * @email   wuchzh0@gmail.com
 * @desc    ${table.comment!}前端控制器
 */

@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {

    @Autowired
    public ${table.serviceName} ${table.entityPath}Service;

    /**
     * 分页列表查询
     *
     * @param ${table.entityPath} 查询参数
     * @param pageNo 页码数
     * @param pageSize 每页条数
     * @param req 请求携带的特殊参数
     * @return 查询结果
     */
    @GetMapping(value = "/list")
    public Result<?> queryByList(${entity} ${table.entityPath},
                          @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                          @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                          HttpServletRequest req){
        QueryWrapper<${entity}> queryWrapper = QueryGenerator.initQueryWrapper(${table.entityPath});
        Page<${entity}> page = new Page<>(pageNo, pageSize);
        IPage<${entity}> pageList = ${table.entityPath}Service.page(page,queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 通过id查询
     *
     * @param id 需要查询的id
     * @return 返回查询结果
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        ${entity} ${table.entityPath} = ${table.entityPath}Service.getById(id);
        if(${table.entityPath}==null) {
            return Result.error("未找到对应数据！");
        }
        return Result.OK(${table.entityPath});
    }

    /**
     *   添加
     *
     * @param ${table.entityPath} 添加参数
     * @return 返回添加结果
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ${entity} ${table.entityPath}){
        if(${table.entityPath}Service.save(${table.entityPath})){
            return Result.OK("添加成功！");
        }
        return Result.error("添加失败！");
    }

    /**
     *  编辑
     *
     * @param ${table.entityPath} 需要编辑的对象信息
     * @return 返回编辑结果
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ${entity} ${table.entityPath}) {
        if(${table.entityPath}Service.updateById(${table.entityPath})){
            return Result.OK("编辑成功！");
        }
        return Result.error("编辑失败!");
    }

    /**
     *   通过id删除
     *
     * @param id 需要删除的id
     * @return 返回删除结果
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        if(${table.entityPath}Service.removeById(id)){
            return Result.OK("删除成功!");
        }
        return Result.error("删除失败!");
    }

    /**
     *  通过ids批量删除
     *
     * @param ids 需要批量删除的id，用逗号分割
     * @return 返回删除结果
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        if(${table.entityPath}Service.removeByIds(Arrays.asList(ids.split(",")))){
            return Result.OK("批量删除成功!");
        }
        return Result.OK("批量删除失败!");
    }
    </#if>

}
</#if>