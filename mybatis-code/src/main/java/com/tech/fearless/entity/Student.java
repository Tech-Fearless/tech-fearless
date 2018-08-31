package com.tech.fearless.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生实体类
 *
 * @author xiexinjia
 * @create 2018-08-08 下午11:05
 **/
public class Student implements Serializable{

    private static final long serialVersionUID = -337345144331319522L;

    private int id;

    private String name;

    private int age;

    private int sex;

    private Date addTime;

    private Date updateTime;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return this.age;
    }
    public void setSex(int sex){
        this.sex = sex;
    }

    public int getSex(){
        return this.sex;
    }

    public void setAddTime(Date addTime){
        this.addTime = addTime;
    }

    public Date getAddTime(){
        return this.addTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public Date getUpdateTime(){
        return this.updateTime;
    }

}
