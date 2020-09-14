package view;

import java.io.IOException;

public class DeleteView extends PrimaryView {

    /**
     * Method that ask user to set id of task that will be deleted.
     *
     * @return the id of task for which remove will be called
     */
    public int removeSomeTask() {
        int id = 0;
        System.out.println("Please write the id number of your task, "
                + "that you want to delete");
        try {
            id = Integer.parseInt(bufReader.readLine());
            if (id < 0) {
                System.out.println("This number can't be negative or bigger than Integer");
                removeSomeTask();
            }
        } catch (IOException exp_1) {
            LOGGER.error("Error in input number to the console", exp_1);
            removeSomeTask();
        } catch (NumberFormatException exp_2) {
            LOGGER.error("Wrong type of id for task", exp_2);
            System.out.println("Required integer number, but your type isn't based on it");
            removeSomeTask();
        }
        return id;
    }

}
