package com.tech.designpattern.factory;

public class fatoryTest {

    public static void main(String[] args) {
        //工厂方法模式
        AbstractFactoryWay baoMaCarFactory = new BaoMaCarAbstractFactory();
        Car baoMaCar = baoMaCarFactory.createCar();
        AbstractFactoryWay benChiCarFactory = new BenChiCarAbstractFactory();
        Car benChiCar = benChiCarFactory.createCar();
        //抽象工厂模式
        AbstractFactory factory = new BenChiFactory();
        factory.createCar();
        factory.createTruck();
    }
}
