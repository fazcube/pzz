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
import java.util.List;
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

    @GetMapping(value = "/list")
    public Result<?> list(${entity} ${table.entityPath},
                          @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                          @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                          HttpServletRequest req){
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        Page<${entity}> page = new Page<>(pageNo, pageSize);
        IPage<${entity}> pageList = ${table.entityPath}Service.page(page,queryWrapper);
        return Result.OK(pageList);
    }

    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ${entity} ${table.entityPath}){
        if(${table.entityPath}Service.save(${table.entityPath})){
            return Result.OK("添加成功！");
        }
        return Result.error("添加失败！");
    }
    </#if>

}
</#if>