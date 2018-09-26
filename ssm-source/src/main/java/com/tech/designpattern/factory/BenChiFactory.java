package com.tech.designpattern.factory;

public class BenChiFactory extends AbstractFactory{

    public Car createCar(){
        return new BenChiCar();
    }

    public Truck createTruck(){
        return new BenChiTruck();
    }
}
