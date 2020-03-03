package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import model.Tasks;
import org.apache.log4j.Logger;
import view.NotificationView;
import view.PrimaryView;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * The name of the folder that stores file.
     */
    private String folderName = "savepoint";

    /**
     * The name of the default file for application.
     */
    private String pathToDefaultFileName = "test_manager";

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
                readFromFile();
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
                runMainApplication();
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
                runSecondaryMenu();
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
                Task repTask = new Task(title, start, end, interval);
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
                runSecondaryMenu();
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
                    LocalDateTime dateTimeStart_1, dateTimeEnd_1, dateTime_1;
                    int timeInterval_1;
                    displayDetailAboutTask(listOfTasks);
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
                            runSecondaryMenu();
                            break;
                        case 2:
                            LocalDateTime startTime = view.inputDateTime();
                            LocalDateTime endTime = view.inputDateTime();
                            smth.setStart(startTime);
                            smth.setEnd(endTime);
                            logger.info("The start and end time was changed");
                            processSavingWork();
                            runSecondaryMenu();
                            break;
                        case 3:
                            if (smth.isRepeated()) {
                                smth.setRepeated(false);
                                dateTime_1 = view.inputDateTime();
                                smth.setTime(dateTime_1);
                                logger.info("The task is nonreptead now");
                                processSavingWork();
                                runSecondaryMenu();
                                break;
                            } else {
                                smth.setRepeated(true);
                                dateTimeStart_1 = view.inputDateTime();
                                dateTimeEnd_1 = view.inputDateTime();
                                timeInterval_1 = view.addInterval();
                                smth.setTime(dateTimeStart_1, dateTimeEnd_1, timeInterval_1);
                                processSavingWork();
                                runSecondaryMenu();
                                logger.info("The task is repetead now");
                                break;
                            }
                        case 4:
                            int taskStatus = view.changeStatusOfTask();
                            switch (taskStatus) {
                                case 0:
                                    smth.setActive(false);
                                    logger.info("The task isn't active");
                                    processSavingWork();
                                    runSecondaryMenu();
                                    break;
                                case 1:
                                    smth.setActive(true);
                                    logger.info("The task is active");
                                    processSavingWork();
                                    runSecondaryMenu();
                                    break;
                                default:
                                    runSecondaryMenu();
                            }
                        case 5:
                            int taskInterval = view.changeIntervalOfTask();
                            smth.setInterval(taskInterval);
                            logger.info("The interval was changed");
                            processSavingWork();
                            runSecondaryMenu();
                            break;
                        case 6:
                            runSecondaryMenu();
                            break;
                        default:
                            processChangingTask();
                    }
                case "no":
                    LocalDateTime dateTimeStart_2, dateTimeEnd_2, dateTime_2;
                    int timeInterval_2;
                    displayDetailAboutTask(listOfTasks);
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
                            runSecondaryMenu();
                            break;
                        case 2:
                            LocalDateTime time = view.inputDateTime();
                            smth.setTime(time);
                            logger.info("The time was changed");
                            processSavingWork();
                            runSecondaryMenu();
                            break;
                        case 3:
                            if (smth.isRepeated()) {
                                smth.setRepeated(false);
                                dateTime_2 = view.inputDateTime();
                                smth.setTime(dateTime_2);
                                logger.info("The task is nonreptead now");
                                processSavingWork();
                                runSecondaryMenu();
                                break;
                            } else {
                                smth.setRepeated(true);
                                dateTimeStart_2 = view.inputDateTime();
                                dateTimeEnd_2 = view.inputDateTime();
                                timeInterval_2 = view.addInterval();
                                smth.setTime(dateTimeStart_2, dateTimeEnd_2, timeInterval_2);
                                processSavingWork();
                                runSecondaryMenu();
                                logger.info("The task is repetead now");
                                break;
                            }
                        case 4:
                            int taskStatus = view.changeStatusOfTask();
                            switch (taskStatus) {
                                case 0:
                                    smth.setActive(false);
                                    logger.info("The task isn't active");
                                    processSavingWork();
                                    runSecondaryMenu();
                                    break;
                                case 1:
                                    smth.setActive(true);
                                    logger.info("The task is active");
                                    processSavingWork();
                                    runSecondaryMenu();
                                    break;
                                default:
                                    runSecondaryMenu();
                                    break;
                            }
                        case 7:
                            runSecondaryMenu();
                            break;
                        default:
                            processChangingTask();
                    }
                default:
                    runSecondaryMenu();
            }
        }
    }

    /**
     * Private method that display info message about
     * new created list.
     */
    private void createEmptyTaskList() {
        checkFileForContent();
        view.getInfoAboutCreation();
    }

    /**
     * Additional method that check defaul file for existing and
     * remove content from it, if should one write to the file.
     */
    private void checkFileForContent() {
        File file = new File(pathToDefaultFileName);
        if ((!file.exists()) || (file.length() == 0)) {
            createFileWithFolder();
        } else {
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                logger.error("File doesn't exist", e);
            }
            printWriter.print("");
            printWriter.close();
        }
    }

    /**
     * Additional (private) method that creates file with subfolder.
     */
    private void createFileWithFolder() {
        Path path = Paths.get("temp_test" + File.separatorChar + pathToDefaultFileName);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            logger.error("Can't create folder by path", e);
            e.printStackTrace();
        }
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            logger.error("Can't create file in folder", e);
        } catch (IOException e) {
            logger.error("Failure in reading path", e);
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
                    (new FileReader("temp_test" + File.separatorChar + nameFile + ".json")));
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (SortedMap.Entry<LocalDateTime, Set<Task>> content : defaultCalendar.entrySet()) {
            for (Task task : content.getValue()) {
                String taskTitle = "Task title: " + task.getTitle();
                view.displayTaskTitle(taskTitle);
            }
            String taskDate = "Its date: " + content.getKey().format(formatter);
            view.displayTaskdate(taskDate);
        }
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
        try {
            TaskIO.writeText(listOfTasks,
                    (new File("temp_test" + File.separatorChar + pathToDefaultFileName + ".json")));
        } catch (IOException e) {
            logger.error("Error with reading process from the file", e);
        }
        String messageAboutPath = "temp_test" + File.separatorChar + pathToDefaultFileName + ".json";
        view.getInfoAboutSavingFile(messageAboutPath);
        runSecondaryMenu();
    }

    /**
     * Private additional method that read tasks from GSON file,
     * that name user input.
     */
    private void readFromFile() {
        String fileName = view.getFileName();
        File file = new File("temp_test" + File.separatorChar + fileName + ".json");
        if (!file.exists()) {
            Path path = Paths.get("temp_test" + File.separatorChar + fileName);
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                logger.error("Can't create folder by path", e);
                e.printStackTrace();
            }
            try {
                Files.createFile(path);
            } catch (FileAlreadyExistsException e) {
                logger.error("Can't create file in folder", e);
            } catch (IOException e) {
                logger.error("Failure in reading path", e);
            }
            String message = "temp_test" + File.separatorChar + fileName;
            view.getMessageAboutDontFind(message);
        } else {
            readFileWithTasks(fileName);
        }
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
        createFileWithFolder();
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
        for (Task someTask : taskList) {
            String result = String.valueOf(someTask);
            view.getViewForList(result);
        }
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (taskList.size() == 0) {
            view.getMessageAboutEmptiness();
            try {
                runSecondaryMenu();
            } catch (IOException e) {
                logger.error("Can't access to additional menu of application ", e);
            }
        }
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.getTask(i);
            if (t.isRepeated()) {
                String resultRepTask = i + "\tYou have the repetead tesk with title : " + t.getTitle()
                        + "\nTask starts at " + formatter.format(t.getStartTime())
                        + "\nTask ends at " + formatter.format(t.getEndTime())
                        + "\nthe interval between start and end time is "
                        + t.getRepeatInterval();
                view.getViewForRepTask(resultRepTask);
            } else if (!t.isRepeated()) {
                String resultNorTask = i + "\tYou have the non-repetead task with title : " + t.getTitle()
                        + "\nTask starts at " + formatter.format(t.getTime());
                view.getViewForNorTask(resultNorTask);
            }
        }
    }
}
