package com.jsan.jvaif.tool.gen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动生成代码
 *
 * @author jcwang
 */
@Slf4j
public class CodeGenerator {

    private static String strategy_tablePrefix = "t_";
    private static String strategy_tableName = "t_scy_resource";
//    private static String pc_moduleName = "inf";
//    private static String gc_projectPath = "/Users/jcwang/01-code/jvaif/inf";
//    private static String pc_moduleName_controller = "web";
//private static String gc_projectPath_controller = "/Users/jcwang/01-code/jvaif/web/src/main/java/com/jsan/jvaif/web/controller/";

    private static String pc_moduleName = "tool";
    private static String gc_projectPath = "/Users/jcwang/01-code/jvaif/tool";
    private static String gc_projectPath_controller = "/Users/jcwang/01-code/jvaif/web/src/main/java/com/jsan/jvaif/web/controller/";
    private static String pg_controller = "com.jsan.jvaif.web.controller";

    /**
     * 是否需要跨域//是否需要在controller中增加@CrossOrigin注解
     */
    private static boolean needCrossOrigin = true;
    private static String pc_parent = "com.jsan.jvaif";
    private static String gc_author = "wangjc";
    private static String dsc_url =
        "jdbc:mysql://39.105.62.226:3306/db_security?useUnicode=true&characterEncoding=UTF-8";
    private static String dsc_driverName = "com.mysql.jdbc.Driver";
    private static String dsc_userName = "root";
    private static String dsc_password = "cykj2018aaa";
    private static String strategy_superEntityClass = null;
    private static boolean strategy_lombokMode = true;
    private static boolean strategy_restControllerStyle = true;
    private static boolean controllerMappingHyphenStyle = true;
    private static String strategy_superControllerClass = "com.jsan.jvaif.web.controller.AbstractController";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(globalConfig());
        // 数据库配置
        mpg.setDataSource(dataSourceConfig());
        // 包配置
        mpg.setPackageInfo(packageConfig());
        // 自定义配置
        mpg.setCfg(injectionConfig());
        // 模板配置
        mpg.setTemplate(templateConfig());
        // 策略配置
        mpg.setStrategy(strategyConfig());
        mpg.setTemplateEngine(new VelocityTemplateEngine());

        mpg.execute();

        log.info("自定义字段: needCrossOrigin = {}",mpg.getCfg().getMap().get("needCrossOrigin"));
    }

    /**
     * @return 全局配置
     */
    private static GlobalConfig globalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(gc_projectPath + "/src/main/java");
        gc.setAuthor(gc_author);
        gc.setOpen(false);
        gc.setIdType(IdType.UUID);
        return gc;
    }

    /**
     * @return 数据源配置
     */
    private static DataSourceConfig dataSourceConfig() {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dsc_url);
        // dsc.setSchemaName("public");
        dsc.setDriverName(dsc_driverName);
        dsc.setUsername(dsc_userName);
        dsc.setPassword(dsc_password);
        return dsc;
    }

    /**
     * @return 包配置
     */
    private static PackageConfig packageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(pc_moduleName);
        pc.setEntity("domain");
        pc.setParent(pc_parent);
        return pc;
    }

    /**
     * @return 策略配置
     */
    private static StrategyConfig strategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(strategy_superEntityClass);
        strategy.setEntityLombokModel(strategy_lombokMode);
        strategy.setRestControllerStyle(strategy_restControllerStyle);
        strategy.setRestControllerStyle(controllerMappingHyphenStyle);
        // 公共父类
        strategy.setSuperControllerClass(strategy_superControllerClass);
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(strategy_tableName);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(strategy_tablePrefix);
        return strategy;
    }

    /**
     * @return 模板配置
     */
    private static TemplateConfig templateConfig() {
        // 配置模板
        TemplateConfig tc = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        tc.setEntity("templates/entity.java");

        // 在这里设置null, 然后再自定义配置中设置代码生成路径, 否则成会生成两个文件
        tc.setXml(null);
        tc.setController(null);
        return tc;
    }

    /**
     * @return 自定义配置
     */
    private static InjectionConfig injectionConfig() {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>(16);
                //设置自定义字段, 通过mpg.getCfg().getMap().get("xxx")获取值, 在模板文件中通过cfg.xxx获取
                map.put("needCrossOrigin" , needCrossOrigin);
                map.put("pg_controller",pg_controller);
                this.setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String mapperXmlTemplate = "/templates/mapper.xml.vm";
        String controllerTemplate = "/template/controller.java.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        // controller
        focList.add(new FileOutConfig(controllerTemplate) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return gc_projectPath_controller + tableInfo
                    .getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        // mapper.xml
        focList.add(new FileOutConfig(mapperXmlTemplate) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return gc_projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper"
                    + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

}