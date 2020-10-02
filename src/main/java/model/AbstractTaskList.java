package model;

import java.util.stream.Stream;

/**
 * Abstract class that holds common methods for ArrayTaskList class.
 *
 * @author Nikita
 */
public abstract class AbstractTaskList implements Iterable<Task> {

    /**
     * Add method, that add task to the ArrayList.
     *
     * @param task the task that adds to the array
     */
    abstract void add(Task task);

    /**
     * Main remove() method.
     *
     * @param task the task which we want to remove
     * @return boolean if this task is existed = true, else = false
     */
    abstract boolean remove(Task task);

    /**
     * Getter for size.
     *
     * @return size
     */
    abstract int size();

    /**
     * getTask() method.
     *
     * @param index the index of the task in the array
     * @return taskStore[index] return task from the array by index
     */
    abstract Task getTask(int index);

    /**
     * Common method getStream() that replace incoming method in ArrayTaskList class.
     *
     * @return get stream
     */
    public Stream<Task> getStream() {
        return null;
    }
}
