package model;

/**
 * Additional class for LinkedTaskList class.
 * Implementing of single-linked list. Fields are public,
 * because they're used in LinkedTaskList class.
 *
 * @param <Task> - generic type for LinkedList
 * @author Nikita
 */
public class Node<Task> {

    /**
     * Contents of the current list item.
     */
    public Task task;

    /**
     * Points to the next list item.
     */
    public Node<Task> next;

    /**
     * EVC.
     *
     * @param task - task
     */
    public Node(Task task) {
        this.task = task;
        next = null;
    }

    /**
     * method that display all items in the list.
     */
    public void displayNode() {
        System.out.println(task);
    }

    /**
     * getting a pointer to the next list item.
     *
     * @return next - get the pointer
     */
    public Node<Task> getNext() {
        return next;
    }

    /**
     * Setter for Node.
     *
     * @param next - set next node for task
     */
    public void setNext(Node<Task> next) {
        this.next = next;
    }

    /**
     * Getter for Node.
     *
     * @return task - get the task
     */
    public Task getTask() {
        return task;
    }
}
