package ru.puzikov;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.junit4.SpringRunner;
import ru.puzikov.common.Vehicle;
import ru.puzikov.config.MainConfig;
import ru.puzikov.service.VehicleService;

import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {
    @Configuration
    @Import(MainConfig.class)
    static class TestConfig {

        @Bean(name = "ThreadPoolTaskExecutor")
        @Scope(value = "prototype")
        ExecutorService getExecutorService() {
            return Executors.newFixedThreadPool(15);
        }
    }

    @Autowired
    VehicleService service;
    @Autowired
    @Qualifier(value = "ThreadPoolTaskExecutor")
    ExecutorService executor;

    AtomicInteger tenCummulative = new AtomicInteger();

    @Test
    public void testMaxInputsUsingService() throws InterruptedException {
        Objects.requireNonNull(service);
        Objects.requireNonNull(executor);
        int THREAD_COUNT = 15;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(THREAD_COUNT);
        service.getAllVehicles();
        for (int i = 0; i < THREAD_COUNT; i++) {
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                        Vehicle vehicle = new Vehicle();
                        vehicle.setY(finalI / 3);
                        vehicle.setId(finalI);
                        vehicle.setX(finalI ^ 2);
                        service.saveOrUpdateVehicle(vehicle);
                        if (service.getAllVehicles().size() == 10) tenCummulative.addAndGet(1);
                        finishLatch.countDown();
                    } catch (InterruptedException e) {
                        Assert.fail();
                    }
                }
            });
        }
        System.out.println("startLatch down");
        startLatch.countDown();
        finishLatch.await();
        System.out.println("finish");
        Assert.assertThat(tenCummulative.get(), is(10));
    }


}
