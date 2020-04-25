package com.tech.designpattern.Observer;

import java.util.ArrayList;
import java.util.List;



public class ArticleAuthor implements Watched{

    private static List<Watcher> watcherList = new ArrayList<>();

    @Override
    public void addWatcher(Watcher watcher) {
        watcherList.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        watcherList.remove(watcher);
    }

    @Override
    public void notifyWatcher() {
        for (Watcher watcher:watcherList) {
            watcher.update(this);
        }
    }
}
