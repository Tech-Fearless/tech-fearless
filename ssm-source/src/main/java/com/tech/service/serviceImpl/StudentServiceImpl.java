package com.tech.service.serviceImpl;

import com.tech.dao.StudentMapper;
import com.tech.entity.Student;
import com.tech.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xiexinjia
 * @create 2018-08-09 上午8:47
 **/
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public void addStudent(){

        Student student = buildStudent();
        studentMapper.insertStudent(student);
        System.out.printf("ok!");
        return;
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
