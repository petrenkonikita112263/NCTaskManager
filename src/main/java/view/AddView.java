package view;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class AddView extends PrimaryView {

    /**
     * Method that allow user to input title for task.
     *
     * @return title of it
     */
    public String addTaskTitle(BufferedReader bufferedReader) {
        String title = null;
        System.out.println("Enter the title for task: \r");
        try {
            title = bufferedReader.readLine();
            checkAddedTaskTitle(title, bufferedReader);
        } catch (IllegalArgumentException exp) {
            System.out.println("Required string, but your type isn't based on it");
            LOGGER.error("Task can't exist with this title", exp);
            addTaskTitle(bufferedReader);
        } catch (IOException exp) {
            LOGGER.error("Error in input word to the console", exp);
            addTaskTitle(bufferedReader);
        }
        return title;
    }

    /**
     * Method that ask user to change type of the task.
     *
     * @return new type repeated or not
     */
    public int selectTheTypeForTask(BufferedReader bufferedReader) {
        System.out.println("If you'd like to add scheduled task type 1"
                + " but if you want to add ordinary task type 2");
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
     * Private additional method that validate task title
     * from user input.
     *
     * @param titleOfTask task title that user try to add to Task
     */
    private void checkAddedTaskTitle(String titleOfTask, BufferedReader bufferedReader) {
        if ((titleOfTask == null) || (titleOfTask.trim().isEmpty())) {
            System.out.println("Task can't exist with empty name or whitespaces\n"
                    + "You don't write any word. Please try again");
            addTaskTitle(bufferedReader);
        }
    }

    /**
     * Method that allow user to input interval for task.
     *
     * @return input interval
     */
    public int addInterval(BufferedReader bufferedReader) {
        int taskInterval = 0;
        System.out.println("At last enter the interval in minutes "
                + "for this task (integer value): \r");
        try {
            taskInterval = parseInt(bufferedReader.readLine());
            checkIntValue(taskInterval, bufferedReader);
        } catch (NumberFormatException exp) {
            LOGGER.error("Wrong type of interval for task", exp);
            System.out.println("Required integer number, but your type isn't based on it");
        } catch (IOException exp) {
            LOGGER.error("Error in input number to the console", exp);
        }
        return taskInterval;
    }

    /**
     * Private additional method that validate int number
     * from user input.
     *
     * @param number any integer number in range of Integer
     */
    private void checkIntValue(int number, BufferedReader bufferedReader) {
        if (number > 60 || number < 0) {
            System.out.println("Time in minutes can't be negative or move than 60");
            addInterval(bufferedReader);
        }
    }

}
