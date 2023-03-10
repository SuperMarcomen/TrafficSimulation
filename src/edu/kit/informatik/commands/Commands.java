package edu.kit.informatik.commands;

/**
 * Handles the execution of all commands
 *
 * @author uswry
 * @version 1.0
 */
public class Commands extends TreeCommand {

    @Override
    public String getDefaultErrorMessage() {
        return "Command not recognized";
    }

    @Override
    public String getNoArgMessage() {
        return "Command not recognized";
    }


}
