package com.tech.proxy;

import java.lang.reflect.Proxy;

public class CompanyRun {

    public static void main(String[] args) {
        //静态代理
        ProgrammerWork work = new ProgrammerWork();
        StaticProxy staticProxy = new StaticProxy(work);
        staticProxy.doDailyJob();

        //动态代理
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(work);
        Work dynamicProxyWork = (Work) Proxy.newProxyInstance(DynamicProxyHandler.class.getClassLoader(),
                new Class[]{Work.class}, dynamicProxyHandler);
        dynamicProxyWork.doDailyJob();
    }

}
