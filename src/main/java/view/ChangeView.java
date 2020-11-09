package view;

import model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Integer.parseInt;

public class ChangeView extends PrimaryView {

    /**
     * Method that ask user to change type of the task.
     *
     * @return new type repeated or not
     */
    public int selectTheTypeForTask(BufferedReader bufferedReader) {
        System.out.println("Do you want to change scheduled task (start, end, interval) = 1"
                + " or you want to change ordinary task = 2");
        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Required integer number, but your type isn't based on it");
            LOGGER.error("The number is out of the limit", e);
        } catch (IOException e) {
            LOGGER.error("Can't get access to console by BufferedReader", e);
        }
        return 0;
    }

    /**
     * Method that allow user to input id for task.
     *
     * @return id of it
     */
    public int getTaskIndex(BufferedReader bufferedReader, CopyOnWriteArrayList<Task> arrayList) {
        int taskIndex = 0;
        System.out.println("Enter the id of the task " +
                "\nP.S. Note and bring to the attention that ArrayList element starts from 0: \r");
        try {
            taskIndex = Integer.parseInt(bufferedReader.readLine());
            if (taskIndex < 0 || taskIndex > arrayList.size()) {
                System.out.println("Can't be negative number or greater that size of the list");
                getTaskIndex(bufferedReader, arrayList);
            }
        } catch (NumberFormatException e) {
            System.out.println("Required integer number, but your type isn't based on it");
            LOGGER.error("The number is out of the limit", e);
        } catch (IOException e) {
            LOGGER.error("Can't get access to console by BufferedReader", e);
        }
        return taskIndex;
    }

    /**
     * Print menu options for change task in the list.
     */
    public void changeScheduledTask() {
        System.out.println("For your task you can change these parameters:"
                + "\n\t 1 - Change title of the task" + "\n\t 2 - Change time"
                + "\n\t 3 - Change type" + "\n\t 4 - Change status"
                + "\n\t 5 - Change interval"
                + "\nBack to menu for changing task type any positive number");
    }

    public void changeOrdinaryTask() {
        System.out.println("For your task you can change these parameters:"
                + "\n\t 1 - Change title of the task" + "\n\t 2 - Change time"
                + "\n\t 3 - Change type" + "\n\t 4 - Change status"
                + "\nBack to menu for changing task type any positive number");
    }

    /**
     * Method that ask user to change type of the task.
     *
     * @return 0 - disable it, 1 - enable it
     */
    public int changeStatusOfTask(BufferedReader bufferedReader) {
        int taskStatus = 0;
        System.out.println("Change status of task"
                + "\n1 - disable task; \n2 - enable task: \r");
        try {
            taskStatus = parseInt(bufferedReader.readLine());
            if (taskStatus < 0 || taskStatus > 2) {
                System.out.println("This number can't be negative or bigger than  option number or even Integer");
                changeStatusOfTask(bufferedReader);
            }
        } catch (NumberFormatException e) {
            System.out.println("Required integer number, but your type isn't based on it");
            LOGGER.error("The number is out of the limit", e);
        } catch (IOException e) {
            LOGGER.error("Can't get access to console by BufferedReader", e);
        }
        return taskStatus;
    }


}
