package com.classpath.accountsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/beans")
public class AppController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    public List<String> beans(){
        return Arrays.asList(this.applicationContext.getBeanDefinitionNames());
    }
}