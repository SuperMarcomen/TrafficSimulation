package edu.kit.informatik.entities;

import edu.kit.informatik.exceptions.SimulatorException;

public abstract class SimulationEntity {

    protected static final String ID = "ID";
    protected static final String SPEED = "speed";
    protected static final String ACCELERATION = "acceleration";
    protected static final String GREEN_INDICATOR = "amount of tick";
    protected static final String STREET_TYPE = "street type";
    protected static final String LENGTH = "length";
    private static final String INCORRECT_INPUT = "The line %s in the file is wrong!";
    private static final String INPUT_NOT_VALID_NUMBER = "%s is not a valid number for the %s!";


    public SimulationEntity(String regex, String arguments) {
        if (arguments.matches(regex)) {
            throw new SimulatorException(INCORRECT_INPUT);
        }
    }

    public int parseNumber(String input, String type, int min, int max) {
        try {
            int id = Integer.parseInt(input);
            if (id < min || id > max) {
                throw new SimulatorException(String.format(INPUT_NOT_VALID_NUMBER, input, type));
            }
            return id;
        } catch (NumberFormatException exception) {
            throw new SimulatorException(String.format(INPUT_NOT_VALID_NUMBER, input, type));
        }
    }
}
