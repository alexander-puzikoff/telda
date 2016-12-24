package ru.puzikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.common.Vehicle;
import ru.puzikov.dao.MainDAO;

import java.util.List;

/**
 * Created by APuzikov on 17.12.2016.
 */
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private MainDAO vehicleDAO;


    @Override
    public void saveOrUpdateVehicle(Vehicle vehicleToSave) {
        vehicleDAO.saveOrUpdateVehicle(vehicleToSave);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }
}
