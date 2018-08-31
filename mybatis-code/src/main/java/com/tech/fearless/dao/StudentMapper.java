package com.tech.fearless.dao;

import com.tech.fearless.entity.Student;
import org.apache.ibatis.annotations.Param;

/**
 * 学生表操作
 *
 * @author xiexinjia
 * @create 2018-08-08 下午11:07
 **/
public interface StudentMapper {

    void insertStudent(@Param("student") Student student);

    Student loadStudentById(@Param("id") int id);
}
