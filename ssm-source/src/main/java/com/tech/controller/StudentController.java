package com.tech.controller;

import com.tech.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author xiexinjia
 * @create 2018-08-08 下午11:15
 **/
@Controller
@RequestMapping("/test")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/student",method = RequestMethod.GET)
    public int  addStudent(){
        studentService.addStudent();
        return 1;
    }

}
