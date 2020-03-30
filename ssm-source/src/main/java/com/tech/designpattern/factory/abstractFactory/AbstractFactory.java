package com.tech.designpattern.factory.abstractFactory;

import com.tech.designpattern.factory.Car;
import com.tech.designpattern.factory.Truck;

public abstract class AbstractFactory {

    public abstract Car createCar();

    public abstract Truck createTruck();

}
