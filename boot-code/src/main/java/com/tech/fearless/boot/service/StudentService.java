package com.tech.fearless.boot.service;



import com.tech.fearless.boot.entity.Student;

import java.util.List;

/**
 * @author xiexinjia
 * @create 2018-08-08 下午11:17
 **/
public interface StudentService {

    String addStudent();

    void addStudentBatch(List<Student> studentList);
}
