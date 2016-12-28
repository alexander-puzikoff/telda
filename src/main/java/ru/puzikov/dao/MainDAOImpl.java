package ru.puzikov.dao;

import org.hibernate.SessionFactory;
import org.springframework.cache.annotation.Cacheable;
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

    @Transactional(readOnly = true)
    @Cacheable(value = "vehicles")
    @Override
    public List<Vehicle> getVehiclesInArea(int x1, int x2, int y1, int y2) {
        String query = "FROM Vehicle v where v.x BETWEEN :x1 and :x2  and v.y BETWEEN :y1 AND :y2 ";
        return sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("x1",x1)
                .setParameter("x2",x2)
                .setParameter("y1",y1)
                .setParameter("y2",y2)
                .list();

    }

}
