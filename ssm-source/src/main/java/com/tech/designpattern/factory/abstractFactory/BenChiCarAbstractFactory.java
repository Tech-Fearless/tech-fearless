package com.tech.designpattern.factory.abstractFactory;

import com.tech.designpattern.factory.BenChiCar;
import com.tech.designpattern.factory.BenChiTruck;
import com.tech.designpattern.factory.Car;
import com.tech.designpattern.factory.Truck;

public class BenChiCarAbstractFactory extends AbstractFactory {

    @Override
    public Car createCar(){
        System.out.printf("生产一辆奔驰小轿车。");
        return new BenChiCar();
    }

    @Override
    public Truck createTruck() {
        System.out.println("生产一辆奔驰卡车。");
        return new BenChiTruck();
    }

}
