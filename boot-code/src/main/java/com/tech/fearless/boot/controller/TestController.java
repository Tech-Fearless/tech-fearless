package com.tech.fearless.boot.controller;

import com.tech.fearless.boot.entity.Student;
import com.tech.fearless.boot.mongo.SimpleMongoService;
import com.tech.fearless.boot.rabbitmq.producer.MsgProducer;
import com.tech.fearless.boot.rocketmq.Producer;
import com.tech.fearless.boot.service.StudentService;
import com.tech.fearless.boot.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private SimpleMongoService simpleMongoService;
    @Autowired
    private MsgProducer rabbitMqProducer;

    @Autowired
    private Producer rocketMqProducer;



    @Value("${person.name}")
    private String name;
    @Value("${test.config}")
    private String testConfig;
    @Value("${spring.rocketmq.topic}")
    private String topic;
    @Value("${spring.rocketmq.tag}")
    private String tag;


    @Autowired
    private StudentService studentService;

    @Autowired
    private ExcelUtil excelUtil;

    @ResponseBody
    @RequestMapping(value = "/student",method = RequestMethod.GET)
    public String  addStudent(){
        studentService.addStudent();
        excelUtil.contextLoads();
        return "OK!";
    }

    @RequestMapping("/test")
    public String test(){
        System.out.println("name:"+name);
        System.out.println("testConfig:"+testConfig);
        return "spring boot test.";
    }


    @ResponseBody
    @RequestMapping(value = "/mongo/student",method = RequestMethod.GET)
    public int  addStudentToMongo(){
        Student student = new Student();
        student.setName("xiexinjia");
        student.setAge(29);
        student.setAddTime(new Date());
        student.setSex(1);
        student.setUpdateTime(new Date());

        simpleMongoService.insert(student);
        return 1;
    }

    @ResponseBody
    @RequestMapping(value = "/rabbitMq/send/message",method = RequestMethod.GET)
    public int  sendRabbitMQMessage(){

        rabbitMqProducer.sendMsg("A RabbitMQ Message;");
        return 1;
    }

    @ResponseBody
    @RequestMapping(value = "/rocketMq/send/message",method = RequestMethod.GET)
    public int  sendRocketMQMessage() throws Exception {

        List<String> mesList = new ArrayList<>();
        mesList.add("小小");
        mesList.add("爸爸");
        mesList.add("妈妈");
        mesList.add("爷爷");
        mesList.add("奶奶");

        //总共发送五次消息
        for (String s : mesList) {
            //创建生产信息
            Message message = new Message(topic, tag, ("小小一家人的称谓:" + s).getBytes());
            //发送
            SendResult sendResult = rocketMqProducer.getProducer().send(message);
            log.info("输出生产者信息={}",sendResult);
        }
        return 1;
    }

}
