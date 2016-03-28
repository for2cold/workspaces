package com.for2cold.dubbo.test;

import com.for2cold.rpc.People;
import com.for2cold.rpc.SpeakInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by root on 2016/3/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:spring-dubbo-consumer.xml",
        "classpath*:spring-context.xml"
})
public class BaseTest {

    @Resource
    private SpeakInterface speakInterface;

    @Test
    public void test() {
        People pojo = new People();
        pojo.setAge(19);
        pojo.setSex(1);
        String resp = speakInterface.speak(pojo);
        System.out.println(resp);
    }
}
