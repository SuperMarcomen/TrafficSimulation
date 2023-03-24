package edu.kit.informatik.entities;

/**
 * A class to represent the streets in the simulation.
 *
 * @author uswry
 * @version 1.0
 */
public class Street extends SimulationEntity {

    private static final String ARGUMENT_REGEX = "\\d+-->\\d+:\\d+m,[1-2]x,\\d{1,2}max";
    private static final int MIN_ID = 0;
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 10000;
    private static final int SINGLE_LANE = 1;
    private static final int MULTIPLE_LANE = 2;
    private static final int MIN_SPEED = 5;
    private static final int MAX_SPEED = 40;
    private final int id;
    private final int startNode;
    private final int endNode;
    private final int length;
    private final int maxSpeed;
    private final boolean multipleLane;

    /**
     * Parses the given arguments and checks if it is
     * a valid input.
     *
     * @param arguments - A string that contains the relevant information of the street
     * @param id - The id of the street
     */
    public Street(String arguments, int id) {
        super(arguments, ARGUMENT_REGEX);
        this.id = id;
        String[] args = arguments.split("\\D+");
        int readIndex = 0;
        startNode = parseNumber(args[readIndex++], ID, MIN_ID, Integer.MAX_VALUE);
        endNode = parseNumber(args[readIndex++], ID, MIN_ID, Integer.MAX_VALUE);
        length = parseNumber(args[readIndex++], LENGTH, MIN_LENGTH, MAX_LENGTH);
        multipleLane = parseNumber(args[readIndex++], STREET_TYPE, SINGLE_LANE, MULTIPLE_LANE) == MULTIPLE_LANE;
        maxSpeed = parseNumber(args[readIndex], SPEED, MIN_SPEED, MAX_SPEED);
    }

    /**
     * Returns the id of the street.
     * @return the id of the street
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the start node.
     * @return the start node
     */
    public int getStartNode() {
        return startNode;
    }

    /**
     * Returns the end node.
     * @return the end node
     */
    public int getEndNode() {
        return endNode;
    }

    /**
     * Returns the length.
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the maximal speed.
     * @return the maximal speed
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Returns if the street has multiple lanes.
     * @return if the street has multiple lanes
     */
    public boolean isMultipleLane() {
        return multipleLane;
    }
}
