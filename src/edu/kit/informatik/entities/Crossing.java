package edu.kit.informatik.entities;

public class Crossing extends SimulationEntity {

    private static final String ARGUMENT_REGEX = "\\d+:\\d+";
    private final int id;
    private final int duration;

    public Crossing(String arguments) {
        super(arguments, ARGUMENT_REGEX);
        String[] args = arguments.split("\\D+");
        id = parseNumber(args[0], ID, 0, Integer.MAX_VALUE);
        duration = parseNumber(args[1], GREEN_INDICATOR, 0, Integer.MAX_VALUE);
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }
}
