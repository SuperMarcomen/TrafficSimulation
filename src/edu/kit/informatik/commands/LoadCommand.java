package edu.kit.informatik.commands;

import edu.kit.informatik.entities.Map;
import edu.kit.informatik.entities.Simulation;
import edu.kit.informatik.io.FileLoader;
import edu.kit.informatik.news.Notifier;

import java.util.List;

/**
 * A class to handle the load command.
 *
 * @author uswry
 * @version 1.0
 */
public class LoadCommand extends InputCommand {

    private static final String ARGUMENT_REGEX = "\\S+";
    private static final String CORRECT_FORMAT = "A path is required";
    private static final String READY = "READY";
    private final Notifier notifier;

    /**
     * Initializing the regex pattern and the correct format of the input.
     * @param notifier - An instance of the notifier to notify all the observers
     */
    public LoadCommand(Notifier notifier) {
        super(ARGUMENT_REGEX, CORRECT_FORMAT);
        this.notifier = notifier;
    }

    @Override
    protected List<String> execute() {
        FileLoader fileLoader = new FileLoader(input);
        Map map = fileLoader.loadFiles();
        map.checkAndInitMap();
        Simulation simulation = new Simulation(map);
        notifier.updateObservers(simulation);
        return List.of(READY);
    }
}
