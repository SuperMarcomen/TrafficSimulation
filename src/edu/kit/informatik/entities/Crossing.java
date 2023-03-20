package edu.kit.informatik.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a crossing in the simulation.
 *
 * @author uswry
 * @version 1.0
 */
public class Crossing extends SimulationEntity {

    private static final String ARGUMENT_REGEX = "\\d+:\\d+t";
    private static final int MIN_ID = 0;
    private static final int MIN_DURATION = 0;
    private static final int MAX_DURATION = 10;
    private final int id;
    private final int duration;
    private final List<Integer> incomingStreets;
    private final List<Integer> outgoingStreets;
    private int ticks;
    private int greenStreetId;

    /**
     * Parses the given arguments and checks if it is
     * a valid input.
     *
     * @param arguments - A string that contains the relevant information of the crossing
     * @param streets - A list of all streets contained in this simulation
     */
    public Crossing(String arguments, List<Street> streets) {
        super(arguments, ARGUMENT_REGEX);
        String[] args = arguments.split("\\D+");
        id = parseNumber(args[0], ID, MIN_ID, Integer.MAX_VALUE);
        duration = parseNumber(args[1], GREEN_INDICATOR, MIN_DURATION, MAX_DURATION);
        incomingStreets = new ArrayList<>();
        outgoingStreets = new ArrayList<>();
        for (Street street : streets) {
            if (street.getStartNode() == id) outgoingStreets.add(street.getId());
            if (street.getEndNode() == id) incomingStreets.add(street.getId());
        }
        ticks = duration;
        greenStreetId = 0;
    }

    /**
     * Returns the id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the duration.
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns a list with the ids of the incoming streets.
     * @return a list with the ids of the incoming streets.
     */
    public List<Integer> getIncomingStreets() {
        return incomingStreets;
    }

    /**
     * Returns a list with the ids of the outgoing streets.
     * @return a list with the ids of the outgoing streets.
     */
    public List<Integer> getOutgoingStreets() {
        return outgoingStreets;
    }

    /**
     * Returns if the street has the green indicator.
     * @param streetId - The id of the street to be checked
     * @return if the street has the green indicator
     */
    public boolean hasStreetGreen(int streetId) {
        if (duration == 0) return true;
        return incomingStreets.get(greenStreetId) == streetId;
    }

    /**
     * Returns the ticks, representing the state of the green indicator switch.
     * @return the ticks
     */
    public int getTicks() {
        return ticks;
    }

    /**
     * Returns the id of the street that has the green indicator.
     * @return the id of the street that has the green indicator
     */
    public int getGreenStreetId() {
        return greenStreetId;
    }

    /**
     * Sets the green street id.
     * @param greenStreetId - The id of the street with the green indicator
     */
    public void setGreenStreetId(int greenStreetId) {
        this.greenStreetId = greenStreetId;
    }

    /**
     * Sets the ticks.
     * @param ticks - The new ticks
     */
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
