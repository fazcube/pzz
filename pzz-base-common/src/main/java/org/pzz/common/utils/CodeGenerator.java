package org.pzz.common.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * @author  PZJ
 * @create  2021/5/9 20:30
 * @email   wuchzh0@gmail.com
 * @desc    代码生成器
 **/

@Component
//@PropertySource(value = "classpath:fazcube/fazcube.properties")
public class CodeGenerator {


    //@Value("${datasource.url}")
    //private String url;

    /**
     * 读取控制台内容
     * @param tip
     * @return
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("please input " + tip + ":");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("Please enter the correct" + tip + "!");
    }

    /**
     * RUN THIS
     */
    public static void generator() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir"); //
        String moduleName = scanner("module name");
        gc.setOutputDir(projectPath + "/" + moduleName + "/src/main/java"); //
        gc.setAuthor("Fazcube"); //作者名
        gc.setIdType(IdType.ASSIGN_ID); //设置id生成策略
        gc.setOpen(false); // 是否打开输出目录
        gc.setSwagger2(true); // 是否开启swagger2

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        mpg.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("modules name"));
        pc.setParent("org.pzz.modules");
        mpg.setPackageInfo(pc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL); //数据库类型
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/shiro?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 策略配置 数据库表配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 数据库表映射到实体的命名策略 字段驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行 字段驼峰命名
        strategy.setEntityLombokModel(true);// 设置实体类是否使用lombok
        strategy.setInclude(scanner("table name"));// 输入数据库中的表名，需要生成的表名
        //strategy.setSuperEntityColumns("id");//公共实体类字段
        //strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
        //strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");// 公共父类
        strategy.setRestControllerStyle(true); //控制层：true——生成@RsetController false——生成@Controller
        //strategy.setEntityTableFieldAnnotationEnable(true);// 表字段注释启动
        strategy.setControllerMappingHyphenStyle(false);// 设置url连字符样式，设置为true则sys_user会解析成sys-user，设置false则为sysUser
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);

        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return projectPath + "/mybatis-plus-sample-generator/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//        mpg.setTemplate(new TemplateConfig().setXml(null));


        //模板生成器 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        TemplateConfig tc = new TemplateConfig();
        tc.setController("/fazcube/code-template/controller.java");
        tc.setService("/fazcube/code-template/service.java");
        tc.setServiceImpl("/fazcube/code-template/serviceImpl.java");
        tc.setEntity("/fazcube/code-template/entity.java");
        tc.setMapper("/fazcube/code-template/mapper.java");
//        tc.setXml("/templatesFreemaker/mapper.xml");
        mpg.setTemplate(tc);

        mpg.execute();
    }

    public static void main(String[] args) {
        generator();
    }

}

