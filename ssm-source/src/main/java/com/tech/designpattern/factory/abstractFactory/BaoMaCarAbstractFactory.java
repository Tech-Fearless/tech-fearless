package com.tech.designpattern.factory.abstractFactory;

import com.tech.designpattern.factory.BaoMaCar;
import com.tech.designpattern.factory.BaoMaTruck;
import com.tech.designpattern.factory.Car;
import com.tech.designpattern.factory.Truck;

public class BaoMaCarAbstractFactory extends AbstractFactory {

    @Override
    public Car createCar(){
        System.out.printf("生产一辆宝马小轿车。");
        return new BaoMaCar();
    }

    @Override
    public Truck createTruck() {
        System.out.println("生产一辆宝马卡车。");
        return new BaoMaTruck();
    }


}
