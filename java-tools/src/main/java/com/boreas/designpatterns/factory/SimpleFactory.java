package com.boreas.designpatterns.factory;

import com.boreas.designpatterns.factory.service.Machine;
import com.boreas.designpatterns.factory.service.impl.MachineA;
import com.boreas.designpatterns.factory.service.impl.MachineB;

/**
 * 简单工厂
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public class SimpleFactory {
    // 增加子类或者删除子类对象的创建都需要在简单工厂类中进行修改，耦合性高,违背开-闭原则
    public static Machine creatData(int name) {
        Machine data = null;
        switch (name) {
            case 1:
                data = new MachineA();
                break;
            case 2:
                data = new MachineB();
                break;
        }
        return data;
    }
}
