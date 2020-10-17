package com.boreas.designpatterns.factory.service.impl;

import com.boreas.designpatterns.factory.service.Factory;
import com.boreas.designpatterns.factory.service.Machine;

public class FactoryA implements Factory {

    public Machine createMachine() {
        return new MachineA();
    }
}
