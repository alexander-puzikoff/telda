package ru.puzikov;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.puzikov.dao.MainDAO;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainDAOTest {

    @Autowired
    MainDAO mainDAO;

    @Test
    public void contextLoads() {
        Objects.requireNonNull(mainDAO);
    }

}
