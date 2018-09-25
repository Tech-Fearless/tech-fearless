package com.tech.designpattern.factory;

public class BaoMaCarAbstractFactory extends AbstractFactoryWay {
    @Override
    public Car createCar(){
        System.out.printf("create BaoMaCar");
        return new BaoMaCar();
    }

}
