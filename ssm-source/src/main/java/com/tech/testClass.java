package com.tech;

import com.tech.hystrix.TestCommand;

import java.util.concurrent.Future;

public class testClass {

    public static void main(String[] args) {
        String s = new TestCommand().execute();
        System.out.printf("execute:"+s+"\n");

        try{
            Future<String> future = new TestCommand().queue();
            String s2 = future.get();
            System.out.printf("queue："+s2+"\n");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
