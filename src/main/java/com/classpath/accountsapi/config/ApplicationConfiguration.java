package com.classpath.accountsapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {//implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    /*@Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World From Spring Boot Application in "+  environment + " .... ");
        final String[] beanDefinitionNames = this.applicationContext.getBeanDefinitionNames();
        for(String beanName: beanDefinitionNames){
            System.out.println(" :: "+ beanName + " ::");
        }
    }*/
}