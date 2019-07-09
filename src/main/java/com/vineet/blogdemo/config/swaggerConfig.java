package com.vineet.blogdemo.config;

import com.vineet.blogdemo.controller.restController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2     // get functionality for Swagger 2
@PropertySource("classpath:swagger.properties")
@ComponentScan (basePackageClasses = restController.class)
@Configuration
public class swaggerConfig {

    private static final String SWAGGER_API_VERSION = "1.0";
   // private static final String LICENSE = "License";
    private static final String title = "Blog REST APIs";
    private static final String description = "RESTful APIs for Blog Management";

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(SWAGGER_API_VERSION)
                .build();
    }

    @Bean
    public Docket restAPI(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/api/v1.*"))
                .build();
    }
}
