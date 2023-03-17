package edu.kit.informatik.entities;

import edu.kit.informatik.exceptions.SimulatorException;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private static final int MINIMAL_DISTANCE = 10;
    public static final String TOO_MANY_CARS = "There are too many cars on street %d";
    public static final String STREET_SAME_START_END = "The street %d has the same starting and ending node!";
    public static final String NOT_FOUND = "No %s was found with the id %d";
    public static final String STREET = "street";
    public static final String CROSSING = "crossing";
    public static final String CAR = "car";
    public static final int MIN_TICK_DURATION = 3;
    public static final int MAX_STREETS = 4;
    public static final String INVALID_CROSSING_DURATION = "The crossing %d has an invalid duration: %d!";
    public static final String NON_EXISTING_NODE = "Street %d is connected to a non-existing node";
    public static final String INVALID_AMOUNT_OF_INCOMING_STREETS = "Crossing %d has an invalid amount of incoming streets";
    public static final String INVALID_AMOUNT_OF_OUTGOING_STREETS = "Crossing %d has an invalid amount of outgoing streets";
    public static final String CROSSING_ID_NOT_UNIQUE = "Crossing id %d is not unique";
    public static final String CAR_ID_NOT_UNIQUE = "Crossing id %d is not unique";
    public static final String CAR_NON_EXISTING_STREET = "Car %d is placed on a non-existing street!";
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

    public void checkAndInitMap() {
        positionCars();
        for (Street street : streets) {
            if (street.getStartNode() == street.getEndNode()) {
                throw new SimulatorException(String.format(STREET_SAME_START_END, street.getId()));
            }

            boolean startsExist = false;
            boolean endExists = false;
            for (Crossing crossing : crossings) {
                if (street.getStartNode() == crossing.getId()) startsExist = true;
                if (street.getEndNode() == crossing.getId()) endExists = true;
            }

            if (!startsExist || !endExists) {
                throw new SimulatorException(NON_EXISTING_NODE.formatted(street.getId()));
            }
        }

        // Every crossing has at least one street
        List<Integer> crossingIds = new ArrayList<>();
        for (Crossing crossing : crossings) {
            int crossingId = crossing.getId();
            if (crossingIds.contains(crossingId)) {
                throw new SimulatorException(CROSSING_ID_NOT_UNIQUE.formatted(crossingId));
            }
            crossingIds.add(crossingId);
            if (0 < crossing.getDuration() && crossing.getDuration() < MIN_TICK_DURATION) {
                throw new SimulatorException(INVALID_CROSSING_DURATION.formatted(crossingId, crossing.getDuration()));
            }
            if (crossing.getIncomingStreets().size() < 1 || crossing.getIncomingStreets().size() > MAX_STREETS) {
                throw new SimulatorException(INVALID_AMOUNT_OF_INCOMING_STREETS.formatted(crossingId));
            }

            if (crossing.getOutgoingStreets().size() < 1 || crossing.getOutgoingStreets().size() > MAX_STREETS) {
                throw new SimulatorException(INVALID_AMOUNT_OF_OUTGOING_STREETS.formatted(crossingId));
            }
        }

        List<Integer> carIds = new ArrayList<>();
        for (Car car : cars) {
            int carId = car.getId();
            if (carIds.contains(carId)) {
                throw new SimulatorException(CAR_ID_NOT_UNIQUE.formatted(carId));
            }
            carIds.add(carId);

            boolean valid = false;
            for (Street street : streets) {
                if (street.getId() == car.getStreetId()) {
                    valid = true;
                    break;
                }
            }
            if (!valid)
                throw new SimulatorException(CAR_NON_EXISTING_STREET.formatted(carId));
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
