package view;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class SecondaryView extends PrimaryView {

    /**
     * Method displays additional functionality of
     * application.
     */
    public void displayInfo() {
        System.out.println("You have these options, so type number in range(1,7):");
        System.out.println("\t1 - To add task to the list");
        System.out.println("\t2 - To change the task in the list");
        System.out.println("\t3 - To delete task from the list");
        System.out.println("\t4 - Display all tasks with information inside ArrayList");
        System.out.println("\t5 - Create calendar");
        System.out.println("\t6 - To write your task list to the file");
        System.out.println("\t7 - Terminate the application");
    }

    /**
     * Ask user to type one of these numbers (1-7)
     *
     * @return get input integer number from console
     */
    public int getIntegerValue() {
        try {
            return parseInt(bufReader.readLine());
        } catch (IOException e) {
            LOGGER.error("Can't get access to console by BufferedReader", e);
        } catch (NumberFormatException e) {
            System.out.println("Required integer number, but your type isn't based on it");
            LOGGER.error("Wrong input type", e);
            getUserInput();
        }
        return 0;
    }
}
