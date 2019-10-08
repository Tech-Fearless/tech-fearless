package com.tech.fearless.boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${person.name}")
    private String name;
    @Value("${test.config}")
    private String testConfig;

    @RequestMapping("/test")
    public String test(){
        System.out.println("name:"+name);
        System.out.println("testConfig:"+testConfig);
        return "spring boot test.";
    }
}
