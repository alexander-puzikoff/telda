package ru.puzikov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.puzikov.common.Vehicle;
import ru.puzikov.service.VehicleService;

import java.util.List;

/**
 * Created by APuzikov on 24.12.2016.
 */
@Controller
@RequestMapping("rest")
public class VehiclesRestController {

    @Autowired
    VehicleService service;

    @RequestMapping(value = {"/", "/vehicle"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Vehicle> index() {
        return service.getAllVehicles();
    }

    @RequestMapping(value = "/vehicle", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void putVehicle(@RequestBody final Vehicle entity) {
        service.saveOrUpdateVehicle(entity);
    }
}
