package com.tech.designpattern.Observer;

public class Reader3 implements Watcher{
    @Override
    public void update(Watched watched) {
        System.out.println(watched + " reader3 comment on new article.");
    }
}
