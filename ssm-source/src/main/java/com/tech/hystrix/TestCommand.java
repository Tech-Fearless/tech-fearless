package com.tech.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.tech.entity.Student;
import com.tech.service.StudentService;

import java.util.Arrays;
import java.util.List;

public class TestCommand extends HystrixCommand<List<String>> {

    private StudentService studentService;
    private List<Student> studentList;

    public TestCommand(StudentService studentService,List<Student> studentList){
        super(HystrixCommandGroupKey.Factory.asKey("StudentServiceCommand"));
        this.studentService = studentService;
        this.studentList = studentList;
    }

    @Override
    public List<String> run(){
        studentService.addStudentBatch(studentList);
        return Arrays.asList("hello!");
    }

    @Override
    public List<String> getFallback(){
        return Arrays.asList("hello fallback");
    }
}
