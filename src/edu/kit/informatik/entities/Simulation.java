package edu.kit.informatik.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Simulation {

    private final Map map;

    public Simulation(Map map) {
        this.map = map;
    }

    public void simulate(int ticks) {
        for (int i = 0; i < ticks; i++) {
            simulate();
        }
    }

    private void simulate() {
        for (Street street : map.getStreets()) {
            List<Car> cars = map.getCarsOnStreet(street.getId());
            cars.sort(Comparator.comparing(Car::getPosition).reversed());
            for (Car car : cars) {
                int speed = Math.min(car.getSpeed() + car.getAcceleration(), Math.min(car.getWantedSpeed(), street.getMaxSpeed()));
                car.setSpeed(speed);
            }
        }
    }

    public Map getMap() {
        return map;
    }
}
