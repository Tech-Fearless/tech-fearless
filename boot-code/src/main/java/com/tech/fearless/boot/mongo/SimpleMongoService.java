package com.tech.fearless.boot.mongo;


import com.tech.fearless.boot.entity.Student;

import java.util.List;

public interface SimpleMongoService {

    List<Student> findAll();

    void update(Student student);

    void insert(Student student);

    void delete(Object student);

}
