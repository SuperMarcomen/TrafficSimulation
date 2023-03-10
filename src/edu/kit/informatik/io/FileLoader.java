package edu.kit.informatik.io;

import edu.kit.informatik.entities.Map;
import edu.kit.informatik.exceptions.SimulatorException;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;

import java.io.IOException;
import java.util.List;

public class FileLoader {

    private final SimulationFileLoader simulationFileLoader;

    public FileLoader(String path) {
        try {
            simulationFileLoader = new SimulationFileLoader(path);
        } catch (IOException exception) {
            throw new SimulatorException(exception.getMessage());
        }
    }

    public Map loadFiles() {
        List<String> streetsStrings, crossingStrings, carsStrings;
        try {
            streetsStrings = simulationFileLoader.loadStreets();
            crossingStrings = simulationFileLoader.loadCrossings();
            carsStrings = simulationFileLoader.loadCars();
        } catch (IOException exception) {
            throw new SimulatorException(exception.getMessage());
        }
        return new Map(streetsStrings, crossingStrings, carsStrings);
    }
}
