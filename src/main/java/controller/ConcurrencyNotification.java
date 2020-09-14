package controller;

import model.ArrayTaskList;
import model.Task;
import model.Tasks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.NotificationView;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private static final Logger logger = LogManager.getLogger(ConcurrencyNotification.class);

    /**
     * Time constant in seconds for time notification.
     */
    private static final int DEFAULT_TIME = 30;

    /**
     * Time constant in milliseconds how long the thread would sleep.
     */
    private static final int SLEEP_THREAD = 5_000;

    /**
     * This variable uses to set flag for killing thread.
     */
    private final AtomicBoolean running = new AtomicBoolean(false);

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
     * @param listOfTasks array list
     * @param notificationView instance of view
     */
    public ConcurrencyNotification(ArrayTaskList listOfTasks,
                                   NotificationView notificationView
    ) {
        this.listOfTasks = listOfTasks;
        this.notificationView = notificationView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interrupt() {
        super.interrupt();
        running.set(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            LocalDateTime startNotify = (LocalDateTime.now().withSecond(0).withNano(0));
            LocalDateTime endNotify = LocalDateTime.now().withSecond(0).withNano(0)
                    .plusSeconds(DEFAULT_TIME);
            SortedMap<LocalDateTime, Set<Task>>
                    calendarContent = Tasks.calendar(listOfTasks, startNotify, endNotify);
            for (SortedMap.Entry<LocalDateTime, Set<Task>> content : calendarContent.entrySet()) {
                for (Task someTask : content.getValue()) {
                    String taskTitle = someTask.getTitle();
                    if (content.getKey().isEqual(startNotify)) {
                        notificationView.displayMessageNotification(content.getKey(), taskTitle);
                    }
                }
            }
            try {
                sleep(SLEEP_THREAD);
            } catch (InterruptedException e) {
                logger.error("The thread can't run", e);
            }
        }
    }
}


