package com.tech.thread;

public class ThreadCommunication {

    public volatile int account = 0;

    public volatile boolean flag = false;

    public static int intSave = 1;

    public static int intGet = 1;


    public synchronized void saveMoney(int num, int i) {
        try {
            if (!flag){
                account += num;
                System.out.println("存钱线程第"+i+"次for循环，第"+(intSave++) + "次存钱，" + "此次存入金额：" + num + ",账户总余额：" + account);
                flag = true;
                notify();
            }else {
                wait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void getMoney(int num, int i) {
        try {
            if (flag){
                account -= num;
                System.out.println("取钱线程第"+i+"次for循环，第"+(intGet++) + "次取钱，" + "此次取出金额：" + num + ",账户总余额：" + account);
                flag = false;
                notify();
            }else {
                wait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class ThreadSave implements Runnable{

    ThreadCommunication threadCommunication = null;

    public ThreadSave(ThreadCommunication threadCommunication){
        this.threadCommunication = threadCommunication;
    }

    @Override
    public void run() {
        for (int i = 1;i <= 5 ; i++){
            threadCommunication.saveMoney(100, i);
        }
    }
}

class ThreadGet implements Runnable{

    ThreadCommunication threadCommunication = null;

    public ThreadGet(ThreadCommunication threadCommunication){
        this.threadCommunication = threadCommunication;
    }

    @Override
    public void run() {
        for (int i = 1;i <= 5; i++){
            threadCommunication.getMoney(100, i);
        }
    }
}
