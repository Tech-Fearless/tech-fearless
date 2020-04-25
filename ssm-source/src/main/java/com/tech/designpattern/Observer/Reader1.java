package com.tech.designpattern.Observer;

public class Reader1 implements Watcher{
    @Override
    public void update(Watched watched) {
        System.out.println(watched + " reader1 read new article.");
    }
}
