package com.for2cold.rpc;

import org.springframework.stereotype.Service;

/**
 * Created by jasme on 16/3/20.
 */
@Service("speakInterface")
public class SpeakInterfaceImpl implements SpeakInterface {

    public String speak(People people) {
        if (people.getAge() > 18) {
            if (people.getSex() == 0) {
                return "男 " + people.getAge() + " 岁";
            }
            return "女 " + people.getAge() + " 岁";
        }
        return "小朋友";
    }
}
