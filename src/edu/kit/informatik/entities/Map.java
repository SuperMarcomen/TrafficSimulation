package edu.kit.informatik.entities;

import edu.kit.informatik.exceptions.SimulatorException;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private static final int MINIMAL_DISTANCE = 10;
    public static final String TOO_MANY_CARS = "There are too many cars on street %d";
    public static final String NOT_CONNECTED_TO_STREET = "The crossing %d is not connected to any street!";
    public static final String NOT_FOUND = "No %s was found with the id %d";
    public static final String STREET = "street";
    public static final String CROSSING = "crossing";
    public static final String CAR = "car";
    private final List<Street> streets;
    private final List<Crossing> crossings;
    private final List<Car> cars;

    public Map(List<String> streetsString, List<String> crossingStrings, List<String> carsStrings) {
        streets = new ArrayList<>();
        crossings = new ArrayList<>();
        cars = new ArrayList<>();
        for (int i = 0; i < streetsString.size(); i++) {
            streets.add(new Street(streetsString.get(i), i));
        }
        for (String crossingString : crossingStrings) {
            crossings.add(new Crossing(crossingString, streets));
        }
        for (String carsString : carsStrings) {
            cars.add(new Car(carsString));
        }
    }

    public boolean haveCarsSafeDistance(Car first, Car second) {
        return Math.abs(second.getPosition() - first.getPosition()) >= MINIMAL_DISTANCE;
    }

    public void checkAndInitMap() {
        positionCars();
        // Every crossing has at least one street
        for (Crossing crossing : crossings) {
            int crossingId = crossing.getId();
            boolean valid = false;
            for (Street street : streets) {
                if (street.getStartNode() == crossingId || street.getEndNode() == crossingId) {
                    valid = true;
                    break;
                }
            }
            if (!valid)
                throw new SimulatorException(String.format(NOT_CONNECTED_TO_STREET, crossing.getId()));
        }
    }

    public int getMinimalDistance() {
        return MINIMAL_DISTANCE;
    }

    private void positionCars() {
        for (Street street : streets) {
            int position = street.getLength();
            List<Car> carsOnStreet = getCarsOnStreet(street.getId());
            for (Car car : carsOnStreet) {
                if (position < 0) {
                    throw new SimulatorException(String.format(TOO_MANY_CARS, street.getId()));
                }
                car.setPosition(position);
                position -= MINIMAL_DISTANCE;
            }
        }
    }

    public List<Car> getCarsOnStreet(int streetId) {
        List<Car> carsOnStreet = new ArrayList<>();
        for (Car car : cars) {
            if (car.getStreetId() != streetId) continue;
            carsOnStreet.add(car);
        }
        return carsOnStreet;
    }

    public Street getStreetFromId(int id) {
        for (Street street : streets) {
            if (street.getId() == id) return street;
        }
        throw new SimulatorException(String.format(NOT_FOUND, STREET, id));
    }

    private Crossing getCrossingFromId(int id) {
        for (Crossing crossing : crossings) {
            if (crossing.getId() == id) return crossing;
        }
        throw new SimulatorException(String.format(NOT_FOUND, CROSSING, id));
    }

    public Crossing getCrossingFromIncomingStreetId(int id) {
        for (Crossing crossing : crossings) {
            if (crossing.getIncomingStreets().contains(id))
                return crossing;
        }
        throw new SimulatorException(String.format(NOT_FOUND, CROSSING, id));
    }

    public Car getCarFromId(int id) {
        for (Car car : cars) {
            if (car.getId() == id) return car;
        }
        throw new SimulatorException(String.format(NOT_FOUND, CAR, id));
    }

    public List<Street> getStreets() {
        return streets;
    }

    public List<Crossing> getCrossings() {
        return crossings;
    }

    public List<Car> getCars() {
        return cars;
    }
}
