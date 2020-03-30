package com.tech.controller;

import com.tech.entity.GetBeanEntity;
import com.tech.service.DoFilterService;
import com.tech.service.SpringUtilTestService;
import com.tech.spring.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

@Controller
@RequestMapping("/test")
public class TestController {


    @Autowired
    private SpringUtilTestService beanService;

    @Autowired
    private DoFilterService doFilterService;

    @ResponseBody
    @RequestMapping(value = "/getBean", method = RequestMethod.GET)
    public void testGetBean(){
        //beanService.callMethod();
        GetBeanEntity entity = new GetBeanEntity();
        entity.testPrint();
    }

    @ResponseBody
    @RequestMapping(value = "/doFilter", method = RequestMethod.GET)
    public void testDoFilter(){
        doFilterService.doBusiness(Collections.emptyMap());
    }

}
