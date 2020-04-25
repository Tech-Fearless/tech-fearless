package com.tech.jvm;

/**
 * 加载时机
 * 静态内部类、非静态内部类
 * 静态变量，静态代码块，
 *
 * 总结：
 * 1.静态变量和静态代码块的加载时间同类加载时间一致
 * 2.构造方法加载时间后于上面的时间
 * 3.非静态内部类只有在使用的时候才会加载，且不能定义静态变量和静态方法
 * 4.静态内部类只有在使用的时候才加载，使用静态变量和静态方法不会初始化静态内部类
 */
public class LoadTime {

    private static final Long outterTime = System.currentTimeMillis();

    static {
        Long lumpTime = System.currentTimeMillis();
        System.out.println("lumpTime:" + lumpTime);
    }

    public LoadTime(){
        Long constructorTime = System.currentTimeMillis();
        System.out.println("constructorTime:" + constructorTime);
    }

    public void printTime(){
        System.out.println("outterTime:" + outterTime);
        InnerClass innerClass = new InnerClass();
        innerClass.printTime();

        InnerStaticClass.printTime();

    }

    class InnerClass{

        private final Long innerTime = System.currentTimeMillis();

        public InnerClass(){
            Long innerConstructorTime = System.currentTimeMillis();
            System.out.println("innerConstructorTime:" + innerConstructorTime);
        }

        public void printTime(){
            System.out.println("innerTime:" + innerTime);
        }
    }

    static class InnerStaticClass{
        private static final Long innerStaticTime = System.currentTimeMillis();

        static {
            System.out.println("innerStaticLump:" + System.currentTimeMillis());
        }

        public InnerStaticClass(){
            Long innerStaticConstructorTime = System.currentTimeMillis();
            System.out.println("innerStaticConstructorTime:" + innerStaticConstructorTime);
        }

        public static void printTime(){
            System.out.println("innerStaticTime:" + innerStaticTime);
        }
    }


}
