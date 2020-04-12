package com.tech.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {

    private HelloConcrete s;
    public MyMethodInterceptor(HelloConcrete s) {
        this.s = s;
    }

    private void aopMethod() {
        System.out.println("i am aopMethod");
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        aopMethod();
        Object a = method.invoke(s, objects);
        return a;
    }
}
