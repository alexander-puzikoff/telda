package ru.puzikov.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.puzikov.dao.MainDAOImpl;

/**
 * Created by APuzikov on 11.12.2016.
 */

@RestController
public class MainController {

    @Autowired
    private MainDAOImpl repo;

    @RequestMapping("/")
    public String index() {
        return "hello world!" + repo.getVehicles();
    }

}
