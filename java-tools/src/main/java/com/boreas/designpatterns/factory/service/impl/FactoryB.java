package com.boreas.designpatterns.factory.service.impl;

import com.boreas.designpatterns.factory.service.Factory;
import com.boreas.designpatterns.factory.service.Machine;

public class FactoryB implements Factory {

    public Machine createMachine() {
        return new MachineB();
    }
}