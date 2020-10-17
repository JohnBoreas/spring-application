package com.boreas.designpatterns.factory.service.impl;

import com.boreas.designpatterns.factory.service.Machine;

public class MachineB implements Machine {

    public void create() {
        System.out.println("create MachineB");
    }

    public void machining() {
        System.out.println("machining MachineB");
    }
}
