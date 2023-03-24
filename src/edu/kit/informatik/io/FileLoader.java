package edu.kit.informatik.io;

import edu.kit.informatik.entities.Map;
import edu.kit.informatik.exceptions.SimulatorException;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

/**
 * Loads the files needed for the simulation and handles the
 * possible errors.
 *
 * @author uswry
 * @version 1.0
 */
public class FileLoader {

    private final SimulationFileLoader simulationFileLoader;

    /**
     * Initializes the file loader.
     * @param path - The path of the files
     * @throws SimulatorException if the folder does not exist or the path is pointing to a normal file
     */
    public FileLoader(String path) {
        try {
            simulationFileLoader = new SimulationFileLoader(path);
        } catch (IOException | InvalidPathException exception) {
            throw new SimulatorException(exception.getMessage());
        }
    }

    /**
     * Load the three needed files.
     * @return an instance of the simulation map
     * @throws SimulatorException if the file does not exist or points to a directory
     */
    public Map loadFiles() {
        List<String> streetsStrings;
        List<String> crossingStrings;
        List<String> carsStrings;
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
