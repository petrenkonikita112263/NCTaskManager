package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import model.Tasks;
import org.apache.log4j.Logger;
import view.NotificationView;
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
     * Instance of list.
     */
    private ArrayTaskList listOfTasks;

    /**
     * Instance of PrimaryView.
     */
    private PrimaryView view;

    /**
     * The name of the file that stores the session work.
     */
    private String folderName = "savepoint";

    /**
     * EVC constructor.
     */
    public MajorController() {
        listOfTasks = new ArrayTaskList();
        view = new PrimaryView();
        ConcurrencyNotification notificationThread =
                new ConcurrencyNotification(listOfTasks, new NotificationView());
        notificationThread.setDaemon(true);
        notificationThread.start();
        logger.info("The thread is running. Notification works");
    }

    /**
     * Private method that display info message about
     * new created list.
     */
    private void createEmptyTaskList() {
        view.getInfoAboutCreation();
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
                readFileWithTasks();
                runSecondaryMenu();
                break;
            case 2:
                continueWork();
                runSecondaryMenu();
                break;
            case 3:
                createEmptyTaskList();
                runSecondaryMenu();
                break;
            case 4:
                view.closeInput();
                System.exit(0);
            default:
                throw new AssertionError("Something went wrong, fatal error");
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
        int defaultNumber = view.getNumberForFurtherAction();
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
                break;
            case 5:
                displayCalendar();
                break;
            case 6:
                processSavingWork();
                break;
            case 7:
                view.closeInput();
                System.exit(0);
            default:
                throw  new AssertionError("Something went wrong, fatal error");
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
        switch (wordAnswer.toLowerCase()) {
            case "yes":
                LocalDateTime start = view.inputDateTime();
                LocalDateTime end = view.inputDateTime();
                int interval = view.addInterval();
                Task repTask = new Task(title, start, end, interval * 60);
                listOfTasks.add(repTask);
                logger.info("The repetead task was added");
                processSavingWork();
                runSecondaryMenu();
                break;
            case "no":
                LocalDateTime time = view.inputDateTime();
                Task normalTask = new Task(title, time);
                listOfTasks.add(normalTask);
                logger.info("The non-repetead task was added");
                processSavingWork();
                runSecondaryMenu();
                break;
            default:
                runMainApplication();
        }
    }


    /**
     * Implementing (override) removeSomeTask() method
     * from interface.
     */
    @Override
    public void processDeletingTask() throws IOException {
        displayDetailAboutTask(listOfTasks);
        int id = view.removeSomeTask();
        listOfTasks.removeElement(id);
        logger.info("Task was deleted");
        processSavingWork();
        runSecondaryMenu();
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
        logger.info("The console was called");
        String answer = view.selectTheTypeForTask();
        for (Task smth : listOfTasks) {
            switch (answer.toLowerCase()) {
                case "yes":
                    view.getViewForTask(listOfTasks);
                    int taskIndex_1 = view.getTaskIndex();
                    listOfTasks.getTask(taskIndex_1);
                    view.changeFunctionalityOfTask();
                    int changeOption_1 = view.getNumberForFurtherAction();
                    switch (changeOption_1) {
                        case 1:
                            String taskName = view.changeTitleOfTask();
                            smth.setTitle(taskName);
                            logger.info("The title of the task was changed");
                            processSavingWork();
                            processChangingTask();
                            break;
                        case 2:
                            LocalDateTime startTime = view.inputDateTime();
                            LocalDateTime endTime = view.inputDateTime();
                            smth.setStart(startTime);
                            smth.setEnd(endTime);
                            logger.info("The start and end time was changed");
                            processSavingWork();
                            processChangingTask();
                            break;
                        case 3:
                            int taskStatus = view.changeStatusOfTask();
                            switch (taskStatus) {
                                case 0:
                                    smth.setRepeated(false);
                                    smth.setTime(LocalDateTime.now());
                                    logger.info("The task is nonreptead now");
                                    processSavingWork();
                                    break;
                                case 1:
                                    smth.setRepeated(true);
                                    smth.setTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1), 0);
                                    logger.info("The task is repetead now");
                                    processSavingWork();
                                    break;
                                case 2:
                                    smth.setActive(false);
                                    logger.info("The task isn't active");
                                    processSavingWork();
                                    break;
                                case 3:
                                    smth.setActive(true);
                                    logger.info("The task is active");
                                    processSavingWork();
                                    break;
                                default:
                                    runSecondaryMenu();
                            }
                        case 4:
                            int taskInterval = view.changeIntervalOfTask();
                            smth.setInterval(taskInterval * 60);
                            logger.info("The interval was changed");
                            processSavingWork();
                            processChangingTask();
                            break;
                        case 5:
                            runSecondaryMenu();
                            break;
                        default:
                            throw  new AssertionError("Something went wrong, fatal error");
                    }
                case "no":
                    view.getViewForTask(listOfTasks);
                    int taskIndex_2 = view.getTaskIndex();
                    listOfTasks.getTask(taskIndex_2);
                    view.changeFunctionalityOfTask();
                    int changeOption_2 = view.getNumberForFurtherAction();
                    switch (changeOption_2) {
                        case 1:
                            String taskName = view.changeTitleOfTask();
                            smth.setTitle(taskName);
                            logger.info("The title of the task was changed");
                            processSavingWork();
                            processChangingTask();
                            break;
                        case 2:
                            LocalDateTime time = view.inputDateTime();
                            smth.setTime(time);
                            logger.info("The time was changed");
                            processSavingWork();
                            processChangingTask();
                            break;
                        case 3:
                            int taskStatus = view.changeStatusOfTask();
                            switch (taskStatus) {
                                case 0:
                                    smth.setRepeated(false);
                                    smth.setTime(LocalDateTime.now());
                                    logger.info("The task is nonreptead now");
                                    processSavingWork();
                                    break;
                                case 1:
                                    smth.setRepeated(true);
                                    smth.setTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1), 0);
                                    processSavingWork();
                                    logger.info("The task is repetead now");
                                    break;
                                case 2:
                                    smth.setActive(false);
                                    logger.info("The task isn't active");
                                    processSavingWork();
                                    break;
                                case 3:
                                    smth.setActive(true);
                                    logger.info("The task is active");
                                    processSavingWork();
                                    break;
                                default:
                                    runSecondaryMenu();
                            }
                        case 4:
                            runSecondaryMenu();
                            break;
                        default:
                            throw  new AssertionError("Something went wrong, fatal error");
                    }
                default:
                    runSecondaryMenu();
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
    public void displayCalendar() throws IOException {
        LocalDateTime startDate = view.addTimeLimit_1();
        LocalDateTime limitDate = view.addTimeLimit_2();
        SortedMap<LocalDateTime, Set<Task>> defaultCalendar =
                Tasks.calendar(listOfTasks, startDate, limitDate);
        logger.info("The calendar was created");
        view.displayCreatedCalendar(defaultCalendar);
        runSecondaryMenu();
    }

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
        switch (optionNumber) {
            case 1:
                String fileName = view.getFileName();
                saveFileWithTasks(fileName);
                break;
            default:
                runMainApplication();
        }
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
            if (fileTxt.createNewFile()) {
                logger.info("Your file is created here '"
                        + defaultFile + "'");
            } else {
                logger.info("File've already existed");
                PrintWriter writer = new PrintWriter(defaultFile);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            logger.error("The error occurred ", e);
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
        logger.info("The process of creating folder was started");
        File fileDirectory = new File(dirPath);
        if (!fileDirectory.exists()) {
            logger.info("The directory starts creating........");
            if (fileDirectory.mkdir()) {
                logger.info("The directory '"
                        + dirPath + "' is created");
            } else {
                logger.error("Don't enough "
                        + "permission to create directory");
            }
        }
        logger.info("The process of creating folder was finished");
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
        if (taskList.size() == 0) {
            view.getMessageAboutEmptiness();
            try {
                runSecondaryMenu();
            } catch (IOException e) {
                logger.error("Can't access to additional menu of application ", e);
            }
        }
        view.getViewForList(taskList);
        try {
            runSecondaryMenu();
        } catch (IOException e) {
            logger.error("Can't access to additional menu of application ", e);
        }
    }

    /**
     * Implementation (override) displayDetailAboutTask() method from
     * interface.
     */
    @Override
    public void displayDetailAboutTask(ArrayTaskList taskList) {
        if (taskList.size() == 0) {
            view.getMessageAboutEmptiness();
            try {
                runSecondaryMenu();
            } catch (IOException e) {
                logger.error("Can't access to additional menu of application ", e);
            }
        }
        view.getViewForTask(taskList);
    }
}
