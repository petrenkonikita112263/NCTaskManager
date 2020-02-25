package model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * The LinkedTaskList class implements the same as
 * ArrayTaskList class: addTask(Task task), size(),
 * remove(Task task) and incoming(from, to).
 *
 * @author Nikita
 */
public class LinkedTaskList extends AbstractTaskList
        implements Iterable<Task>, Cloneable {

    /**
     * from Node class: head points to the first item.
     */
    private Node<Task> head;

    /**
     * size - tells how long the list is.
     */
    private int size;

    /**
     * DVC constructor - empty list.
     */
    public LinkedTaskList() {
        head = null;
    }

    /**
     * method that inserts a new list item into the
     * first position.
     *
     * @param task - the task that adds to the list
     */
    public void add(Task task) {

        // create new element
        Node tempNode = new Node(task);

        // pointed to the old "first" element
        tempNode.next = head;

        // and made it the second
        // marked the created item as the first
        head = tempNode;
        // increase size
        size++;
    }

    /**
     * Getter for size.
     *
     * @return size
     */
    public int size() {
        return size;
    }


    /**
     * getTask() method.
     *
     * @param index - the index of the task in the list
     * @return tempNode.task - return the this task from the list
     */
    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<Task> tempNode = head;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode.task;
    }

    /**
     * print all items from the list.
     */
    public void displayList() {

        // start from the first element
        Node<Task> tempList = head;

        // while it's not empty
        while (tempList != null) {
            tempList.displayNode();

            // move to the next element
            tempList = tempList.getNext();
        }
        System.out.println();
    }

    /**
     * method that removes a specific item in the list.
     *
     * @param task - the task which we want to remove
     * @return boolean - if this task is existed = true
     * else = false
     */
    public boolean remove(Task task) {
        if (head == null) {
            return false;
        } else if (head.task.equals(task)) {

            // if deleted was 1st item,
            // then next one becomes the first
            head = head.next;
            size--;
            return true;
        } else {
            Node<Task> currentNode = head;

            // task search
            while (currentNode.next != null) {

                // if deleted 2nd item
                if (currentNode.next.task.equals(task)) {

                    // then next one becomes the second
                    // if the item is inside the list, we will bypass it
                    currentNode.next = currentNode.next.next;
                    size--;
                    return true;
                }
                currentNode = currentNode.next;
            }
            return false;
        }
    }

    /**
     * Override toString() method from Object class.
     *
     * @return - String type of message
     */
    @Override
    public String toString() {
        String stringForList = new String();
        Iterator iteratorForString = this.iterator();
        while (iteratorForString.hasNext()) {
            stringForList += iteratorForString.next().toString();
        }
        return "LinkedTaskList: " + stringForList;
    }

    /**
     * Override hashCode() method from Object class.
     *
     * @return - int number based on 31 * 1 + size(of the list)
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + size;
        return result;
    }

    /**
     * Override equals() method from Object class.
     *
     * @param templLink - object of the class
     * @return - get true if this tempLink is the object of LinkedTaskList
     * elsewhere get false
     */
    @Override
    public boolean equals(Object templLink) {
        if (getClass() != templLink.getClass()) {
            return false;
        }
        if (templLink == this) {
            return true;
        }
        LinkedTaskList link = (LinkedTaskList) templLink;
        return size == link.size;
    }

    /**
     * Override method that returns iterator for LinkedList.
     *
     * @return - iterator
     */
    @Override
    public Iterator<Task> iterator() {
        return new LinkedTaskListIterator();
    }

    /**
     * Addtiotnal private class for implementing iterator for
     * single-node LinkedList.
     *
     * @author Nikita
     */
    private class LinkedTaskListIterator implements Iterator<Task> {

        /**
         * Current - keep track where iterator is going.
         */
        private Node<Task> current;

        /**
         * Previous_1 - the previous node.
         */
        private Node<Task> previous_1;

        /**
         * Previous_2 - previous node before previous node.
         */
        private Node<Task> previous_2;

        /**
         * CallRemove - check if remove was called (default false).
         */
        private boolean callRemove;

        /**
         * DVC constructor with default position = start of the list,
         * previous and previous-previous nodes are null, and method
         * remove was not called yet so it false.
         */
        public LinkedTaskListIterator() {

            // starts from the beginning of the list
            current = head;
            previous_1 = null;
            previous_2 = null;
            callRemove = false;
        }

        @Override
        public boolean hasNext() {

            //while current isn't null call method
            return current != null;
        }

        @Override
        public Task next() {
            if (current == null) {
                throw new NoSuchElementException("DON'T DO THESE PLEASE");
            }

            // need to store temp node
            Task temp = current.getTask();
            previous_2 = previous_1;
            previous_1 = current;

// that's for remove method: which node we're gonna to delete,
// so we need to know what node is it and the node before
// it was previous, and finally which previous node should point to current

            // point to the next node
            current = current.getNext();

            // set to false can't run remove() one more time
            callRemove = false;

            // get the data from temp node
            return temp;
        }

        @Override
        public void remove() {
            if ((previous_1 == null) || (callRemove)) {
                // never delete or hasNext wasn't called
                throw new IllegalStateException("DON'T DO THESE PLEASE");

                // hasNext was called once and trying to remove
            } else if (previous_2 == null) {

                // first node in the list
                head = current;

                // previous_2 was skipped
            } else {

// previous_2 isn't null, next node after previous_2
// -> current (skipping the node we're trying to delete)
                previous_2.setNext(current);
            }
            size--;
            callRemove = true;
        }
    }

    /**
     * Override clone() method.
     * Shallow cloning of the class LinkedTaskList.
     *
     * @return - copy of the class
     */
    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        return (LinkedTaskList) super.clone();
    }

    /**
     * Implementing toArray method for LinkedList.
     *
     * @return - return the array of the tasks
     */
    public Task[] toArray() {
        Task[] arrayOfTask = new Task[size()];
        int i = 0;
        for (Task smth : this) {
            arrayOfTask[i] = smth;
            i++;
        }
        return arrayOfTask;
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

        // convert Array to stream -> Sequential Stream
        Stream<Task> stream = Arrays.stream(arrayOfTask);
        return stream;
    }
}
