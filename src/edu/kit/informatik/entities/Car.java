package edu.kit.informatik.entities;

public class Car extends SimulationEntity {

    private static final String ARGUMENT_REGEX = "\\d+,\\d+,\\d+,\\d+";
    private final int id;
    private final int wantedSpeed;
    private final int acceleration;
    private int streetId;
    private int speed;
    private int position;

    public Car(String arguments) {
        super(arguments, ARGUMENT_REGEX);
        String[] args = arguments.split(",");
        id = parseNumber(args[0], ID, 0, Integer.MAX_VALUE);
        streetId = parseNumber(args[1], ID, 0, Integer.MAX_VALUE);
        wantedSpeed = parseNumber(args[2], SPEED, 20, 40);
        acceleration = parseNumber(args[3], ACCELERATION, 1, 10);
        speed = 0;
    }

    public int getId() {
        return id;
    }

    public int getWantedSpeed() {
        return wantedSpeed;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStreetId() {
        return streetId;
    }

    public int getPosition() {
        return position;
    }

    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
