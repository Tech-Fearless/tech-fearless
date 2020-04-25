package com.tech.designpattern.Observer;


/**
 * 观察者模式分为推模式和拉模式
 “推”的好处包括：
 1、高效。如果没有更新发生，不会有任何更新消息推送的动作，即每次消息推送都发生在确确实实的更新事件之后，都是有意义的。
 2、实时。事件发生后的第一时间即可触发通知操作。
 3、可以由Subject确立通知的时间，可以避开一些繁忙时间。
 4、可以表达出不同事件发生的先后顺序。

 “拉”的好处包括：
 1、如果观察者众多，Subject来维护订阅者的列表，可能困难，或者臃肿，把订阅关系解脱到Observer去完成。
 2、Observer可以不理会它不关心的变更事件，只需要去获取自己感兴趣的事件即可。
 3、Observer可以自行决定获取更新事件的时间。
 4、拉的形式可以让Subject更好地控制各个Observer每次查询更新的访问权限。
 */
public class Test {
    public static void main(String[] args) {
        ArticleAuthor articleAuthor = new ArticleAuthor();

        articleAuthor.addWatcher(new Reader1());
        articleAuthor.addWatcher(new Reader2());
        articleAuthor.addWatcher(new Reader3());

        articleAuthor.notifyWatcher();
    }

}
