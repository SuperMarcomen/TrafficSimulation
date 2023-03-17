package edu.kit.informatik.entities;

import java.util.ArrayList;
import java.util.List;

public class Crossing extends SimulationEntity {

    private static final String ARGUMENT_REGEX = "\\d+:\\d+t";
    private final int id;
    private final int duration;
    private final List<Integer> incomingStreets;
    private final List<Integer> outgoingStreets;
    private int ticks;
    private int greenStreetId;

    public Crossing(String arguments, List<Street> streets) {
        super(arguments, ARGUMENT_REGEX);
        String[] args = arguments.split("\\D+");
        id = parseNumber(args[0], ID, 0, Integer.MAX_VALUE);
        duration = parseNumber(args[1], GREEN_INDICATOR, 0, 10);
        incomingStreets = new ArrayList<>();
        outgoingStreets = new ArrayList<>();
        for (Street street : streets) {
            if (street.getStartNode() == id) outgoingStreets.add(street.getId());
            if (street.getEndNode() == id) incomingStreets.add(street.getId());
        }
        ticks = duration;
        greenStreetId = 0;
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

    public boolean hasStreetGreen(int streetId) {
        if (duration == 0) return true;
        return incomingStreets.get(greenStreetId) == streetId;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public int getGreenStreetId() {
        return greenStreetId;
    }

    public void setGreenStreetId(int greenStreetId) {
        this.greenStreetId = greenStreetId;
    }
}
