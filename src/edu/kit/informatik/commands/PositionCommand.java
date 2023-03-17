package edu.kit.informatik.commands;

import edu.kit.informatik.entities.Car;
import edu.kit.informatik.entities.Simulation;
import edu.kit.informatik.io.ErrorLogger;
import edu.kit.informatik.news.Observer;

import java.util.List;

public class PositionCommand extends InputCommand implements Observer {

    private static final String ARGUMENT_REGEX = "\\d+";
    private static final String CORRECT_FORMAT = "This command needs an id to function!";
    private static final String NO_SIMULATION = "To run this command you first need to load a simulation!";
    private static final String POSITION = "Car %d on street %d with speed %d and position %d";
    private Simulation simulation;

    /**
     * Initializing the regex pattern and the correct format of the input.
     *
     */
    public PositionCommand() {
        super(ARGUMENT_REGEX, CORRECT_FORMAT);
    }

    @Override
    protected List<String> execute() {
        if (simulation == null) return List.of(ErrorLogger.format(NO_SIMULATION));
        int id = parseInput(input, 0);
        Car car = simulation.getMap().getCarFromId(id);
        String string = String.format(POSITION, car.getId(), car.getStreetId(), car.getSpeed(), car.getPosition());
        return List.of(string);
    }

    @Override
    public void update(Object object) {
        this.simulation = (Simulation) object;
    }
}
