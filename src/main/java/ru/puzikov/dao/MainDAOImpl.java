package ru.puzikov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.puzikov.common.Vehicle;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by APuzikov on 11.12.2016.
 */
@Repository
public class MainDAOImpl {

    private DataSource dataSource;
    private JdbcTemplate template = null;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new JdbcTemplate(this.dataSource);
    }


    public Vehicle findById(long id) {
        return null;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = template.query("SELECT * FROM vehicles", new VehicleMapper());
        return vehicles;
    }

    private class VehicleMapper implements RowMapper<Vehicle> {
        @Override
        public Vehicle mapRow(ResultSet resultSet, int i) throws SQLException {
            Vehicle innerVehicle = new Vehicle();
            innerVehicle.setId(resultSet.getInt(1));
            innerVehicle.setX(resultSet.getInt(2));
            innerVehicle.setY(resultSet.getInt(3));
            return innerVehicle;
        }
    }
}
