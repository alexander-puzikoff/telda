package ru.puzikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    public List<Vehicle> getAllVehiclesInArea(int xmin, int xmax, int ymin, int ymax) {
        if(xmin > xmax || ymin > ymax){
            throw new IllegalArgumentException("Invalid coordinates");
        }
        return vehicleDAO.getVehiclesInArea(xmin, xmax, ymin, ymax);
    }

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
    @CacheEvict(value = "vehicles")
    public void updateCoordinates(int x1, int x2, int y1, int y2) {
        vehicleDAO.getVehiclesInArea(x1, x2, y1, y2); // empty cache
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }
}
