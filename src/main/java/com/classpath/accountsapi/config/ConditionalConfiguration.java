package com.classpath.accountsapi.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConditionalConfiguration {

    @Bean
    @ConditionalOnProperty(name = "loadBean", prefix = "app", havingValue = "true")
    public RestTemplate restTemplateBean(){
        return new RestTemplate();
    }
    @Bean
    @ConditionalOnMissingBean(name = "restTemplateBean")
    public RestTemplate restTemplateBeanTwo(){
        return new RestTemplate();
    }
}