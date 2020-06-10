package com.tech.fearless.boot.mongo.impl;

import com.tech.fearless.boot.entity.Student;
import com.tech.fearless.boot.mongo.AbstractBaseMongoTemplete;
import com.tech.fearless.boot.mongo.SimpleMongoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleMongoServiceImpl extends AbstractBaseMongoTemplete implements SimpleMongoService {

    private static Logger log = LoggerFactory.getLogger(SimpleMongoServiceImpl.class);


    @Autowired
    public MongoTemplate mongoTemplate;

    /**
     * 查询所有
     */
    @Override
    public List<Student> findAll() {
        List<Student> list = mongoTemplate.findAll(Student.class);

        return list;
    }

    @Override
    public void update(Student student) {
        Query query = new Query(Criteria.where("name").is(student.getName()));
        Update update = new Update();
        update.set("age",student.getAge());

        mongoTemplate.updateFirst(query, update, Student.class);
    }

    @Override
    public void insert(Student student) {
        List<Student> list = new ArrayList<>();
        list.add(student);
        try {
            mongoTemplate.insert(list, Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object student) {
        Query query = new Query(new Criteria("_id").is(student));
        try {
            mongoTemplate.findAllAndRemove(query, Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
