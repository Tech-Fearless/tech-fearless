package com.tech.designpattern.assemble;

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
