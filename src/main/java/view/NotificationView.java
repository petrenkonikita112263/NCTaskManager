package view;

import java.time.LocalDateTime;

/**
 * Additonal view class for thread-notification
 * class display upcoming task.
 * @author Nikita
 */
public class NotificationView {

//    /**
//     *
//     */
//    private Task someTask;
//
//    /**
//     * EVC constructor.
//     *
//     * @param someTask -
//     */
//    public NotificationView(Task someTask) {
//        this.someTask = someTask;
//    }

    /**
     * Method that print message of upcoming task.
     */
    public void displayMessageNotification(LocalDateTime time, String title) {
        try {
            System.out.println("Hi, you've upcoming event in "
                    + time + " don't forget about it " + title);
        } catch (Exception mainExp) {
            mainExp.printStackTrace();
        }
    }
}
