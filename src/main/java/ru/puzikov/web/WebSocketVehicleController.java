package ru.puzikov.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.puzikov.common.Vehicle;
import ru.puzikov.service.VehicleService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by APuzikov on 27.12.2016.
 */

@Controller
public class WebSocketVehicleController {

    @Autowired
    VehicleService service;

    @MessageMapping("/coordinates")
    @SendTo("/result/vehicles")
    public List<Vehicle> setCoordinates(Coordinates coordinates) {
        return service.getAllVehiclesInArea(coordinates.getX1(), coordinates.getX2(), coordinates.getY1(), coordinates.getY2()); //
    }

    private static class Coordinates {
        private String x1;
        private String x2;
        private String y1;
        private String y2;
        @JsonCreator
        public Coordinates() {
        }

        public void setX1(String x1) {
            this.x1 = x1;
        }

        public void setX2(String x2) {
            this.x2 = x2;
        }

        public void setY1(String y1) {
            this.y1 = y1;
        }

        public void setY2(String y2) {
            this.y2 = y2;
        }

        public int getX1() {
            return Integer.valueOf(x1);
        }

        public int getX2() {
            return Integer.valueOf(x2);
        }

        public int getY1() {
            return Integer.valueOf(y1);
        }

        public int getY2() {
            return Integer.valueOf(y2);
        }

    }
}
