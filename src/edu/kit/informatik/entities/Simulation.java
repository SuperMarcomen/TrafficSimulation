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
            sortCarsByPosition(cars);
            for (Car car : cars) {
                if (car.isUpdated()) continue;
                updateCarSpeed(car, street);
                simulateCar(car, street, car.getSpeed());
                car.setUpdated(true);
                /*int newPosition = car.getPosition() + speed;
                int restMovement = 0;
                if (newPosition > streetLength) {
                    restMovement = newPosition - streetLength;
                    newPosition = streetLength;
                }

                Car precedingCar = getPrecedingCar(car, cars);
                if (precedingCar == null) {
                    if (restMovement == 0) {
                        car.setPosition(newPosition);
                    } else { // car has to turn
                        Crossing crossing = map.getCrossingFromIncomingStreetId(street.getId());
                        int desiredDirection = car.getDesiredDirection();
                        boolean increase = true;
                        if (desiredDirection > crossing.getOutgoingStreets().size() - 1) {
                            desiredDirection = 0;
                            increase = false;
                        }

                        Street newStreet = map.getStreetFromId(crossing.getOutgoingStreets().get(desiredDirection));
                        List<Car> carsOnNewStreet = map.getCarsOnStreet(newStreet.getId());
                        Car lastCar = carsOnNewStreet.get(carsOnNewStreet.size() - 1);
                        if (!crossing.isCrossable()) {
                            car.setPosition(streetLength);
                            continue;
                        }
                        if (map.haveCarsSafeDistance(car, lastCar)) {
                            car.setStreetId(desiredDirection);
                            car.setPosition(restMovement);
                            if (increase) car.setDesiredDirection(car.getDesiredDirection() + 1);
                        } else { // car stays in street
                            car.setPosition(streetLength);
                        }
                    }
                } else {
                    if (map.haveCarsSafeDistance(car, precedingCar)) {
                        car.setPosition(newPosition);
                    } else {
                        car.setPosition(precedingCar.getPosition() - map.getMinimalDistance());
                    }
                }*/
            }
        }

        for (Car car : map.getCars()) {
            car.setUpdated(false);
            car.setTurned(false);
        }
    }

    private void updateCarSpeed(Car car, Street street) {
        int speed = Math.min(car.getSpeed() + car.getAcceleration(), Math.min(car.getWantedSpeed(), street.getMaxSpeed()));
        car.setSpeed(speed);
    }

    private void simulateCar(Car car, Street street, int distance) {
        int streetLength = street.getLength();

        int newPosition = car.getPosition() + distance;
        if (newPosition <= streetLength) {
            drive(car, distance, street);
            return;
        }
        // car has to turn
        int distanceOnCurrentStreet = streetLength - car.getPosition();
        int distanceToDrive = distance;
        if (distanceOnCurrentStreet != 0) {
            drive(car, distanceOnCurrentStreet, street);
            if (car.getPosition() != streetLength) return;
            distanceToDrive -= distanceOnCurrentStreet;
        }

        if (car.hasTurned()) return;
        boolean turned = turnCar(car, street);
        if (!turned) return;

        simulateCar(car, map.getStreetFromId(car.getStreetId()), distanceToDrive);
    }

    private boolean turnCar(Car car, Street street) {
        Crossing crossing = map.getCrossingFromIncomingStreetId(street.getId());
        if (!crossing.isCrossable()) return false;

        int desiredDirection = car.getDesiredDirection();
        boolean increase = true;
        if (desiredDirection > crossing.getOutgoingStreets().size() - 1) {
            desiredDirection = 0;
            increase = false;
        }

        Street newStreet = map.getStreetFromId(crossing.getOutgoingStreets().get(desiredDirection));
        List<Car> carsOnNewStreet = map.getCarsOnStreet(newStreet.getId());
        if (!carsOnNewStreet.isEmpty()) {
            sortCarsByPosition(carsOnNewStreet);
            Car lastCar = carsOnNewStreet.get(carsOnNewStreet.size() - 1);
            if (lastCar.getPosition() < map.getMinimalDistance()) return false;
        }

        car.setStreetId(newStreet.getId());
        car.setPosition(0);
        desiredDirection += increase ? 1 : 0;
        car.setDesiredDirection(desiredDirection);
        car.setTurned(true);
        return true;
    }

    private int drive(Car car, int distance, Street street) {
        // distance + car.getPosition() <= street.getLength()
        List<Car> carsOnStreet = map.getCarsOnStreet(street.getId());
        Car precedingCar;
        int restingDistance = distance;
        int newPosition = car.getPosition();
        do {
            precedingCar = getPrecedingCar(car, carsOnStreet);
            if (precedingCar == null) {
                newPosition += restingDistance;
                restingDistance = 0;
                break;
            }
            if ((precedingCar.getPosition() - map.getMinimalDistance()) >= (newPosition + restingDistance)) {
                newPosition += restingDistance;
                restingDistance = 0;
                break;
            }

            if (!street.isMultipleLane()) {
                newPosition = precedingCar.getPosition() - map.getMinimalDistance();
                restingDistance -= newPosition - car.getPosition();
                break;
            }

            newPosition = precedingCar.getPosition();
            int restingDistanceAfterOvertake = restingDistance - precedingCar.getPosition() - car.getPosition();

            if (restingDistanceAfterOvertake < map.getMinimalDistance()) {
                newPosition = precedingCar.getPosition() - map.getMinimalDistance();
                restingDistance -= newPosition - car.getPosition();
                break;
            }

            Car newCar = car.clone();
            newCar.setPosition(newPosition);
            int newRestingDistance = drive(newCar, restingDistanceAfterOvertake, street);
            if (newRestingDistance == 0) {
                newPosition += restingDistance;
                restingDistance = 0;
                break;
            }
            int positionAfterOvertake = restingDistanceAfterOvertake - newRestingDistance;

            if (positionAfterOvertake - newPosition < map.getMinimalDistance()) {
                newPosition = precedingCar.getPosition() - map.getMinimalDistance();
                restingDistance -= newPosition - car.getPosition();
                break;
            }


        } while (restingDistance > 0);

        car.setPosition(newPosition);
        return restingDistance;
    }

    private Car getPrecedingCar(Car car, List<Car> cars) {
        sortCarsByPosition(cars);
        for (int i = 0; i < cars.size(); i++) {
            Car currentCar = cars.get(i);
            if (currentCar != car) continue;

            if (i == 0) return null;
            return cars.get(i - 1);
        }
        return null;
    }

    private void sortCarsByPosition(List<Car> cars) {
        cars.sort(Comparator.comparing(Car::getPosition).reversed());
    }

    public Map getMap() {
        return map;
    }
}
