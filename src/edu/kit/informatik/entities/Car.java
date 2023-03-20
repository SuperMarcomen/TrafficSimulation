package edu.kit.informatik.entities;

/**
 * A class that represents a car in the simulation.
 *
 * @author uswry
 * @version 1.0
 */
public class Car extends SimulationEntity implements Cloneable {

    private static final String ARGUMENT_REGEX = "\\d+,\\d+,\\d+,\\d+";
    private static final int MIN_ID = 0;
    private static final int MIN_SPEED = 20;
    private static final int MAX_SPEED = 40;
    private static final int MIN_ACCELERATION = 1;
    private static final int MAX_ACCELERATION = 10;
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

    /**
     * Parses the given arguments and checks if it is
     * a valid input.
     *
     * @param arguments - A string that contains the relevant information of the car
     */
    public Car(String arguments) {
        super(arguments, ARGUMENT_REGEX);
        String[] args = arguments.split(",");
        id = parseNumber(args[0], ID, MIN_ID, Integer.MAX_VALUE);
        streetId = parseNumber(args[1], ID, MIN_ID, Integer.MAX_VALUE);
        wantedSpeed = parseNumber(args[2], SPEED, MIN_SPEED, MAX_SPEED);
        acceleration = parseNumber(args[3], ACCELERATION, MIN_ACCELERATION, MAX_ACCELERATION);
        speed = 0;
        updated = false;
        turned = false;
        overtake = false;
        desiredDirection = 0;
    }

    @Override
    public Car clone() {
        try {
            return (Car) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the wanted speed.
     * @return the wanted speed
     */
    public int getWantedSpeed() {
        return wantedSpeed;
    }

    /**
     * Returns the acceleration.
     * @return the acceleration
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Returns the speed.
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the street id.
     * @return the street id
     */
    public int getStreetId() {
        return streetId;
    }

    /**
     * Returns the position.
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Returns if the car is updated.
     * @return if the car is updated
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * Returns if the car has turned.
     * @return if the car has turned
     */
    public boolean hasTurned() {
        return turned;
    }

    /**
     * Returns if the car has overtaken.
     * @return if the car has overtaken
     */
    public boolean hasOvertaken() {
        return overtake;
    }

    /**
     * Returns the desired direction.
     * @return the desired direction
     */
    public int getDesiredDirection() {
        return desiredDirection;
    }

    /**
     * Sets the street id.
     * @param streetId - The id of the new street
     */
    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    /**
     * Sets the speed.
     * @param speed - The new speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Sets the position.
     * @param position - The new position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Sets if the car is updated.
     * @param updated - The new value for the variable updated
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    /**
     * Sets if the car has turned.
     * @param turned - The new value for the variable turned
     */
    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    /**
     * Sets if the car has overtaken.
     * @param overtake - The new value for the variable overtake
     */
    public void setOvertaken(boolean overtake) {
        this.overtake = overtake;
    }

    /**
     * Sets the desired direction.
     * @param desiredDirection - The new desired direction
     */
    public void setDesiredDirection(int desiredDirection) {
        this.desiredDirection = desiredDirection;
    }
}
