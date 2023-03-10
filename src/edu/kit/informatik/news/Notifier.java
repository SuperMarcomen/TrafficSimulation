package edu.kit.informatik.news;

import java.util.ArrayList;
import java.util.List;

public class Notifier {

    private final List<Observer> observers;

    public Notifier() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void updateObservers(Object object) {
        for (Observer observer : observers) {
            observer.update(object);
        }
    }
}
