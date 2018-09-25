package com.tech.designpattern.factory;

public class BenChiCarAbstractFactory extends AbstractFactoryWay {

    @Override
    public Car createCar(){
        System.out.printf("create BenChi");
        return new BenChiCar();
    }

}
