package com.tech.thread;

import java.util.concurrent.Callable;

public class Call implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "callable";
    }
}
