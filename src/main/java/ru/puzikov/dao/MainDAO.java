package ru.puzikov.dao;

import ru.puzikov.common.Vehicle;

import java.util.List;

/**
 * Created by APuzikov on 15.12.2016.
 */
public interface MainDAO {

    List getAllVehicles();

    void saveOrUpdateVehicle(Vehicle vehicleToSave);
}
