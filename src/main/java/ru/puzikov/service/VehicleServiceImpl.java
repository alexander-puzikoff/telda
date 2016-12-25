package ru.puzikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.common.Vehicle;
import ru.puzikov.dao.MainDAO;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by APuzikov on 17.12.2016.
 */
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private MainDAO vehicleDAO;
    private final int PERMITS = 10;
    private final Semaphore writeSemaphore = new Semaphore(PERMITS);

    @Override
    public void saveOrUpdateVehicle(Vehicle vehicleToSave) {
        try {
            writeSemaphore.acquire();
            vehicleDAO.saveOrUpdateVehicle(vehicleToSave);
            if (writeSemaphore.availablePermits() <= PERMITS)
                writeSemaphore.release();
        } catch (InterruptedException e) {
            // e.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }
}
