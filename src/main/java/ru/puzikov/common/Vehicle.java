package ru.puzikov.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by SBT-Puzikov-AYU on 28.11.2016.
 */

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int x;
    @Column
    private int y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
