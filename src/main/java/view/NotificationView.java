package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

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
        DateTimeFormatter formatter = ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Hi, you've upcoming event in "
                + formatter.format(time) + " don't forget about it  - |" + title + "|");
    }

    public void displayHint() {
        System.out.println("Notifier is waiting, list is empty. Please add the task to the list");
    }

    public void getWaitMessage(int timeWaiting) {
        System.out.println("Notifier waits: " + timeWaiting);
    }
}
