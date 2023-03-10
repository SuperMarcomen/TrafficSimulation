package edu.kit.informatik.commands;

import edu.kit.informatik.exceptions.SimulatorException;
import edu.kit.informatik.io.ErrorLogger;

/**
 * A class to handle commands with input.
 *
 * @author uswry
 * @version 1.0
 */
public abstract class InputCommand extends Command {

    private static final String INPUT_ERROR_MESSAGE = "This command requires these parameters: %s";
    private static final String NUMBER_NOT_ACCEPTED_RANGE = "The number you wrote is not in the accepted range!";
    private final String pattern;
    private final String correctFormat;

    /**
     * Initializing the regex pattern and the correct format of the input.
     *
     * @param pattern - The regex pattern for the input
     * @param correctFormat - The correct format of the input
     */
    public InputCommand(String pattern, String correctFormat) {
        this.pattern = pattern;
        this.correctFormat = correctFormat;
    }

    @Override
    public boolean canExecute(String input) {
        return input.matches(pattern);
    }

    @Override
    public String getCorrectFormat() {
        return ErrorLogger.format(String.format(INPUT_ERROR_MESSAGE, correctFormat));
    }

    public int parseInput(String input, int min) {
        try {
            int number = Integer.parseInt(input);
            if (number < min) throw new SimulatorException(NUMBER_NOT_ACCEPTED_RANGE);
            return number;
        } catch (NumberFormatException exception) {
            throw new SimulatorException(NUMBER_NOT_ACCEPTED_RANGE);
        }
    }
}
