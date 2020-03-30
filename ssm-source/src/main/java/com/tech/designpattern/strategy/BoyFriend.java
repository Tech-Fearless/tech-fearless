package com.tech.designpattern.strategy;

public class BoyFriend {

    Strategy strategy;

    public BoyFriend(Strategy strategy){
        this.strategy = strategy;
    }

    public String thinkWay(){
        return strategy.makeHappy();
    }

    public static void main(String[] args) {
        BoyFriend boyFriend = new BoyFriend(new BuyFlowerStrategy());
        System.out.println("how to make girlFriend happy:" + boyFriend.thinkWay());
        System.out.println("\n");
        boyFriend = new BoyFriend(new SeeMovieStrategy());
        System.out.println("how to make girlFriend happy:" + boyFriend.thinkWay());
    }

}
