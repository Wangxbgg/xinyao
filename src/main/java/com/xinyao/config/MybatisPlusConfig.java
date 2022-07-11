package com.xinyao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.xinyao.util.JWTUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Date;

/**
 * @author ZhangFZ
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.hege.sale.mapper")
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("isDeleted", 0, metaObject);
        this.setFieldValByName("createId", JWTUtil.getUserId(), metaObject);
        this.setFieldValByName("createName", JWTUtil.getUsername(), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateId", JWTUtil.getUserId(), metaObject);
        this.setFieldValByName("updateName", JWTUtil.getUsername(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateId", JWTUtil.getUserId(), metaObject);
        this.setFieldValByName("updateName", JWTUtil.getUsername(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * 配置监控服务器
     *
     * @return 返回监控注册的servlet对象
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加IP白名单
//        servletRegistrationBean.addInitParameter("allow", "222.67.190.240");
        // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
//        servletRegistrationBean.addInitParameter("deny", "192.168.25.123");
        // 添加控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "qhsh_admin");
        servletRegistrationBean.addInitParameter("loginPassword", "Qhsh-0129.D@#$");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }
}

