package edu.kit.informatik.entities;

import java.util.ArrayList;
import java.util.List;

public class Crossing extends SimulationEntity {

    private static final String ARGUMENT_REGEX = "\\d+:\\d+";
    private final int id;
    private final int duration;
    private final List<Integer> incomingStreets;
    private final List<Integer> outgoingStreets;
    private boolean crossable;

    public Crossing(String arguments, List<Street> streets) {
        super(arguments, ARGUMENT_REGEX);
        String[] args = arguments.split("\\D+");
        id = parseNumber(args[0], ID, 0, Integer.MAX_VALUE);
        duration = parseNumber(args[1], GREEN_INDICATOR, 0, Integer.MAX_VALUE);
        incomingStreets = new ArrayList<>();
        outgoingStreets = new ArrayList<>();
        for (Street street : streets) {
            if (street.getStartNode() == id) outgoingStreets.add(street.getId());
            if (street.getEndNode() == id) incomingStreets.add(street.getId());
        }
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public List<Integer> getIncomingStreets() {
        return incomingStreets;
    }

    public List<Integer> getOutgoingStreets() {
        return outgoingStreets;
    }

    public boolean isCrossable() {
        if (duration == 0) return true;
        return crossable;
    }
}
