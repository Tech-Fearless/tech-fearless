package com.tech.designpattern.sington;

public class Signton {


    public static Signton starving = new Signton();
    public static Signton sluggard = null;

    public static volatile Signton threadSafe = null;

    private Signton(){}

    public Signton Starving(){
        return starving;
    }

    //线程不安全
    public static Signton sluggard(){
        if (sluggard == null) {
            sluggard = new Signton();
        }
        return sluggard;
    }

    //线程安全
    public static Signton ThreadSafeMan(){
        if (threadSafe == null) {
            synchronized (Signton.class) {
                if (threadSafe == null) {
                    threadSafe = new Signton();
                }
            }
        }
        return threadSafe;
    }

    /**
     * 通过静态内部类的方式实现
     * 静态内部类只有在使用的时候才会被加载，外部类的加载与静态内部类无关
     * 当调用getInstance()方法的时候，加载静态内部类初始化instance变量
     */
    private static class InstanceHandler{
        private final static Signton instance = new Signton();
    }

    public static Signton getInstance(){
        return InstanceHandler.instance;
    }

}
