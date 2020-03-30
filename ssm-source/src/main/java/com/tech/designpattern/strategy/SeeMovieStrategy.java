package com.tech.designpattern.strategy;

public class SeeMovieStrategy implements Strategy{
    @Override
    public String makeHappy() {
        return "a good idea : see movies!";
    }
}
