package edu.kit.informatik.exceptions;

/**
 * An exception used when the input of the user is wrong.
 *
 * @author uswry
 * @version 1.0
 */
public class SimulatorException extends IllegalArgumentException {

    /**
     * Calling the super constructor with the error message.
     *
     * @param errorMessage - The error message to throw
     */
    public SimulatorException(String errorMessage) {
        super(errorMessage);
    }
}
