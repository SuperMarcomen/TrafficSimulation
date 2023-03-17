package edu.kit.informatik.commands;

import edu.kit.informatik.TrafficSimulation;

import java.util.Collections;
import java.util.List;

public class QuitCommand extends Command {

    private final TrafficSimulation trafficSimulation;

    public QuitCommand(TrafficSimulation trafficSimulation) {
        this.trafficSimulation = trafficSimulation;
    }

    @Override
    protected List<String> execute() {
        trafficSimulation.stop();
        return Collections.emptyList();
    }
}
