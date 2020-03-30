package com.tech.designpattern.factory.simple;

import com.tech.designpattern.factory.BaoMaCar;
import com.tech.designpattern.factory.BenChiCar;
import com.tech.designpattern.factory.Car;

/**
 * 简单工厂模式
 */
public class SimpleFatory {

    public Car getInstance(String brand){
        Car car = null;
        switch (brand){
            case "baoma":car = new BaoMaCar();
            break;
            case "benchi":car = new BenChiCar();
            break;
            default:car = null;
        }
        return car;
    }
}
