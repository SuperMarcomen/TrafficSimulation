package edu.kit.informatik.entities;

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
                int position = car.getPosition();
                updateCarSpeed(car, street);
                simulateCar(car, street, car.getSpeed());
                car.setUpdated(true);
                if (car.getPosition() == position || (position == street.getLength() && car.hasTurned() && car.getPosition() == 0)) {
                    car.setSpeed(0);
                }
            }
        }

        for (Crossing crossing : map.getCrossings()) {
            updateCrossing(crossing);
        }

        for (Car car : map.getCars()) {
            car.setUpdated(false);
            car.setTurned(false);
            car.setOvertaken(false);
        }
    }

    private void updateCrossing(Crossing crossing) {
        if (crossing.getDuration() == 0) return;
        crossing.setTicks(crossing.getTicks() - 1);
        if (crossing.getTicks() > 0) return;
        crossing.setTicks(crossing.getDuration());
        crossing.setGreenStreetId(crossing.getGreenStreetId() + 1);
        if (crossing.getGreenStreetId() <= crossing.getIncomingStreets().size() - 1) return;
        crossing.setGreenStreetId(0);
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

        if (car.hasTurned() || car.hasOvertaken()) return;
        boolean turned = turnCar(car, street);
        if (!turned) return;

        simulateCar(car, map.getStreetFromId(car.getStreetId()), distanceToDrive);
    }

    private boolean turnCar(Car car, Street street) {
        Crossing crossing = map.getCrossingFromIncomingStreetId(street.getId());
        if (!crossing.hasStreetGreen(street.getId())) return false;

        int desiredDirection = car.getDesiredDirection();
        if (desiredDirection > crossing.getOutgoingStreets().size() - 1) {
            desiredDirection = 0;
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
        int newDesiredDirection = car.getDesiredDirection();
        newDesiredDirection++;
        if (newDesiredDirection == 3) newDesiredDirection = 0;
        car.setDesiredDirection(newDesiredDirection);
        car.setTurned(true);
        return true;
    }

    private int drive(Car car, int distance, Street street) {
        // distance + car.getPosition() <= street.getLength()
        List<Car> carsOnStreet = map.getCarsOnStreet(street.getId());
        Car[] precedingCars;
        int restingDistance = distance;
        int newPosition = car.getPosition();
        do {
            precedingCars = getTwoPrecedingCars(car, carsOnStreet);
            if (precedingCars[0] == null) {
                newPosition += restingDistance;
                restingDistance = 0;
                break;
            }

            if ((precedingCars[0].getPosition() - map.getMinimalDistance()) >= (newPosition + restingDistance)) {
                newPosition += restingDistance;
                restingDistance = 0;
                break;
            }

            if (!street.isMultipleLane() || car.hasOvertaken()) {
                newPosition = precedingCars[0].getPosition() - map.getMinimalDistance();
                restingDistance -= newPosition - car.getPosition();
                break;
            }

            if ((newPosition + restingDistance) - map.getMinimalDistance() < precedingCars[0].getPosition()) {
                newPosition = precedingCars[0].getPosition() - map.getMinimalDistance();
                restingDistance -= newPosition - car.getPosition();
                break;
            }

            if (precedingCars[1] == null) {
                newPosition += restingDistance;
                restingDistance = 0;
                break;
            }

            int distanceBetweenNextCars = precedingCars[1].getPosition() - precedingCars[0].getPosition();
            if (distanceBetweenNextCars < 2 * map.getMinimalDistance()) {
                newPosition = precedingCars[0].getPosition() - map.getMinimalDistance();
                restingDistance -= newPosition - car.getPosition();
                break;
            }

            car.setOvertaken(true);
            int distanceDriven = precedingCars[0].getPosition() - car.getPosition() + map.getMinimalDistance();
            restingDistance = drive(car, distance - distanceDriven, street);
            newPosition += distance - restingDistance;

        } while (restingDistance > 0);

        car.setPosition(newPosition);
        return restingDistance;
    }

    private Car[] getTwoPrecedingCars(Car car, List<Car> cars) {
        Car[] precedingCars = new Car[2];
        precedingCars[0] = getPrecedingCar(car, cars);
        if (precedingCars[0] != null) {
            precedingCars[1] = getPrecedingCar(precedingCars[0], cars);
        }
        return precedingCars;
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
