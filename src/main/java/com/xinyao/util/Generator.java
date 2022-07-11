package com.xinyao.util;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maple
 * @date 2019/4/25
 */
public class Generator {

    public static void main(String[] args) {
        String auth = "WangXB";
        String packageName = "com.xinyao";
        String path = "D:\\workspace\\java";
        String url = "192.168.1.15:3306/shopping";
        String username = "root";
        String password = "211114";
        generateTest(auth, packageName, path, url, username, password);
    }

    private static void generateTest(String auth, String packageName, String path,
                                     String url, String username, String password) {
        //全局配置
        GlobalConfig config = new GlobalConfig();
        //设置是否支持AR模式
        config.setActiveRecord(true)
                //设置生成代码的作者
                .setAuthor(auth)
                //设置输出代码的位置
                .setOutputDir(path)
                // XML 二级缓存
                .setEnableCache(false)
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columList
                .setBaseColumnList(true)
                //.setKotlin(true) 是否生成 kotlin 代码
                // 是否使用Swagger2
                .setSwagger2(true)
                // 设置是否覆盖原来的代码
                .setFileOverride(false);

        //******************************数据源配置***************************************
        //数据库连接url
        String dbUrl = "jdbc:mysql://" + url + "?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        //数据库类型 枚举
        dataSourceConfig.setDbType(DbType.MYSQL)
                //设置url
                .setUrl(dbUrl)
                //设置用户名
                .setUsername(username)
                //设置密码
                .setPassword(password)
                //设置数据库驱动
                .setDriverName("com.mysql.cj.jdbc.Driver")
                // 自定义数据库表字段类型转换【可选】
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        //将数据库中datetime转换成date
                        if (fieldType.toLowerCase().contains("datetime")) {
                            return DbColumnType.DATE;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });

        //******************************策略配置******************************************************
        // 自定义需要填充的字段 数据库中的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("update_id", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("update_name", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("create_account", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_id", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_name", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
//        tableFillList.add(new TableFill("sync_flag", FieldFill.INSERT));
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                //全局大写命名是否开启
                .setCapitalMode(true)
                //【实体】是否为lombok模型
                .setEntityLombokModel(true)
                //表名生成策略  下划线转驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                //生成的去掉前缀，可以配置多个
                .setTablePrefix("sys_","ma_","sale_","usc_")
                //自动填充设置
                .setTableFillList(tableFillList)
                //修改替换成你需要的表名，多个表名传数组
//                .setInclude(".*.");
                .setLogicDeleteFieldName("is_deleted")
                .setRestControllerStyle(true)
                .setInclude("sale_order", "sale_order_product");
        //集成注入设置
        //注入全局设置
        new AutoGenerator().setGlobalConfig(config)
                //注入数据源配置
                .setDataSource(dataSourceConfig)
                //注入策略配置
                .setStrategy(strategyConfig)
                //设置包名信息
                .setPackageInfo(
                        new PackageConfig()
                                //提取公共父级包名
                                .setParent(packageName)
                                //设置controller信息
                                .setController("controller.sale")
                                //设置实体类信息
                                .setEntity("bean.sale")
                                .setMapper("mapper.sale")
                                .setXml("mapper.sale")
                )
                //设置自定义模板
                .setTemplate(
                        new TemplateConfig()
                                //.setXml(null)//指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
                                //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
                                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                                // .setController("...");
                                // .setEntity("...");
                                // .setMapper("...");
                                // .setXml("...");
                                // .setService("...");
                                .setServiceImpl("templates/serviceImpl.java")
                )
                //开始执行代码生成
                .execute();
    }
}
