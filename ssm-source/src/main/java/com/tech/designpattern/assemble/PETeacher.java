package com.tech.designpattern.assemble;

/**
 * 装饰者模式
 */
public class PETeacher implements Teacher{

    public Teacher teacher;

    public PETeacher(Teacher teacher){
        this.teacher = teacher;
    }

    @Override
    public void teachStudent() {
        System.out.printf("teach student PE.");
        teacher.teachStudent();
    }
}
