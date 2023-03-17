package edu.kit.informatik.entities;

public class Car extends SimulationEntity implements Cloneable {

    private static final String ARGUMENT_REGEX = "\\d+,\\d+,\\d+,\\d+";
    private final int id;
    private final int wantedSpeed;
    private final int acceleration;
    private int streetId;
    private int speed;
    private int position;
    private boolean updated;
    private boolean turned;
    private boolean overtake;
    private int desiredDirection;

    public Car(String arguments) {
        super(arguments, ARGUMENT_REGEX);
        // TODO magic numbers
        String[] args = arguments.split(",");
        id = parseNumber(args[0], ID, 0, Integer.MAX_VALUE);
        streetId = parseNumber(args[1], ID, 0, Integer.MAX_VALUE);
        wantedSpeed = parseNumber(args[2], SPEED, 20, 40);
        acceleration = parseNumber(args[3], ACCELERATION, 1, 10);
        speed = 0;
        updated = false;
        turned = false;
        overtake = false;
        desiredDirection = 0;
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

    public boolean isUpdated() {
        return updated;
    }

    public boolean hasTurned() {
        return turned;
    }

    public int getDesiredDirection() {
        return desiredDirection;
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

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public void setDesiredDirection(int desiredDirection) {
        this.desiredDirection = desiredDirection;
    }

    public boolean hasOvertaken() {
        return overtake;
    }

    public void setOvertaken(boolean overtake) {
        this.overtake = overtake;
    }

    @Override
    public Car clone() {
        try {
            return (Car) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
