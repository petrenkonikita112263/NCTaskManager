package view;

import java.io.BufferedReader;
import java.io.IOException;

public class DeleteView extends PrimaryView {

    /**
     * Method that ask user to set id of task that will be deleted.
     *
     * @return the id of task for which remove will be called
     */
    public String removeSomeTask(BufferedReader bufferedReader) {
        String title = null;
        System.out.println("Please write the name of your task, "
                + "that you want to delete");
        try {
            title = bufferedReader.readLine();
            checkString(title, bufferedReader);
        } catch (IOException exp) {
            LOGGER.error("Error in typing word to the console", exp);
            removeSomeTask(bufferedReader);
        } catch (IllegalArgumentException otherExp) {
            System.out.println("Required string, but your type isn't based on it");
            LOGGER.error("Not String type", otherExp);
            removeSomeTask(bufferedReader);
        }
        return title;
    }

    /**
     * Private additional method that validate name of the file
     * from user input.
     *
     * @param string name of the file that user type
     */
    private void checkString(String string, BufferedReader bufferedReader) {
        if ((string == null) || (string.trim().isEmpty())) {
            System.out.println("Task can't exist with empty name or whitespaces\n"
                    + "You don't write any word. Please try again");
            removeSomeTask(bufferedReader);
        }
    }

}
