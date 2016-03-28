package com.for2cold.dubbo;

import com.for2cold.rpc.GrowUpInterface;
import com.for2cold.rpc.People;

/**
 * Created by root on 2016/3/24.
 */
public class GrowUpInterfaceImpl implements GrowUpInterface {

    public People addAge(People people) {
        people.setAge(people.getAge() + 1);
        return people;
    }
}
