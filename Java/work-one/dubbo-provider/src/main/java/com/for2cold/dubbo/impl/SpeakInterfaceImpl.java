package com.for2cold.dubbo.impl;

import com.for2cold.rpc.GrowUpInterface;
import com.for2cold.rpc.People;
import com.for2cold.rpc.SpeakInterface;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by root on 2016/3/24.
 */
@Component("speakInterface")
public class SpeakInterfaceImpl implements SpeakInterface {

    @Resource
    private GrowUpInterface growUpInterface;

    @Override
    public String speak(People people) {
        return "Dubbo return " + growUpInterface.addAge(people).getAge();
    }
}
