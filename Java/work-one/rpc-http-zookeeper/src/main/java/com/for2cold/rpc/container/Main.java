package com.for2cold.rpc.container;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jasme on 16/3/22.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        /*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
        context.start();
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();*/
        Main main = new Main();
        System.out.print(main.getClass().getPackage().getName());
    }

}
