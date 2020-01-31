package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import model.Tasks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.PrimaryView;

import java.io.File;
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
    private static final Logger logger = LogManager.getLogger(MajorController.class);

//    /**
//     * Instead of using Scanner, use BufferedReader for input.
//     */
//    private static BufferedReader bufReader;

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

//    /**
//     * Empty file.
//     */
//    private File dataHoldingFile = new File(nameOfFile);

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

//    /**
//     * Private method that allow user to input info in console.
//     *
//     * @return - get the input number from console
//     * @throws IOException - input|output exception, failure during reading,
//     *                     writing information
//     */
//    private int getUserInput() throws IOException {
//        logger.info("User start works with console");
//        bufReader = new BufferedReader(new InputStreamReader(System.in));
//        return Integer.parseInt(bufReader.readLine());
//    }

    private void createEmptyTaskList() {
//        listOfTasks = new ArrayTaskList();
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
        logger.info("The main menu was called");
//        view.displayInfo();
        int defaultNumber_1 = view.getUserInput();
        logger.info("Console was called");
        switch (defaultNumber_1) {
            case 1:
                continueWork();
//                view.displayAdditionalInfo();
                runSecondaryMenu();
                break;
            case 2:
                readFileWithTasks();
//                view.displayAdditionalInfo();
                runSecondaryMenu();
                break;
            case 3:
                createEmptyTaskList();
//                view.displayAdditionalInfo();
                runSecondaryMenu();
                break;
            case 4:
                System.exit(0);
//                return;
            default:
                System.out.println("Wrong input by user!!!!" + new AssertionError());
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
                showListOfTask();
                break;
            case 5:
                showTaskDetails();
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
                System.out.println("Wrong input by user!!!!" + new AssertionError());
        }
    }

    /**
     * Implementing (override) addSomeTask() method
     * from interface.
     */
    @Override
    public void processAddingTask() throws IOException {
        int id = view.addTaskIndex();
        String title = view.addTaskTitle();
        String wordAnswer = view.addTypeOfTask();
        if (wordAnswer.toLowerCase().equals("no")) {
            LocalDateTime time = view.addTimeForTask();
            Task someTask = new Task(id, title, time);
            listOfTasks.add(someTask);
            logger.info("The non-repetead task was added");
            runSecondaryMenu();
        } else if (wordAnswer.toLowerCase().equals("yes")) {
            LocalDateTime start = view.addStartTimeForTask();
            LocalDateTime end = view.addEndTimeForTask();
            int interval = view.addInterval();
            Task someTask = new Task(id, title, start, end, interval);
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
    public void processDeletingTask() {
        view.displayListOfTasks(listOfTasks);
        int id = view.removeSomeTask();
//        int id = 0;
//        System.out.println("Please write the id number of your task, "
//                + "that you want to delete");
//        try {
//            id = Integer.parseInt(bufReader.readLine());
//        } catch (IOException exp) {
//            logger.error("Error in input number to the console", exp);
//        }
        boolean result = removeTask(id);
        if (result) {
            logger.info("The task was removed");
        } else {
            logger.error("You try to delete task that doesn't exist");
        }
    }

    /**
     * Private method for delete task by id.
     *
     * @param id - serial number of the task
     * @return - true if task was deleted, elsewhere false
     */
    private boolean removeTask(int id) {
        int index = -1;
        for (int i = 0, n = listOfTasks.size(); i < n; i++) {
            if (listOfTasks.getTask(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            listOfTasks.removeElement(index);
            return true;
        } else {
            return false;
        }
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
        int optionValue = view.getUserInput();
        logger.info("The console was called");
        for (Task smth : listOfTasks) {
            switch (optionValue) {
                case 1:
                    int taskId = view.changeIdOfTask();
                    smth.setId(taskId);
                    logger.info("The id of the task was changed");
                    break;
                case 2:
                    String taskName = view.changeTitleOfTask();
                    smth.setTitle(taskName);
                    logger.info("The title of the task was changed");
                    break;
                case 3:
                    String answer = view.changeTypeOfTask();
                    if (answer.toLowerCase().equals("yes")) {
                        LocalDateTime startTime = view.changeStartTimeOfTask();
                        LocalDateTime endTime = view.changeEndTimeOfTask();
                        int taskInterval = view.changeIntervalOfTask();
                        if ((startTime == null) || (endTime == null)
                                || (taskInterval == 0)) {
                            System.out.println("Wrong input make sure try again");
                            view.changeStartTimeOfTask();
                            view.changeEndTimeOfTask();
                            view.changeIntervalOfTask();
                        } else {
                            smth.setTime(startTime, endTime, taskInterval);
                            logger.info("The repeated time and interval were changed");
                        }
                    } else if (answer.toLowerCase().equals("no")) {
                        LocalDateTime taskTime = view.changeTimeOfTask();
                        smth.setTime(taskTime);
                        logger.info("The time of the task was changed");
                    } else {
                        logger.error("Wrong input, make sure "
                                + "that you write yes or no!!!");
                        view.changeTimeOfTask();
                    }
                case 4:
                    int taskStatus = view.changeStatusOfTask();
                    if (taskStatus == 1) {
                        smth.setActive(true);
                        logger.info("The task is now active");
                    } else if (taskStatus == 0) {
                        smth.setActive(false);
                        logger.info("The task turn off");
                    } else {
                        logger.error("Wrong input, make "
                                + "sure that you enter number 1 or 0!!");
                        view.changeStatusOfTask();
                    }
                default:
                    System.out.println("Wrong input by user!!!!" + new AssertionError());
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
            TaskIO.readText(listOfTasks,
                    (new File(folderName + File.separatorChar + nameFile + ".json")));
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
        System.out.println("The calendar was created");
        view.displayCreatedCalendar(defaultCalendar);
        runSecondaryMenu();
    }

    /**
     * Implementing (override) showTaskDetails() method
     * from interface.
     */
    @Override
    public void showTaskDetails() {
        System.out.println("--------ALL YOUR TASKS--------");
        System.out.println();
        for (int i = 0; i < listOfTasks.size(); i++) {
            Task t = listOfTasks.getTask(i);
            if (t.isRepeated()) {
                System.out.printf("Id : " + t.getId());
                System.out.printf("Title : " + t.getTitle());
                System.out.printf("Start Time : " + t.getStartTime());
                System.out.printf("End Time : " + t.getEndTime());
                System.out.printf("Repetition of period : " + t.getRepeatInterval());
            } else if (!t.isActive()) {
                System.out.printf("Id : " + t.getId());
                System.out.printf("Title : " + t.getTitle());
                System.out.printf("Task Time : " + t.getTime());
            } else {
                System.out.printf("Id : " + t.getId());
                System.out.printf("Title : " + t.getTitle());
                System.out.printf("Start Time : " + t.getStartTime());
                System.out.printf("End Time : " + t.getEndTime());
            }
        }
    }

    /**
     * Implementing (override) showListOfTask() method
     * from interface.
     */
    @Override
    public void showListOfTask() {
        view.displayListOfTasks(listOfTasks);
    }

//    /**
//     * The path where the file is stored.
//     */
//    private String pathForFile = "save" + File.separatorChar + "point"
//            + File.separatorChar + nameFile;
//    /**
//     * The name of directory to store files.
//     */
//    private String dirPath = "save";

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
            runMainApplication();
        } else {
            System.out.println("Something wrong try again");
            processSavingWork();
        }
        logger.info("The session was saved");
    }

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
            System.out.println("The file starts creating.........");
//            logger.info("The file starts creating.........");
            if (fileTxt.createNewFile()) {
                System.out.println("Your file is created here '"
                        + defaultFile + "'");
            } else {
                System.out.println("File've already existed");
//                logger.info("File've already existed");
                PrintWriter writer = new PrintWriter(defaultFile);
                writer.print("");
                writer.close();
            }
//            TaskIO.writeText(listOfTasks, fileTxt);
            System.out.println("The process of "
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
            System.out.println("The directory starts creating........");
//            logger.info("The directory starts creating........");
            if (fileDirectory.mkdir()) {
                System.out.println("The directory '"
                        + dirPath + "' is created");
//                logger.info("The directory '"
//                        + dirPath + "' is created");
            } else {
                System.out.println("Don't enough "
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
}
