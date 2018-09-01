package com.tech.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class TestCommand extends HystrixCommand<String> {

    public TestCommand(){
        super(HystrixCommandGroupKey.Factory.asKey("ServiceGroup"));
    }

    @Override
    public String run(){
        return "hello world";
    }

    @Override
    public String getFallback(){
        return "hello fallback";
    }
}
