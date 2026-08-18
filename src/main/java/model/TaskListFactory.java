package model;

/**
 * Implementing of AbstractFactory. It has createTaskList() method,
 * that creates Array list or Linked list.
 *
 * @author Nikita
 */
public class TaskListFactory {

    /**
     * Static method that creates type of the list.
     *
     * @param type - use Array or Linked type
     * @return - get array or linked list or null if type not equals
     */
    public static AbstractTaskList createTaskList(ListTypes.Type type) {
        if (type.equals(ListTypes.Type.ARRAY)) {
            return new ArrayTaskList();
        }
        if (type.equals(ListTypes.Type.LINKED)) {
            return new LinkedTaskList();
        }
        return null;
    }
}
