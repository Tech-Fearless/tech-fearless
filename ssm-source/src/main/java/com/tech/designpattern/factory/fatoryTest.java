package com.tech.designpattern.factory;

public class fatoryTest {

    public static void main(String[] args) {
        AbstractFactoryWay baoMaCarFactory = new BaoMaCarAbstractFactory();
        Car baoMaCar = baoMaCarFactory.createCar();
        AbstractFactoryWay benChiCarFactory = new BenChiCarAbstractFactory();
        Car benChiCar = benChiCarFactory.createCar();
    }
}
