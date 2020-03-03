package controller;

import model.ArrayTaskList;
import model.Task;
import model.Tasks;
import org.apache.log4j.Logger;
import view.NotificationView;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Additional functionality of application. This class
 * extends from Thread class and perform notification function.
 *
 * @author Nikita
 */
public class ConcurrencyNotification extends Thread {

    /**
     * Adding logger to the class.
     */
    private static final Logger logger = Logger.getLogger(ConcurrencyNotification.class);

    /**
     * Time constant in seconds for time notification.
     */
    private static final int DEFAULT_TIME = 100_000;

    /**
     * Time constant in milliseconds how long the thread would sleep.
     */
    private static final int SLEEP_THREAD = 10_000;

    /**
     * The array list of tasks.
     */
    private ArrayTaskList listOfTasks;

    /**
     * Instance of view of notifications.
     */
    private NotificationView notificationView;

    /**
     * EVC constructor.
     *
     * @param listOfTasks      - array list
     * @param notificationView - instance of view
     */
    public ConcurrencyNotification(ArrayTaskList listOfTasks,
                                   NotificationView notificationView
    ) {
        this.listOfTasks = listOfTasks;
        this.notificationView = notificationView;
    }

    /**
     * Override run() method to thread.
     */
    @Override
    public void run() {
        boolean exit = false;
        while ((!Thread.currentThread().isInterrupted()) && (!exit)) {
            try {
                LocalDateTime startNotify = (LocalDateTime.now());
                LocalDateTime endNotify = LocalDateTime.now()
                        .plusSeconds(DEFAULT_TIME);
                SortedMap<LocalDateTime, Set<Task>>
                        calendarContent = Tasks.calendar(listOfTasks, startNotify, endNotify);
                if (listOfTasks.size() == 0) {
                    notificationView.displayMessage(startNotify);
                    exit = true;
                } else
                    for (SortedMap.Entry<LocalDateTime, Set<Task>> content : calendarContent.entrySet()) {
                        for (Task someTask : content.getValue()) {
                            String taskTitle = someTask.getTitle();
                            if (content.getKey().isEqual(startNotify)) {
                                notificationView.displayMessageNotification(content.getKey(), taskTitle);
                            }
                        }
                    }

                sleep(SLEEP_THREAD);
            } catch (InterruptedException e) {
                logger.error("The thread can't run", e);
            }
        }
    }
}


