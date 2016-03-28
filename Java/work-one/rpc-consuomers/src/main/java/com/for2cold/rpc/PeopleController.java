package com.for2cold.rpc;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by jasme on 16/3/20.
 */
@Component("peopleController")
public class PeopleController {

    @Resource
    private SpeakInterface speakInterface;

    public String getSpeak(Integer age, Integer sex) {
        People people = new People();
        people.setAge(age);
        people.setSex(sex);

        return speakInterface.speak(people);
    }
}
