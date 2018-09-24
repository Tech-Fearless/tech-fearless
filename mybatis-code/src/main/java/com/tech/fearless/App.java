package com.tech.fearless;

import com.tech.fearless.dao.StudentMapper;
import com.tech.fearless.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        try {
            //使用MyBatis提供的Resources类加载mybatis的配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            //构建sqlSession的工厂
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

            SqlSession session= sessionFactory.openSession();
            StudentMapper mapper=session.getMapper(StudentMapper.class);
            Student student = buildStudent();
            mapper.insertStudent(student);
            System.out.println(student.toString());

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Student buildStudent(){
        Student student = new Student();
        student.setName("xxj");
        student.setAge(26);
        student.setSex(1);
        student.setAddTime(new Date());
        student.setUpdateTime(new Date());
        return student;
    }
}
