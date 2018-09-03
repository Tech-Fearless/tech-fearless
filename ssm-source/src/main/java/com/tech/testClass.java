package com.tech;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.tech.entity.Student;
import com.tech.hystrix.TestCollapseCommand;
import com.tech.service.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Date;
import java.util.concurrent.Future;

public class testClass {

    public static void main(String[] args) {

        /*ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"classpath:spring-mybatis.xml",
                "classpath:spring-mvc.xml"});

        StudentService studentService = (StudentService)applicationContext.getBean("studentService");

        try {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            TestCollapseCommand bc1 = new TestCollapseCommand(studentService, buildStudent());
            TestCollapseCommand bc2 = new TestCollapseCommand(studentService, buildStudent());
            TestCollapseCommand bc3 = new TestCollapseCommand(studentService, buildStudent());
            TestCollapseCommand bc4 = new TestCollapseCommand(studentService, buildStudent());
            Future<String> q1 = bc1.queue();
            Future<String> q2 = bc2.queue();
            Future<String> q3 = bc3.queue();
            String book1 = q1.get();
            String book2 = q2.get();
            String book3 = q3.get();
            Thread.sleep(3000);
            Future<String> q4 = bc4.queue();
            String book4 = q4.get();
            System.out.println("book1>>>"+book1);
            System.out.println("book2>>>"+book2);
            System.out.println("book3>>>"+book3);
            System.out.println("book4>>>"+book4);
            context.shutdown();
        }catch (Exception e){
            System.out.printf("error happend!");
        }
*/
    }

    private static Student buildStudent(){
        Student student = new Student();
        student.setName("xxj");
        student.setAge(26);
        student.setSex(1);
        student.setAddTime(new Date());
        student.setUpdateTime(new Date());
        return student;
    }


}
