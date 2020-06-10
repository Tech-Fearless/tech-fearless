package com.tech.fearless.boot.mongo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class AbstractBaseMongoTemplete implements ApplicationContextAware {


    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

    }

}
