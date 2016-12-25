package ru.puzikov.dao;

import ru.puzikov.common.Vehicle;

import java.util.List;

/**
 * Created by APuzikov on 15.12.2016.
 */
public interface MainDAO {

    List getAllVehicles();

    void saveOrUpdateVehicle(Vehicle vehicleToSave);

    List<Vehicle> getVehiclesInArea(int x1, int x2, int y1, int y2);
}
