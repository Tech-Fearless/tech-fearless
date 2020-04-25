package com.tech.designpattern.Observer;

public interface Watched {

    void addWatcher(Watcher watcher);

    void removeWatcher(Watcher watcher);

    void notifyWatcher();

}
