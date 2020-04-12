package com.tech.proxy;

import com.tech.proxy.cglib.HelloConcrete;
import com.tech.proxy.cglib.MyMethodInterceptor;


import net.sf.cglib.proxy.Callback;

import net.sf.cglib.proxy.Enhancer;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class CompanyRun {

    public static void main(String[] args) {
        //静态代理
        ProgrammerWork work = new ProgrammerWork();
        StaticProxy staticProxy = new StaticProxy(work);
        staticProxy.doDailyJob();

        //jdk动态代理
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(work);
        Work dynamicProxyWork = (Work) Proxy.newProxyInstance(DynamicProxyHandler.class.getClassLoader(),
                new Class[]{Work.class}, dynamicProxyHandler);
        dynamicProxyWork.doDailyJob();

        //cglib动态代理
        HelloConcrete helloConcrete = new HelloConcrete();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloConcrete.class);

        Callback s = new MyMethodInterceptor(helloConcrete);
        Callback callbacks[] = new Callback[] { s };
        enhancer.setCallbacks(callbacks);

        HelloConcrete newHelloConcrete = (HelloConcrete)enhancer.create();
        newHelloConcrete.gotoHome();

        //打印jdk动态代理类
        //writeToDisk("D:/com.sun.proxy.$Proxy0.class");


    }

    private static void writeToDisk(String path){
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass("com.sun.proxy.$Proxy0", new Class[]{Work.class}, 17);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(proxyClassFile);
            fileOutputStream.flush();
        }catch (FileNotFoundException e ){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
    }

}
