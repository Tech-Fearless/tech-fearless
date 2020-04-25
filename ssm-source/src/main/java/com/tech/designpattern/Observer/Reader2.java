package com.tech.designpattern.Observer;

public class Reader2 implements Watcher{
    @Override
    public void update(Watched watched) {
        System.out.println(watched + " reader2 collect new article.");
    }
}
