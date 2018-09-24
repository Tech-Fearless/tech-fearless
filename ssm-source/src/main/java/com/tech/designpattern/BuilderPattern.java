package com.tech.designpattern;

import java.util.Map;

public class BuilderPattern {

    private String name;

    private int age;

    private int score;

    private String address;

    private Map<String,Object> condition;

    private BuilderPattern(){}

    public static class Builder{

        private BuilderPattern builderPattern = new BuilderPattern();

        private String name;

        private int age;

        private int score;

        private String address;

        private Map<String,Object> paramMap;

        public Builder(){}

        public Builder(String name,int age){
            this.age = age;
            this.name = name;
        }

        public Builder addCondition(Map<String,Object> paramMap){
            this.paramMap = paramMap;
            return this;
        }

        public Builder addAddress(String address){
            this.address = address;
            return this;
        }

        public Builder addScore(int score){
            this.score = score;
            return this;
        }

        public BuilderPattern build(){
            builderPattern.age = this.age;
            builderPattern.name = this.name;
            builderPattern.address = this.address;
            builderPattern.score = this.score;
            builderPattern.condition = this.paramMap;
            return this.builderPattern;
        }

    }

}
