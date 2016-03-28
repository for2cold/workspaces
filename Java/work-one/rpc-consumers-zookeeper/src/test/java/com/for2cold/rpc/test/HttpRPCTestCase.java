package com.for2cold.rpc.test;

import com.for2cold.rpc.PeopleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jasme on 16/3/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring-*.xml"})
public class HttpRPCTestCase {

    private static final Logger logger = LoggerFactory.getLogger(HttpRPCTestCase.class);

    @Resource
    private PeopleController peopleController;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Test
    public void test() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(10000);

        final ExecutorService exec = Executors.newFixedThreadPool(50);

        Date date = new Date();

        for (int index = 0; index < 10000; index++) {

            Runnable run = new Runnable() {
                public void run() {
                    try {
                        String result = peopleController.getSpeak(new Random(100).nextInt(20), new Random(1).nextInt(1));
                        System.out.println(result);
                    } catch (Exception e) {
                        logger.warn("出现异常", e);
                    } finally {
                        latch.countDown();
                    }
                }
            };
            exec.submit(run);
        }
        latch.await();
        System.out.println(new Date().getTime() - date.getTime());
        exec.shutdown();
    }
}
