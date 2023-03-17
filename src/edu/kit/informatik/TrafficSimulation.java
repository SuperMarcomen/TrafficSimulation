package edu.kit.informatik;

import edu.kit.informatik.commands.Commands;
import edu.kit.informatik.commands.LoadCommand;
import edu.kit.informatik.commands.PositionCommand;
import edu.kit.informatik.commands.QuitCommand;
import edu.kit.informatik.commands.SimulateCommand;
import edu.kit.informatik.exceptions.SimulatorException;
import edu.kit.informatik.io.ErrorLogger;
import edu.kit.informatik.news.Notifier;

import java.util.List;

public final class TrafficSimulation {

    private final Commands commands;
    private boolean running;

    public TrafficSimulation() {
        commands = new Commands();
        Notifier notifier = new Notifier();
        PositionCommand positionCommand = new PositionCommand();
        SimulateCommand simulateCommand = new SimulateCommand();
        notifier.addObserver(positionCommand);
        notifier.addObserver(simulateCommand);
        commands.registerSubCommand("load", new LoadCommand(notifier));
        commands.registerSubCommand("position", positionCommand);
        commands.registerSubCommand("simulate", simulateCommand);
        commands.registerSubCommand("quit", new QuitCommand(this));
        running = true;
    }

    /**
     * Checks if the given command is an input, runs it
     * and gives its output back.
     *
     * @param input - The input from the user
     * @return the output of the command
     */
    public List<String> handleInput(String input) {
        try {
            if (!commands.canExecute(input)) {
                return List.of(ErrorLogger.format(commands.getCorrectFormat()));
            } else {
                return commands.execute();
            }
        } catch (SimulatorException exception) {
            return List.of(ErrorLogger.format(exception.getMessage()));
        }
    }

    public void stop() {
        running = false;
    }

    /**
     * Returns if the simulation is running.
     * @return if the simulation is running
     */
    public boolean isRunning() {
        return running;
    }
}
