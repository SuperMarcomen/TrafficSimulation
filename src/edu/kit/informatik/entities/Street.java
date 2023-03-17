package edu.kit.informatik.entities;

public class Street extends SimulationEntity {

    private static final String ARGUMENT_REGEX = "\\d+-->\\d+:\\d+m,[1-2]x,\\d{1,2}max";
    private final int id;
    private final int startNode;
    private final int endNode;
    private final int length;
    private final int maxSpeed;
    private final boolean multipleLane;


    public Street(String arguments, int id) {
        super(arguments, ARGUMENT_REGEX);
        this.id = id;
        String[] args = arguments.split("\\D+");
        startNode = parseNumber(args[0], ID, 0, Integer.MAX_VALUE);
        endNode = parseNumber(args[1], ID, 0, Integer.MAX_VALUE);
        length = parseNumber(args[2], LENGTH, 10, 10000);
        multipleLane = parseNumber(args[3], STREET_TYPE, 1, 2) == 2;
        maxSpeed = parseNumber(args[4], SPEED, 5, 40);
    }

    public int getId() {
        return id;
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public int getLength() {
        return length;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public boolean isMultipleLane() {
        return multipleLane;
    }
}
