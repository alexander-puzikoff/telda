package ru.puzikov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.common.Vehicle;

/**
 * Created by APuzikov on 15.12.2016.
 */
@Deprecated
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {


}
