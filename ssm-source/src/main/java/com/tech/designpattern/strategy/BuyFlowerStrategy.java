package com.tech.designpattern.strategy;

public class BuyFlowerStrategy implements Strategy{
    @Override
    public String makeHappy() {
        return "a good idea:buy flowers!";
    }
}
