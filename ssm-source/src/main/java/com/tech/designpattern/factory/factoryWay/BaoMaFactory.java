package com.tech.designpattern.factory.factoryWay;

import com.tech.designpattern.factory.BenChiCar;
import com.tech.designpattern.factory.Car;

public class BaoMaFactory implements Factory{

    public Car createCar(){
        System.out.println("生产一辆奔驰车。");
        return new BenChiCar();
    }

}
