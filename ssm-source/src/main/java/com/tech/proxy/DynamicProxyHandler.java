package com.tech.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyHandler implements InvocationHandler {

    private Object proxied;

    public DynamicProxyHandler(Object proxied){
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{

        befoerJob();
        Object invokeObject = method.invoke(proxied,args);
        afterJob();
        return invokeObject;
    }

    private void befoerJob(){
        System.out.println("write design file before work.----DynamicProxy\n");
    }
    private void afterJob(){
        System.out.println("slove product problem after work.----DynamicProxy");
    }

}
