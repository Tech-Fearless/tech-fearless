package com.tech.entity;

import com.tech.service.SpringUtilTestService;
import com.tech.spring.SpringContextUtil;

public class GetBeanEntity {

    public void testPrint(){
        SpringUtilTestService beanService = (SpringUtilTestService)SpringContextUtil.getBean(SpringUtilTestService.BEAN_NAME);
        beanService.callMethod();
    }

}
