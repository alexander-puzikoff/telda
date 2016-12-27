package ru.puzikov.service;

import ru.puzikov.common.Vehicle;

import java.util.List;

/**
 * Created by APuzikov on 17.12.2016.
 */
public interface VehicleService {

    List<Vehicle> getAllVehiclesInArea(int x1, int x2, int y1, int y2);

    void saveOrUpdateVehicle(Vehicle vehicleToSave);

    void updateCoordinates(int x1, int x2, int y1, int y2);

    List<Vehicle> getAllVehicles();
}
