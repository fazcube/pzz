package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
 * @author  ${author}
 * @create  ${date}
 * @email   wuchzh0@gmail.com
 * @desc    ${table.comment!}Mapper
 */

<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>