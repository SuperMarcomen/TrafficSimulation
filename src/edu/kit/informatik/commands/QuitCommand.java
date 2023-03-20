package edu.kit.informatik.commands;

import edu.kit.informatik.TrafficSimulation;

import java.util.Collections;
import java.util.List;

/**
 * A class to handle the quit command.
 *
 * @author uswry
 * @version 1.0
 */
public class QuitCommand extends Command {

    private final TrafficSimulation trafficSimulation;

    /**
     * Initializes the needed instance of TrafficSimulation.
     * @param trafficSimulation - An instance of TrafficSimulation
     */
    public QuitCommand(TrafficSimulation trafficSimulation) {
        this.trafficSimulation = trafficSimulation;
    }

    @Override
    protected List<String> execute() {
        trafficSimulation.stop();
        return Collections.emptyList();
    }
}
