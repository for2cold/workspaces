import com.for2cold.rpc.dubbo.facade.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * Created by root on 2016/3/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:spring-consumer.xml"
})
public class UserTestCase {

    @Resource
    private UserController userController;

    @Test
    public void test() throws InterruptedException {
        System.out.println(userController.index());

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
