package model;

import org.apache.log4j.Logger;
import view.DeleteView;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * The ArrayTaskList class creates a array from the tasks, that
 * was created by the Task class. Also we create some methods:
 * addTask(Task task), size(), remove(Task task) and
 * incoming(from, to).
 *
 * @author Nikita
 */
public class ArrayTaskList extends AbstractTaskList implements Cloneable {

    /**
     * Adding logger to the class.
     */
    private static final Logger logger = Logger.getLogger(ArrayTaskList.class);

    /**
     * Instance of DeleteView.
     */
    private DeleteView view;

    /**
     * initial cpacity of the array.
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * taskStore - name of the array.
     */
    private Task[] taskStore;

    /**
     * size - how long the array is.
     */
    private int size = 0;

    /**
     * DVC constructor create the array.
     */
    public ArrayTaskList() {
        view = new DeleteView();
        this.taskStore = new Task[DEFAULT_SIZE];
    }

    /**
     * Add method, that add task to the ArrayList.
     *
     * @param task - the task that adds to the array
     */
    @Override
    public void add(Task task) {
        if (size == taskStore.length) {
            increaseCapacity();
        }
        taskStore[size] = task;
        size++;
    }

    /**
     * Method that multiples the length by 2.
     */
    public void increaseCapacity() {
        taskStore = Arrays.copyOf(taskStore, taskStore.length * 2);
    }

    /**
     * getTask() method.
     *
     * @param index - the index of the task in the array
     * @return taskStore[index] - return task from the array by index
     */

    public Task getTask(int index) {
        if (index < size) {
            return taskStore[index];
        } else if (index < 0) {
            throw new IllegalArgumentException(index + " "
                    + "is a negative value, it can't be");
        } else {
            throw new ArrayIndexOutOfBoundsException("The number of index "
                    + index + " bigger than size " + size
                    + " of the array");
        }
    }

    /**
     * Getter for size.
     *
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * Private method removeElement().
     *
     * @param index - the index of the element, that
     *              will be removed from the array
     * @return removedTask - return removed task
     */
    public Task removeElement(int index) {
        try {
            if (index < 0 || index >= size) {
                System.out.println("The index can't be bigger that size of the array or even negative");
                view.removeSomeTask();
            }
        } catch (IndexOutOfBoundsException e) {
            logger.error("The main error of array list - out of the size ", e);
        }
        Task removedTask = taskStore[index];
        for (int i = index; i < size - 1; i++) {
            taskStore[i] = taskStore[i + 1];
        }
        size--;
        return removedTask;
    }

    /**
     * Main remove() method.
     *
     * @param task - the task which we want to remove
     * @return boolean - if this task is existed = true
     * else = false
     */
    public boolean remove(Task task) {
        for (int i = 0; i < size(); i++) {
            if (task.equals(taskStore[i])) {
                removeElement(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Override toString() method from Object class.
     *
     * @return - String type of message
     */
    @Override
    public String toString() {
        return "ArrayTaskList{" + "taskStore = " + Arrays.toString(taskStore)
                + '}';
    }

    /**
     * Override hashCode() method from Object class.
     *
     * @return - int number based on size of the list
     */
    @Override
    public int hashCode() {
        return this.size;
    }

    /**
     * Override equals() method from Object class.
     *
     * @param o - object of the class
     * @return - get true if this object is the object of ArrayTaskList
     * elsewhere get false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrayTaskList)) {
            return false;
        }
        ArrayTaskList that = (ArrayTaskList) o;
        return size == that.size && Arrays.equals(taskStore, that.taskStore);
    }

    /**
     * Override method that returns iterator for ArrayList.
     *
     * @return - iterator
     */
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }


    /**
     * Additional class for implementing Iterator with three main methods:
     * hasNext(), next(), remove().
     *
     * @author Nikita
     */
    private class ArrayTaskListIterator implements Iterator<Task> {

        /**
         * Parameter of current position in the list.
         */
        private int currentPosition;

        /**
         * Override hasNext method from Iterator interface.
         *
         * @return - true if there's next elements,
         * elsewhere - get false
         */
        @Override
        public boolean hasNext() {
            return currentPosition < size();
        }

        @Override
        public Task next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return taskStore[currentPosition++];
        }

        /**
         * Override remove method from Iterator interface.
         */
        @Override
        public void remove() {
            if (currentPosition <= 0) {
                throw new IllegalStateException();
            }
            try {
                ArrayTaskList.this.remove(getTask(currentPosition - 1));
                currentPosition--;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException("You've got smth bad "
                        + ex);
            }
        }
    }

    /**
     * Override clone() method.
     * Deep cloning of the class ArrayTaskList.
     *
     * @return - copy of the task
     */
    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList cloneClass = (ArrayTaskList) super.clone();
        cloneClass.taskStore = this.taskStore.clone();
        return cloneClass;
    }

    /**
     * Implementing toArray method for ArrayList.
     *
     * @return - return the array of the tasks
     */
    public Task[] toArray() {
        return Arrays.copyOf(taskStore, size());
    }

    /**
     * Override method getStream() from Tasks class.
     * Run getStream() instead of incoming().
     *
     * @return - get stream
     */
    @Override
    public Stream<Task> getStream() {
        Task[] arrayOfTask = this.toArray();
        Stream<Task> stream = Stream.of(arrayOfTask);
        return stream;
    }
}

