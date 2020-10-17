package com.boreas.designpatterns.factory.service.impl;

import com.boreas.designpatterns.factory.service.Machine;

public class MachineA implements Machine {

    public void create() {
        System.out.println("create MachineA");
    }

    public void machining() {
        System.out.println("machining MachineA");
    }
}
