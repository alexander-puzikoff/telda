package ru.puzikov.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.puzikov.common.Vehicle;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by APuzikov on 11.12.2016.
 */
@Repository
@Transactional
public class MainDAOImpl implements MainDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    @Transactional(readOnly = true)
    @Override
    public List<Vehicle> getAllVehicles() {
        return sessionFactory.getCurrentSession().createQuery("FROM Vehicle V").list();
    }

    @Override
    public void saveOrUpdateVehicle(Vehicle vehicleToSave) {
        sessionFactory.getCurrentSession().saveOrUpdate(vehicleToSave);
    }

}
