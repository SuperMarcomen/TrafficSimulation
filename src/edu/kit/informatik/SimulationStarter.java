package edu.kit.informatik;

import java.util.List;
import java.util.Scanner;

/**
 * A class to start a traffic simulation and
 * handle the inputs and outputs.
 *
 * @author uswry
 * @version 1.0
 */
public final class SimulationStarter {

    // A utility class can not be instantiated
    private SimulationStarter() {

    }

    /**
     * Contains the main loop and asks for input, handles it
     * and gives an output until the simulation is ended.
     *
     * @param args - No arguments are needed
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrafficSimulation trafficSimulation = new TrafficSimulation();
        do {
            String input = scanner.nextLine();
            List<String> output = trafficSimulation.handleInput(input);
            if (output.isEmpty()) continue;
            output.forEach(System.out::println);
        } while (trafficSimulation.isRunning());
        scanner.close();
    }
}
