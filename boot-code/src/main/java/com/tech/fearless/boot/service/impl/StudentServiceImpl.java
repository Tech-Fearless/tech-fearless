package com.tech.fearless.boot.service.impl;

import com.tech.fearless.boot.dao.StudentMapper;
import com.tech.fearless.boot.entity.Student;
import com.tech.fearless.boot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xiexinjia
 * @create 2018-08-09 上午8:47
 **/
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public String addStudent(){

        Student student = buildStudent();
        studentMapper.insertStudent(student);

        return "ok!";
    }

    public void addStudentBatch(List<Student> studentList){
        studentMapper.insertStudentBatch(studentList);
        System.out.printf("ok!");
    }

    private Student buildStudent(){
        Student student = new Student();
        student.setName("xxj");
        student.setAge(26);
        student.setSex(1);
        student.setAddTime(new Date());
        student.setUpdateTime(new Date());
        return student;
    }
}
