package edu.kit.informatik.entities;

import edu.kit.informatik.exceptions.SimulatorException;

/**
 * A generalisation of a simulation entity with useful methods and constants.
 *
 * @author uswry
 * @version 1.0
 */
public abstract class SimulationEntity {

    /**
     * The string representation of the id.
     */
    protected static final String ID = "ID";
    /**
     * The string representation of the speed.
     */
    protected static final String SPEED = "speed";
    /**
     * The string representation of the acceleration.
     */
    protected static final String ACCELERATION = "acceleration";
    /**
     * The string representation of the green indicator.
     */
    protected static final String GREEN_INDICATOR = "amount of tick";
    /**
     * The string representation of the street type.
     */
    protected static final String STREET_TYPE = "street type";
    /**
     * The string representation of the length.
     */
    protected static final String LENGTH = "length";
    /**
     * The string representation for an incorrect input.
     */
    private static final String INCORRECT_INPUT = "The line %s in the file is wrong!";
    /**
     * The string representation for an invalid number.
     */
    private static final String INPUT_NOT_VALID_NUMBER = "%s is not a valid number for the %s!";


    /**
     * Checks if the input matches the regex and throws an error
     * if that's not the case.
     *
     * @param arguments - The string to be checked
     * @param regex - The regex that has to match
     * @throws SimulatorException if the argument does not match the regex
     */
    public SimulationEntity(String arguments, String regex) {
        if (!arguments.matches(regex)) {
            throw new SimulatorException(String.format(INCORRECT_INPUT, arguments));
        }
    }

    /**
     * Parses a numeric value from a String to an integer and checks if
     * the value is in the accepted range, otherwise it throws an error.
     *
     * @param input - The input to be parsed
     * @param type - The type of the input (Id, speed, ...)
     * @param min - The minimal value for the input
     * @param max - The maximal value for the input
     * @throws SimulatorException if the input is not an integer
     * @throws SimulatorException if the numeric input is not in the accepted range
     * @return the parsed String
     */
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
