package view;

import model.Task;

/**
 * Additonal view class for thread-notification
 * class display upcoming task.
 * @author Nikita
 */
public class NotificationView {

    /**
     *
     */
    private Task someTask;

    /**
     * EVC constructor.
     *
     * @param someTask -
     */
    public NotificationView(Task someTask) {
        this.someTask = someTask;
    }

    /**
     * Method that print message of upcoming task.
     */
    public void displayMessageNotification() {
        try {
            System.out.println("Hi, you've upcoming event,"
                    + " don't forget about it " + someTask.getTitle());
        } catch (Exception mainExp) {
            mainExp.printStackTrace();
        }
    }
}
