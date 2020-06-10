package com.tech.thread;

public class Sync{

    String string = new String("dfs");

    static Sync sync = new Sync();

    static int i = 0;
    static int k = 0;
    static int t = 0;

    public void getString(){
        System.out.println("address:" +  System.identityHashCode(string));
    }

    public synchronized void forI(){
        for (int j = 0;j < 10000; j++){
            System.out.println(" i = " + i++);
        }
    }

    public static synchronized void forK(){
        for (int j = 0;j < 100000; j++){
            System.out.println(" K = " +
                    k++);
        }
    }

    public void forT(){
        for (int j = 0;j < 100000; j++){
            synchronized (Sync.class){
                System.out.println(" k = " + k++);
            }
        }
    }

}

class ThreadA implements Runnable{

    @Override
    public void run() {
        Sync.sync.forT();
    }
}

class ThreadB implements Runnable{

    @Override
    public void run() {
        Sync.sync.forK();
    }
}


