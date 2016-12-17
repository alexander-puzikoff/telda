package ru.puzikov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.puzikov.common.Vehicle;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by APuzikov on 15.12.2016.
 */
public interface MainDAO {
    @Autowired
    void setDataSource(DataSource dataSource);

    List<Vehicle> getVehicles();
}
