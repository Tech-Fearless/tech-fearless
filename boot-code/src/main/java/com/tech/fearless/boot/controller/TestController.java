package com.tech.fearless.boot.controller;

import com.tech.fearless.boot.entity.Student;
import com.tech.fearless.boot.mongo.SimpleMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestController {

    @Autowired
    private SimpleMongoService simpleMongoService;

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


    @ResponseBody
    @RequestMapping(value = "/mongo/student",method = RequestMethod.GET)
    public int  addStudentToMongo(){
        Student student = new Student();
        student.setName("xiexinjia");
        student.setAge(29);
        student.setAddTime(new Date());
        student.setSex(1);
        student.setUpdateTime(new Date());

        simpleMongoService.insert(student);
        return 1;
    }

}
