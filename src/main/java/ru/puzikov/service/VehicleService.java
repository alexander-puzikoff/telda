package ru.puzikov.service;

import ru.puzikov.common.Vehicle;

import java.util.List;

/**
 * Created by APuzikov on 17.12.2016.
 */
public interface VehicleService {


    void saveOrUpdateVehicle(Vehicle vehicleToSave);

    List<Vehicle> getAllVehicles();
}
