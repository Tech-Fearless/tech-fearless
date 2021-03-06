package com.tech.fearless.boot.entity;

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

    private String id;

    private String name;

    private int age;

    private int sex;

    private Date addTime;

    private Date updateTime;

    private String grade;

    private String score;

    private String studentId;

    private String meth;

    private String chinese;

    private String english;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMeth() {
        return meth;
    }

    public void setMeth(String meth) {
        this.meth = meth;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
