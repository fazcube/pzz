package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.generate.common.PageResult;
import com.hong.generate.common.Result;
import com.hong.generate.common.StatusCode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
 * @author  ${author}
 * @create  ${date}
 * @email   wuchzh0@gmail.com
 * @desc    ${table.comment!}前端控制器
 */

@Slf4j
@Api(tags = "${table.comment!}")
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

        @ApiOperation(value = "新增")
        @PostMapping("/save")
        public Result save(@RequestBody ${entity} ${table.entityPath}){
        ${table.entityPath}Service.save(${table.entityPath});
        return new Result(StatusCode.SUCCESS,"保存成功");
        }

        @ApiOperation(value = "根据id删除")
        @PostMapping("/delete/{id}")
        public Result delete(@PathVariable("id") Long id){
        ${table.entityPath}Service.removeById(id);
        return new Result(StatusCode.SUCCESS,"删除成功");
        }

        @ApiOperation(value = "条件查询")
        @PostMapping("/get")
        public Result list(@RequestBody ${entity} ${table.entityPath}){
        List<${entity}> ${table.entityPath}List = ${table.entityPath}Service.list(new QueryWrapper<>(${table.entityPath}));
        return new Result(StatusCode.SUCCESS,"查询成功",${table.entityPath}List);
        }

        @ApiOperation(value = "列表（分页）")
        @GetMapping("/list/{pageNum}/{pageSize}")
        public Object list(@PathVariable("pageNum")Long pageNum, @PathVariable("pageSize")Long pageSize){
        IPage<${entity}> page = ${table.entityPath}Service.page(
        new Page<>(pageNum, pageSize), null);
        return new Result(StatusCode.SUCCESS,"查询成功",new PageResult<>(page.getTotal(),page.getRecords()));
        }

        @ApiOperation(value = "详情")
        @GetMapping("/get/{id}")
        public Result get(@PathVariable("id") String id){
        ${entity} ${table.entityPath} = ${table.entityPath}Service.getById(id);
        return new Result(StatusCode.SUCCESS,"查询成功",${table.entityPath});
        }

        @ApiOperation(value = "根据id修改")
        @PostMapping("/update/{id}")
        public Result update(@PathVariable("id") String id, @RequestBody ${entity} ${table.entityPath}){
        ${table.entityPath}.setId(id);
        ${table.entityPath}Service.updateById(${table.entityPath});
        return new Result(StatusCode.SUCCESS,"更新成功");
        }

    </#if>

    }
</#if>