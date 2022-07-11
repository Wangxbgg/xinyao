package com.xinyao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 我的配置信息
 * @author Maple
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 图片存储路径
     */
    @Value("${file.imageFilePath}")
    private String imageFilePath;
    /**
     * 文档存储路径
     */
    @Value("${file.docFilePath}")
    private String docFilePath;

    /**
     * 临时合同存放路径
     */
    @Value("${file.contractTemplatePdfPath}")
    private String contractTemplatePdfPath;

    /**
     * 正式合同pdf存放路径
     */
    @Value("${file.contractPdfPath}")
    private String contractPdfPath;

    /**
     * 提货单pdf存放路径
     */
    @Value("${file.loadBillPdfPath}")
    private String loadBillPdfPath;



    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 单位为ms
        factory.setReadTimeout(30000);
        factory.setConnectTimeout(30000);
        return factory;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //重写方法
        //修改tomcat 虚拟映射
        //定义图片存放路径
        registry.addResourceHandler("/images/**").
                addResourceLocations("file:" + imageFilePath);
        //定义文档存放路径
        registry.addResourceHandler("/doc/**").
                addResourceLocations("file:" + docFilePath);
        //定义临时合同存放路径
        registry.addResourceHandler("/template/**").
                addResourceLocations("file:" + contractTemplatePdfPath);
        //定义正式合同存放路径
        registry.addResourceHandler("/contract/**").
                addResourceLocations("file:" + contractPdfPath);
        //定义提货单存放路径
        registry.addResourceHandler("/loadBill/**").
                addResourceLocations("file:" + loadBillPdfPath);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyStringConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    /**
     * 修改StringHttpMessageConverter默认配置
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add(responseBodyStringConverter());
    }
}