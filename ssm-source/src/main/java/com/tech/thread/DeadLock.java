package com.tech.thread;

public class DeadLock{

    volatile boolean flag = true;

    public void startDead(){
        Object A = new Object();

        Dead1 dead1 = new Dead1(A, flag);
        Dead2 dead2 = new Dead2(A, flag);

        Thread thread1 = new Thread(dead1);
        Thread thread2 = new Thread(dead2);

        thread1.start();
        thread2.start();
    }

}


class Dead1 implements Runnable{

    Object A = null;
    boolean flag = true;

    public Dead1(Object A, boolean flag){
        this.A = A;
        this.flag = flag;
    }

    @Override
    public void run() {

        for (int i = 0;i < 100 ; i++) {
            try {
                synchronized (A){
                    if (flag){
                        A.notify();
                        flag = false;
                        System.out.println("Dead1 唤醒B线程！！！");
                    }else {
                        A.wait();
                        System.out.println("Dead1 让A线程等待！！！");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

class Dead2 implements Runnable{

    Object A = null;
    boolean flag = false;

    public Dead2(Object A, boolean flag){
        this.A = A;
        this.flag = flag;
    }

    @Override
    public void run() {

        for (int i = 0;i < 100 ;i++) {
            try {
                synchronized (A){
                    if (!flag){
                        A.notify();
                        flag = true;
                        System.out.println("Dead2 唤醒A线程！！！");
                    }else {
                        A.wait();
                        System.out.println("Dead2 让B线程等待！！！");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}