package view;

import org.apache.log4j.Logger;

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
     * Adding logger to the class.
     */
    private static final Logger logger = Logger.getLogger(NotificationView.class);

    /**
     * Method that prints message of upcoming task.
     */
    public void displayMessageNotification(LocalDateTime time, String title) {
        try {
            System.out.println("Hi, you've upcoming event in "
                    + time + " don't forget about it " + title);
        } catch (Exception mainExp) {
            logger.error("Can't create message", mainExp);
        }
    }

    /**
     * Method that prints message after the thread starts
     * and if the list is empty, it displays only today date.
     */
    public void displayMessage(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("The thread runs.\nToday " + formatter.format(localDateTime)
                + " there're no tasks in the list.");
    }
}
