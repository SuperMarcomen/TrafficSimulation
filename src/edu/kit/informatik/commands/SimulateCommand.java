package edu.kit.informatik.commands;

import edu.kit.informatik.entities.Simulation;
import edu.kit.informatik.io.ErrorLogger;
import edu.kit.informatik.news.Observer;

import java.util.List;

public class SimulateCommand extends InputCommand implements Observer {

    private static final String ARGUMENT_REGEX = "\\d+";
    private static final String CORRECT_FORMAT = "This command needs an amount of ticks to function!";
    private static final String NO_SIMULATION = "To run this command you first need to load a simulation!";
    private Simulation simulation;

    /**
     * Initializing the regex pattern and the correct format of the input.
     */
    public SimulateCommand() {
        super(ARGUMENT_REGEX, CORRECT_FORMAT);
    }

    @Override
    protected List<String> execute() {
        if (simulation == null) return List.of(ErrorLogger.format(NO_SIMULATION));
        int ticks = parseInput(input, 1);
        simulation.simulate(ticks);
        return List.of("READY");
    }

    @Override
    public void update(Object object) {
        this.simulation = (Simulation) object;
    }
}