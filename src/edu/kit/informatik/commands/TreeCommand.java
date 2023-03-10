package edu.kit.informatik.commands;

import edu.kit.informatik.exceptions.SimulatorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A class to handle commands that can have subcommands.
 *
 * @author uswry
 * @version 1.0
 */
public abstract  class TreeCommand extends Command {

    /**
     * A map matching a subcommand to its String representation.
     */
    protected final HashMap<String, Command> commands = new HashMap<>();

    @Override
    public List<String> executeDefault() {
        Command command = getCommandToExecute(input);
        if (command == this) {
            return execute();
        } else {
            return command.executeDefault();
        }
    }

    @Override
    public List<String> execute() {
        List<String> arguments = new ArrayList<>(Arrays.stream(input.split(" ")).toList());
        char lastChar = input.charAt(input.length() - 1);
        if (Character.isSpaceChar(lastChar)) arguments.add(String.valueOf(lastChar));

        Command command = getCommandToExecute(arguments.get(0));
        String truncatedInput = input.length() > arguments.get(0).length()
                ? input.substring(arguments.get(0).length() + 1) : "";
        command.setInput(truncatedInput);
        return command.execute();
    }

    @Override
    public boolean canExecute(String input) {
        this.input = input;
        List<String> arguments = new ArrayList<>(Arrays.stream(input.split(" ")).toList());
        if (input.isBlank()) {
            throw new SimulatorException(getNoArgMessage());
        }
        char lastChar = input.charAt(input.length() - 1);
        if (Character.isSpaceChar(lastChar)) arguments.add(String.valueOf(lastChar));

        Command command = getCommandToExecute(arguments.get(0));
        if (command == this) {
            throw new SimulatorException(getDefaultErrorMessage());
        } else {
            String truncatedInput = input.length() > arguments.get(0).length()
                    ? input.substring(arguments.get(0).length() + 1) : "";
            if (truncatedInput.isEmpty() && Character.isSpaceChar(lastChar)) {
                truncatedInput = String.valueOf(lastChar);
            }
            return command.canExecute(truncatedInput);
        }
    }

    @Override
    public String getCorrectFormat() {
        return getCommandToExecute(input.split(" ")[0]).getCorrectFormat();
    }

    /**
     * Gets the default error message when the input is incorrect.
     *
     * @return the default error message when the input is incorrect
     */
    public abstract String getDefaultErrorMessage();

    /**
     * Gets the default error message when there is no input.
     *
     * @return the default error message when there is no input
     */
    public abstract String getNoArgMessage();

    /**
     * Gets the command to execute from the HashMap.
     *
     * @param argument - The argument for the command
     * @return the command to execute
     */
    protected Command getCommandToExecute(String argument) {
        if (!commands.isEmpty()) {
            Command command = commands.get(argument);
            if (command != null) return command;
        }
        return this;
    }

    /**
     * Registers a subcommands and adds it to the HashMap.
     *
     * @param name - The name of the subcommand
     * @param command - The instance of the subcommand
     */
    public void registerSubCommand(String name, Command command) {
        commands.put(name, command);
    }
}
