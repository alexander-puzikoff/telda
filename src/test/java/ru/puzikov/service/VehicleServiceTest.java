package ru.puzikov.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.puzikov.common.Vehicle;
import ru.puzikov.config.MainConfig;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {
    @Configuration
    @Import(MainConfig.class)
    static class TestConfig {

        @Bean(name = "ThreadPoolTaskExecutor")
        ExecutorService getExecutorService() {
            return Executors.newFixedThreadPool(15);
        }
    }

    @Autowired
    VehicleService service;
    @Autowired
    ExecutorService executor;

    @Test
    public void testMaxInputsUsingService() throws InterruptedException {
        AtomicInteger tenCummulative = new AtomicInteger();
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

    @Test
    public void vehicleInAreaTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(432);
        vehicle.setX(15);
        vehicle.setY(20);
        service.saveOrUpdateVehicle(vehicle);
        List<Vehicle> allVehiclesInArea = service.getAllVehiclesInArea(10, 20, 10, 40);
        Assert.assertThat(allVehiclesInArea.size(), is(1));
        Assert.assertThat(allVehiclesInArea.get(0), is(vehicle));
    }

    @Test
    public void vehicleNotInAreaTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(123);
        vehicle.setX(4);
        vehicle.setY(2 / 3);
        service.saveOrUpdateVehicle(vehicle);
        List<Vehicle> allVehiclesInArea = service.getAllVehiclesInArea(100, 200, 100, 400);
        Assert.assertThat(allVehiclesInArea.size(), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAreaInputForX() {
        service.getAllVehiclesInArea(100, 12, 3201, 4444);
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAreaInputForY() {
        service.getAllVehiclesInArea(10, 12, 31201, 4444);
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAreaInputForBothXandY() {
        service.getAllVehiclesInArea(101, 12, 31201, 4444);
        Assert.fail();
    }

    public void validAreaInput() {
        Assert.assertNotNull(service.getAllVehiclesInArea(10, 12, 301, 444));
    }

}
