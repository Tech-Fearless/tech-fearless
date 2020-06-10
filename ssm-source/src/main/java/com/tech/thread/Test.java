package com.tech.thread;

import java.util.concurrent.*;

public class Test {


    private static final CyclicBarrier cyb = new CyclicBarrier(5);
    static Integer count=1;

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,4,1000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(1000));

        /*ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        threadPoolExecutor.submit(threadA);
        threadPoolExecutor.submit(threadB);*/

        /*Call call = new Call();
        try {
            Future<String> future = threadPoolExecutor.submit(call);
            System.out.println("call:" + future.get());
        }catch (Exception e){
            e.printStackTrace();
        }*/

        /*ThreadCommunication threadCommunication = new ThreadCommunication();

        ThreadSave threadSave = new ThreadSave(threadCommunication);
        ThreadGet threadGet = new ThreadGet(threadCommunication);

        threadPoolExecutor.submit(threadSave);
        threadPoolExecutor.submit(threadGet);*/


        /*int threadNum = 5;
        CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 完成最后任务");
            }
        });

        for(int i = 0; i < threadNum; i++) {
            new TaskThread(barrier).start();
        }*/


        DeadLock deadLock = new DeadLock();
        deadLock.startDead();

    }

    static class TaskThread extends Thread {

        CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏 A");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 A");

                Thread.sleep(2000);
                System.out.println(getName() + " 到达栅栏 B");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


