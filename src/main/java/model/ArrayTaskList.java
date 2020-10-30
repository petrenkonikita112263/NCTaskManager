//package model;
//
//import org.apache.logging.log4j.Logger;
//import view.DeleteView;
//
//import java.util.Arrays;
//import java.util.ConcurrentModificationException;
//import java.util.Iterator;
//import java.util.NoSuchElementException;
//import java.util.stream.Stream;
//
//import static java.util.Arrays.*;
//import static java.util.stream.Stream.*;
//import static org.apache.logging.log4j.LogManager.getLogger;
//
///**
// * The ArrayTaskList class creates a array from the tasks, that
// * was created by the Task class. Also we create some methods:
// * addTask(Task task), size(), remove(Task task) and
// * incoming(from, to).
// *
// * @author Nikita
// */
//public class ArrayTaskList extends AbstractTaskList implements Cloneable {
//
//    /**
//     * Adding logger to the class.
//     */
//    private static final Logger logger = getLogger(ArrayTaskList.class);
//
//    /**
//     * Instance of DeleteView.
//     */
//    private DeleteView view;
//
//    /**
//     * initial capacity of the array.
//     */
//    private static final int DEFAULT_SIZE = 10;
//
//    /**
//     * taskStore - name of the array.
//     */
//    private Task[] taskStore;
//
//    /**
//     * size - how long the array is.
//     */
//    private int size = 0;
//
//    /**
//     * DVC constructor create the array.
//     */
//    public ArrayTaskList() {
//        view = new DeleteView();
//        this.taskStore = new Task[DEFAULT_SIZE];
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void add(Task task) {
//        if (size == taskStore.length) {
//            increaseCapacity();
//        }
//        taskStore[size] = task;
//        size++;
//    }
//
//    /**
//     * Method that multiples the length by 2.
//     */
//    public void increaseCapacity() {
//        taskStore = copyOf(taskStore, taskStore.length * 2);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Task getTask(int index) {
//        if (index < size) {
//            return taskStore[index];
//        } else if (index < 0) {
//            throw new IllegalArgumentException(index + " "
//                    + "is a negative value, it can't be");
//        } else {
//            throw new ArrayIndexOutOfBoundsException("The number of index "
//                    + index + " bigger than size " + size
//                    + " of the array");
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public int size() {
//        return this.size;
//    }
//
//    /**
//     * Public method removeElement().
//     *
//     * @param index the index of the element, that
//     *              will be removed from the array
//     * @return removedTask return removed task
//     */
//    public Task removeElement(int index) {
//        try {
//            if (index < 0 || index >= size) {
//                System.out.println("The index can't be bigger that size of the array or even negative");
//                view.removeSomeTask();
//            }
//        } catch (IndexOutOfBoundsException e) {
//            logger.error("The main error of array list - out of the size ", e);
//        }
//        Task removedTask = taskStore[index];
//        if (size - 1 - index >= 0) {
//            System.arraycopy(taskStore, index + 1, taskStore, index, size - 1 - index);
//        }
//        size--;
//        return removedTask;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean remove(Task task) {
//        for (int i = 0; i < size(); i++) {
//            if (task.equals(taskStore[i])) {
//                removeElement(i);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public String toString() {
//        return "ArrayTaskList{" + "taskStore = " + Arrays.toString(taskStore)
//                + '}';
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof ArrayTaskList)) {
//            return false;
//        }
//        ArrayTaskList that = (ArrayTaskList) o;
//        return size == that.size && Arrays.equals(taskStore, that.taskStore);
//    }
//
//    /**
//     * Override method that returns iterator for ArrayList.
//     *
//     * @return - iterator
//     */
//    public Iterator<Task> iterator() {
//        return new ArrayTaskListIterator();
//    }
//
//
//    /**
//     * Additional class for implementing Iterator with three main methods:
//     * hasNext(), next(), remove().
//     *
//     * @author Nikita
//     */
//    private class ArrayTaskListIterator implements Iterator<Task> {
//
//        /**
//         * Parameter of current position in the list.
//         */
//        private int currentPosition;
//
//        /**
//         * {@inheritDoc}
//         */
//        @Override
//        public boolean hasNext() {
//            return currentPosition < size();
//        }
//
//        /**
//         * {@inheritDoc}
//         */
//        @Override
//        public Task next() {
//            if (!hasNext()) {
//                throw new NoSuchElementException();
//            }
//            return taskStore[currentPosition++];
//        }
//
//        /**
//         * {@inheritDoc}
//         */
//        @Override
//        public void remove() {
//            if (currentPosition <= 0) {
//                throw new IllegalStateException();
//            }
//            try {
//                ArrayTaskList.this.remove(getTask(currentPosition - 1));
//                currentPosition--;
//            } catch (IndexOutOfBoundsException ex) {
//                throw new ConcurrentModificationException("You've got smth bad "
//                        + ex);
//            }
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public ArrayTaskList clone() throws CloneNotSupportedException {
//        ArrayTaskList cloneClass = (ArrayTaskList) super.clone();
//        cloneClass.taskStore = this.taskStore.clone();
//        return cloneClass;
//    }
//
//    /**
//     * Implementing toArray method for ArrayList.
//     *
//     * @return return the array of the tasks
//     */
//    public Task[] toArray() {
//        return copyOf(taskStore, size());
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Stream<Task> getStream() {
//        Task[] arrayOfTask = this.toArray();
//        return of(arrayOfTask);
//    }
//}
//
