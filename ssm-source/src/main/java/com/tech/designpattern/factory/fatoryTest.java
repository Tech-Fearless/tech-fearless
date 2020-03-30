package com.tech.designpattern.factory;

import com.tech.designpattern.factory.abstractFactory.AbstractFactory;
import com.tech.designpattern.factory.abstractFactory.BaoMaCarAbstractFactory;
import com.tech.designpattern.factory.factoryWay.BaoMaFactory;
import com.tech.designpattern.factory.factoryWay.BenChiFactory;
import com.tech.designpattern.factory.factoryWay.Factory;
import com.tech.designpattern.factory.simple.SimpleFatory;

public class fatoryTest {

    public static void main(String[] args) {
        //简单工厂模式
        SimpleFatory simpleFatory = new SimpleFatory();
        Car baoma = simpleFatory.getInstance("baoma");
        baoma.driver();
        //工厂方法模式
        Factory baomaFactory = new BaoMaFactory();
        Car baoMaCar = baomaFactory.createCar();
        baoMaCar.driver();
        Factory benchiFactory = new BenChiFactory();
        Car benChiCar = benchiFactory.createCar();
        benChiCar.driver();
        //抽象工厂模式
        AbstractFactory abstractFactory = new BaoMaCarAbstractFactory();
        abstractFactory.createCar();
        abstractFactory.createTruck();
    }
}
