package model;

import java.util.stream.Stream;

/**
 * Abstract class that holds common methods for ArrayTaskList class
 * and LinkedTaskList class.
 * @author Nikita
 */
public abstract class AbstractTaskList implements Iterable<Task> {

    abstract void add(Task task);

    abstract boolean remove(Task task);

    abstract int size();

    abstract Task getTask(int index);

    /**
     * Common method getStream() that replace incoming method in ArrayTaskList
     * class and LinkedTaskList class.
     *
     * @return - get stream
     */
    public Stream<Task> getStream() {
        return null;
    }
}
