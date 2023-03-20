package edu.kit.informatik.news;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to notify its subscribers that the
 * observed object has changed.
 *
 * @author uswry
 * @version 1.0
 */
public class Notifier {

    private final List<Observer> observers;

    /**
     * Initializes the needed list.
     */
    public Notifier() {
        observers = new ArrayList<>();
    }

    /**
     * Adds an observer to the list.
     * @param observer - The observer to be added
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifies all the subscribers that the observed object has changed
     * @param object - The object that changed
     */
    public void updateObservers(Object object) {
        for (Observer observer : observers) {
            observer.update(object);
        }
    }
}
