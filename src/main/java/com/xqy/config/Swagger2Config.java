package com.xqy.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xu
 * swagger2接口配置
 * 使得不用写测试类直接使用swaggerUI进行各个类的crud功能的测试
 * 访问地址：localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger.enable}")
    private Boolean enable;

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("CRM-Api")
                .apiInfo(webApiInfo())
                .enable(enable) // 是否显示
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("唐三藏CRM管理系统-Api")
                .description("唐三藏-Api")
                .version("1.0")
                .contact(new Contact("徐庆耀", "www.xqy/tangsanzang.com", "965447695@qq.com"))
                .build();
    }
}
