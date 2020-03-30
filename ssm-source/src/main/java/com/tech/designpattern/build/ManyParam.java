package com.tech.designpattern.build;

public class ManyParam {

    private String name;

    private String sex;

    private int age;

    private String color;

    private ManyParam(Builder builder){
        this.name = builder.name;
        this.sex = builder.sex;
        this.age = builder.age;
        this.color = builder.color;
    }

    public static class Builder{

        private String name;

        private String sex;

        private int age;

        private String color;

        public Builder(){}

        public Builder(String name,String sex,int age,String color){
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.color = color;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setSex(String sex){
            this.sex = sex;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setColor(String color){
            this.color = color;
            return this;
        }

        public ManyParam build(){
            return new ManyParam(this);
        }
    }


    public static void main(String[] args) {
        Builder builder = new ManyParam.Builder();
        ManyParam manyParam = builder.setAge(1)
                .setSex("male")
                .setName("xiaoxie")
                .setColor("yellow")
                .build();
        System.out.println("ok!");
    }
}
