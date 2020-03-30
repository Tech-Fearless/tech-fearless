package com.tech.designpattern.factory.factoryWay;

import com.tech.designpattern.factory.*;

public class BenChiFactory implements Factory {

    public Car createCar(){
        System.out.println("生产一辆奔驰小轿车。");
        return new BenChiCar();
    }

}
