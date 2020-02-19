package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import model.Tasks;
import org.apache.log4j.Logger;
import view.PrimaryView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class that implements interface of controller.
 *
 * @author Nikita
 */
public class MajorController implements CoreController {

    /**
     * Adding logger to the class.
     */
    private static final Logger logger = Logger.getLogger(MajorController.class);

    /**
     * Constant that holds name of the file for saving task list.
     */
    private final String nameOfFile = "TaskManagerList";

    /**
     * Instance of list.
     */
    private ArrayTaskList listOfTasks;

    /**
     * Instance of PrimaryView.
     */
    private PrimaryView view;

    /**
     * The name of the file that stores the session of application.
     */
    private String folderName = "savepoint";

    /**
     * EVC constructor.
     */
    public MajorController() {
        listOfTasks = new ArrayTaskList();
        view = new PrimaryView();
        ConcurrencyNotification notificationThread =
                new ConcurrencyNotification(listOfTasks, this);
        notificationThread.start();
        logger.info("The thread is running. Notification works");
    }

    /**
     * Private method that display info message about
     * new created list.
     */
    private void createEmptyTaskList() {
        System.out.println("The empty task list was created");
    }

    /**
     * Implementing (override) runMainApplication() method
     * from interface.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    @Override
    public void runMainApplication() throws IOException {
        view.displayInfo();
        logger.info("The main menu was called");
        int defaultNumber_1 = view.getUserInput();
        logger.info("Console was called");
        switch (defaultNumber_1) {
            case 1:
                continueWork();
                runSecondaryMenu();
                break;
            case 2:
                readFileWithTasks();
                runSecondaryMenu();
                break;
            case 3:
                createEmptyTaskList();
                runSecondaryMenu();
                break;
            case 4:
                System.exit(0);
            default:
//                System.out.println("Wrong input by user!!!!" + new AssertionError());
                new AssertionError();
        }
    }

    /**
     * Privatre method that run more duties in application.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    private void runSecondaryMenu() throws IOException {
        logger.info("The additional menu was called");
        view.displayAdditionalInfo();
        int defaultNumber = view.getUserInput();
        logger.info("Console was called");
        switch (defaultNumber) {
            case 1:
                processAddingTask();
                break;
            case 2:
                processChangingTask();
                break;
            case 3:
                processDeletingTask();
                break;
            case 4:
                displayListOfTasks(listOfTasks);
//                showListOfTask();
                break;
            case 5:
                displayDetailAboutTask(listOfTasks);
//                showTaskDetails();
                break;
            case 6:
                createCalendar();
                break;
            case 7:
                processSavingWork();
                break;
            case 8:
                System.exit(0);
            default:
//                System.out.println("Wrong input by user!!!!" + new AssertionError());
                new AssertionError();
        }
    }

    /**
     * Implementing (override) addSomeTask() method
     * from interface.
     *
     * @throws IOException - input exception
     */
    @Override
    public void processAddingTask() throws IOException {
        String title = view.addTaskTitle();
        String wordAnswer = view.selectTheTypeForTask();
        if (wordAnswer.toLowerCase().equals("no")) {
            LocalDateTime time = view.inputDateTime();
            Task someTask = new Task(title, time);
            listOfTasks.add(someTask);
            logger.info("The non-repetead task was added");
            runSecondaryMenu();
        } else if (wordAnswer.toLowerCase().equals("yes")) {
            LocalDateTime start = view.inputDateTime();
            LocalDateTime end = view.inputDateTime();
            int interval = view.addInterval();
            Task someTask = new Task(title, start, end, interval);
            listOfTasks.add(someTask);
            logger.info("The repetead task was added");
            runSecondaryMenu();
        } else {
            logger.error("Wrong input, make "
                    + "sure that you enter the word yes or no!!");
            processAddingTask();
        }
    }


    /**
     * Implementing (override) removeSomeTask() method
     * from interface.
     */
    @Override
    public void processDeletingTask() throws IOException {
        displayListOfTasks(listOfTasks);
        int id = view.removeSomeTask();
        listOfTasks.removeElement(id);
    }

    /**
     * Implementing (override) changeTask() method
     * from interface.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    @Override
    public void processChangingTask() throws IOException {
        logger.info("The process of changing task was started");
        view.changeOptions();
        displayListOfTasks(listOfTasks);
        int optionValue = view.getUserInput();
        logger.info("The console was called");
        for (Task smth : listOfTasks) {
            switch (optionValue) {
//                case 1:
//                    int taskId = view.changeIdOfTask();
//                    smth.setId(taskId);
//                    logger.info("The id of the task was changed");
//                    runSecondaryMenu();
//                    break;
                case 1:
                    String taskName = view.changeTitleOfTask();
                    smth.setTitle(taskName);
                    logger.info("The title of the task was changed");
                    runSecondaryMenu();
                    break;
                case 2:
                    String answer = view.selectTheTypeForTask();
                    if (answer.toLowerCase().equals("yes")) {
                        LocalDateTime startTime = view.inputDateTime();
                        LocalDateTime endTime = view.inputDateTime();
                        int taskInterval = view.changeIntervalOfTask();
                        smth.setTime(startTime, endTime, taskInterval);
                        logger.info("The repeated time and interval were changed");
                        runSecondaryMenu();
                    } else if (answer.toLowerCase().equals("no")) {
                        LocalDateTime taskTime = view.inputDateTime();
                        smth.setTime(taskTime);
                        logger.info("The time of the task was changed");
                        runSecondaryMenu();
                    } else {
                        logger.error("Wrong input, make sure "
                                + "that you write yes or no!!!");
                        view.selectTheTypeForTask();
                    }
                case 3:
                    int taskStatus = view.changeStatusOfTask();
                    if (taskStatus == 1) {
                        smth.setActive(true);
                        logger.info("The task is now active");
                        runSecondaryMenu();
                    } else if (taskStatus == 0) {
                        smth.setActive(false);
                        logger.info("The task turn off");
                        runSecondaryMenu();
                    } else {
                        logger.error("Wrong input, make "
                                + "sure that you enter number 1 or 0!!");
                        processChangingTask();
                    }
                default:
//                    System.out.println("Wrong input by user!!!!" + new AssertionError());
                    new AssertionError();
            }
        }
    }

    /**
     * Private method that allow to write task list to GSON file.
     *
     * @param nameFile - file name that stores the task list
     */
    private void saveFileWithTasks(String nameFile) {
        try {
            TaskIO.writeText(listOfTasks,
                    (new File(folderName + File.separatorChar + nameFile + ".json")));
        } catch (IOException e) {
            logger.error("Error with writing process to the file", e);
        }
    }

    /**
     * Private method that allow to read task list from GSON file.
     *
     * @param nameFile - file name that stores the task list
     */
    public void readFileWithTasks(String nameFile) {
        try {
            TaskIO.read(listOfTasks,
                    (new FileReader(folderName + File.separatorChar + nameFile + ".json")));
        } catch (IOException e) {
            logger.error("Error with reading process from the file", e);
        }
    }

    /**
     * Implementing (override) createCalendar() method
     * from interface.
     */
    @Override
    public void createCalendar() throws IOException {
        LocalDateTime startDate = view.addTimeLimit_1();
        LocalDateTime limitDate = view.addTimeLimit_2();
        SortedMap<LocalDateTime, Set<Task>> defaultCalendar =
                Tasks.calendar(listOfTasks, startDate, limitDate);
//        System.out.println("The calendar was created");
        view.displayCreatedCalendar(defaultCalendar);
        runSecondaryMenu();
    }

//    /**
//     * Implementing (override) showTaskDetails() method
//     * from interface.
//     */
//    @Override
//    public void showTaskDetails() throws IOException {
//        view.displayDetailAboutTask(listOfTasks);
//        runSecondaryMenu();
//    }
//
//    /**
//     * Implementing (override) showListOfTask() method
//     * from interface.
//     */
//    @Override
//    public void showListOfTask() throws IOException {
//        view.displayListOfTasks(listOfTasks);
//        runSecondaryMenu();
//    }

    /**
     * Implementing (override) configApplication() method
     * from interface.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing text file
     */
    @Override
    public void processSavingWork() throws IOException {
        createFolder(folderName);
        int optionNumber = view.getAction();
        if (optionNumber == 1) {
            String fileName = view.getFileName();
            saveFileWithTasks(fileName);
        } else if (optionNumber == 2) {
            readFileWithTasks();
        } else if (optionNumber == 3) {
            runSecondaryMenu();
        } else {
//            System.out.println("Something wrong try again");
            processSavingWork();
        }
        logger.info("The session was saved");
    }

    /**
     * Private additional method that read tasks from GSON file,
     * that name user input.
     */
    private void readFileWithTasks() {
        String fileName = view.getFileName();
        readFileWithTasks(fileName);
    }

    /**
     * Private method that creates empty file and write the content to it.
     *
     * @throws IOException - failure during writing to the file
     */
    private void processCreatingFile() throws IOException {
        try {
            String defaultFile = view.getFileName();
            File fileTxt = new File(defaultFile);
            logger.info("The file starts creating.........");
//            logger.info("The file starts creating.........");
            if (fileTxt.createNewFile()) {
                logger.info("Your file is created here '"
                        + defaultFile + "'");
            } else {
                logger.info("File've already existed");
//                logger.info("File've already existed");
                PrintWriter writer = new PrintWriter(defaultFile);
                writer.print("");
                writer.close();
            }
//            TaskIO.writeText(listOfTasks, fileTxt);
            logger.info("The process of "
                    + "writing was successfully completed");
//            logger.info("The process of "
//                    + "writing was successfully completed");
        } finally {
            logger.info("The process "
                    + "of create default file was successfully completed");
        }
    }

    /**
     * Private static method that create empty folder by specified way.
     *
     * @param dirPath - path to the directory
     */
    private static void createFolder(String dirPath) {
        logger.info("\nThe process of creating folder was started");
        File fileDirectory = new File(dirPath);
        if (!fileDirectory.exists()) {
            logger.info("The directory starts creating........");
//            logger.info("The directory starts creating........");
            if (fileDirectory.mkdir()) {
                logger.info("The directory '"
                        + dirPath + "' is created");
//                logger.info("The directory '"
//                        + dirPath + "' is created");
            } else {
                logger.error("Don't enough "
                        + "permission to create directory");
//                logger.error("Don't enough "
//                        + "permission to create directory");
            }
        }
        logger.info("\nThe process of creating folder was finished");
    }

    /**
     * Implementing (override) continueWork() method
     * from interface.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing text file
     */
    @Override
    public void continueWork() throws IOException {
        processCreatingFile();
    }

    /**
     * Implementation (override) displayListOfTasks() method from
     * interface.
     */
    @Override
    public void displayListOfTasks(ArrayTaskList taskList) {
        for (Task someTask : taskList) {
            System.out.println(someTask);
        }
    }

    /**
     * Implementation (override) displayDetailAboutTask() method from
     * interface.
     */
    @Override
    public void displayDetailAboutTask(ArrayTaskList taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.getTask(i);
            if (t.isRepeated()) {
                System.out.println("\tTitle : " + t.getTitle()
                        + "\nTask starts at " + t.getStartTime()
                        + "\nTask ends at " + t.getEndTime()
                        + "\nthe interval between start and end time is "
                        + t.getRepeatInterval());
            } else {
                System.out.println("\tTitle : " + t.getTitle()
                        + "\nTask starts at " + t.getStartTime()
                        + "\nTask ends at " + t.getEndTime());
            }
        }
    }
}
