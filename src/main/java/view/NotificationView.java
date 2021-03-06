package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Additonal view class for thread-notification
 * class display upcoming task.
 *
 * @author Nikita
 */
public class NotificationView {

    /**
     * Method that prints message of upcoming task.
     */
    public void displayMessageNotification(LocalDateTime time, String title) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Hi, you've upcoming event in "
                + formatter.format(time) + " don't forget about it  - |" + title + "|");
    }
}
