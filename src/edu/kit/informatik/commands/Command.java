package edu.kit.informatik.commands;

import java.util.List;

/**
 * A class to handle a command without an input.
 *
 * @author uswry
 * @version 1.0
 */
public abstract class Command {

    private static final String INPUT_ERROR_MESSAGE = "Error: This command does not accept an input!";
    /**
     * The input given by the user.
     */
    protected String input;

    /**
     * Execute default behaviour of the command, without any subcommand.
     *
     * @return the output of the command
     */
    public List<String> executeDefault() {
        return execute();
    }

    /**
     * Executes the command
     *
     * @return the output of the command
     */
    protected abstract List<String> execute();

    /**
     * Checks if the command can be executed with the given input.
     *
     * @param input - The user input
     * @return true if the command can be executed
     */
    public boolean canExecute(String input) {
        this.input = input;
        return input.isEmpty();
    }

    /**
     * Sets the inputs.
     *
     * @param input - The input
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Gets the correct format.
     *
     * @return - The correct format
     */
    public String getCorrectFormat() {
        return INPUT_ERROR_MESSAGE;
    }
}
