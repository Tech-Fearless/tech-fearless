package com.tech.dao;

import org.apache.ibatis.annotations.Param;
import com.tech.entity.Student;

/**
 * 学生表操作
 *
 * @author xiexinjia
 * @create 2018-08-08 下午11:07
 **/
public interface StudentMapper {

    void insertStudent(@Param("student")Student student);

    Student loadStudentById(@Param("id")int id);
}
