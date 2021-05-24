package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * @author  ${author}
 * @create  ${date}
 * @email   wuchzh0@gmail.com
 * @desc    ${table.comment!}接口
 */

<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.serviceName} extends IService<${entity}> {

}
</#if>