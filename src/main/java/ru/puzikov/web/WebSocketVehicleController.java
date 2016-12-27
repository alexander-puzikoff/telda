package ru.puzikov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.puzikov.common.Vehicle;
import ru.puzikov.service.VehicleService;

import java.util.List;

/**
 * Created by APuzikov on 27.12.2016.
 */

@Controller
public class WebSocketVehicleController {

    @Autowired
    VehicleService service;

    @MessageMapping("/coordinates")
    @SendTo("/result")
    public List<Vehicle> setCoordinates(int x1, int x2, int y1, int y2) {
        return service.getAllVehiclesInArea(x1, x2, y1, y2); //
    }
}
