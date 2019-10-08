package com.tech.proxy;

public class StaticProxy implements Work{

    private Work work;

    public StaticProxy(Work work){
        this.work = work;
    }

    @Override
    public void doDailyJob(){
        befoerJob();
        work.doDailyJob();
        afterJob();
    }

    private void befoerJob(){
        System.out.println("write design file before work.----StaticProxy\n");
    }

    private void afterJob(){
        System.out.println("slove product problem after work.----StaticProxy");
    }

}
