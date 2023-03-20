package edu.kit.informatik.news;

/**
 * Represents an observer of a changeable object.
 *
 * @author uswry
 * @version 1.0
 */
public interface Observer {

    /**
     * Receives the updated that the observed object has changed.
     * @param object - The object that changed
     */
    void update(Object object);
}
