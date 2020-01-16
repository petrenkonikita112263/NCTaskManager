package view;

import model.ArrayTaskList;
import model.Task;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * View class that implements interface of Core class.
 * @author Nikita
 */
public class PrimaryView implements CoreViewable {

    /**
     * Implementation (override) displayInfo() method from
     * interface.
     */
    @Override
    public void displayInfo() {
        System.out.println("The application Task Manager is running. "
                + "You have these options: \n");
        System.out.println("\t 1 - To continue work with your app"
                + " from previous savepoint");
        System.out.println("\t 2 - To load list of task");
        System.out.println("\t 3 - To create empty task list");
        System.out.println("\t 4 - To terminate your work with Task Manager");
    }

    /**
     * Implementation (override) displayAdditionalInfo() method from
     * interface.
     */
    @Override
    public void displayAdditionalInfo() {
        System.out.println("\t1 - To add task to the list");
        System.out.println("\t2 - To change the task in the list");
        System.out.println("\t3 - To delete task from the list");
        System.out.println("\t4 - Display all tasks from the list");
        System.out.println("\t5 - Display all information about every tasks in the list");
        System.out.println("\t6 - Create calendar");
        System.out.println("\t7 - To write your task list to the file");
        System.out.println("\t8 - Terminate the application");
    }

    /**
     * Implementation (override) displayCreatedCalendar() method from
     * interface.
     */
    @Override
    public void displayCreatedCalendar(SortedMap<LocalDateTime,
            Set<Task>> values) {
        for (Map.Entry<LocalDateTime, Set<Task>> content : values.entrySet()) {
            for (Task task : content.getValue()) {
                System.out.println(content.getKey());
                System.out.println(task.getTitle());
            }
        }
    }

    /**
     * Implementation (override) displayListOfTasks() method from
     * interface.
     */
    @Override
    public void displayListOfTasks(ArrayTaskList taskList) {
        for (Task someTask : taskList) {
            System.out.println(someTask);
        }
    }

    /**
     * Implementation (override) changeOptions() method from
     * interface.
     */
    @Override
    public void changeOptions() {
        System.out.println("\t1 - Change id (number) of your task");
        System.out.println("\t2 - Change title of your task");
        System.out.println("\t3 - Change time and interval of your task");
        System.out.println("\t4- Set your task active or diactive it");
    }
}
