package controller;

import model.ArrayTaskList;
import model.Task;
import model.Tasks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.NotificationView;

import java.time.LocalDateTime;
import java.util.Iterator;

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
    private static final Logger log =
            LogManager.getLogger("info.log");

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
     * Instance of controller.
     */
    private MajorController mainController;

    /**
     * Boolean variable of notification.
     */
    private boolean notify;

    /**
     * EVC constructor.
     *
     * @param listOfTasks    - array list
     * @param mainController - instance of controller
     */
    public ConcurrencyNotification(ArrayTaskList listOfTasks,
                                   MajorController mainController) {
        this.listOfTasks = listOfTasks;
        this.mainController = mainController;
    }

    /**
     * Override run() method to thread.
     */
    @Override
    public void run() {
        notify = false;
        while (true) {
            LocalDateTime startNotify = (LocalDateTime.now()
                    .minusSeconds(DEFAULT_TIME));
            LocalDateTime endNotify = LocalDateTime.now()
                    .plusSeconds(DEFAULT_TIME);
            ArrayTaskList arrayTaskList = (ArrayTaskList) Tasks
                    .incoming(listOfTasks,
                            startNotify, endNotify);
            Iterator<Task> helper = arrayTaskList.iterator();
            while (helper.hasNext()) {
                Task task = helper.next();
                NotificationView notifyThread = new NotificationView(task);
                notifyThread.displayMessageNotification();
                notify = true;
            }
            try {
                sleep(SLEEP_THREAD);
                if (notify) {
                    log.info("Notification was started");
                }
            } catch (InterruptedException e) {
                log.error("The thread can't run " + e);
            }
        }
    }
}
