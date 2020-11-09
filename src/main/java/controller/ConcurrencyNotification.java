package controller;

import model.Task;
import model.Tasks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.NotificationView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

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
     * The array list of tasks.
     */
    private final CopyOnWriteArrayList<Task> listOfTasks;

    /**
     * Instance of view of notifications.
     */
    private final NotificationView notificationView;

    /**
     * EVC constructor.
     *
     * @param listOfTasks array list
     * @param notificationView instance of view
     */
    public ConcurrencyNotification(CopyOnWriteArrayList<Task> listOfTasks,
                                   NotificationView notificationView
    ) {
        this.listOfTasks = listOfTasks;
        this.notificationView = notificationView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        synchronized (listOfTasks) {
            while (true) {
                if (!listOfTasks.isEmpty()) {
                    LocalDateTime startNotify = (LocalDateTime.now());
                    LocalDateTime endNotify = LocalDateTime.now()
                            .plusSeconds(DEFAULT_TIME);
                    ConcurrentNavigableMap<LocalDateTime, CopyOnWriteArraySet<Task>> notifyCalendar =
                            Tasks.calendar(listOfTasks, startNotify, endNotify);
                    for (Map.Entry<LocalDateTime, CopyOnWriteArraySet<Task>> content : notifyCalendar.entrySet()) {
                        for (Task someTask : content.getValue()) {
                            String taskTitle = someTask.getTitle();
                            if (content.getKey().isEqual(startNotify)) {
                                notificationView.displayMessageNotification(content.getKey(), taskTitle);
                            }
                        }
                    }
                } else {
                    notificationView.displayHint();
                }
                try {
                    int random = 120_000;
                    notificationView.getWaitMessage(random);
                    listOfTasks.wait(random);
                } catch (InterruptedException exception) {
                    logger.error("The thread was interrupted during the waiting process", exception);
                }
            }
        }
    }
}


