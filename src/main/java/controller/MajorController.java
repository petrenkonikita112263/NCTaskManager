package controller;

import model.ArrayTaskList;
import model.Task;
import model.Tasks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.*;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;

import static controller.ActionButton.*;
import static model.TaskIO.read;
import static model.TaskIO.writeText;


/**
 * Class that implements interface of controller.
 *
 * @author Nikita
 */
public class MajorController implements CoreController {

    /**
     * Adding logger to the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(MajorController.class);

    /**
     * Instance of list.
     */
    private ArrayTaskList listOfTasks;

    /**
     * Instance of PrimaryView.
     */
    private PrimaryView view;

    /**
     * Instance of SecondaryView.
     */
    private SecondaryView secondaryView;
    /**
     * Instance of AddView.
     */
    private AddView addView;
    /**
     * Instance of DateView.
     */
    private DateView dateView;
    /**
     * Instance of SaveView.
     */
    private SaveView saveView;
    /**
     * Instance of ChangeView.
     */
    private ChangeView changeView;
    /**
     * Instance of DeleteView.
     */
    private DeleteView deleteView;
    /**
     * Instance of DisplayView.
     */
    private DisplayView displayView;
    /**
     * Instance of ReadView.
     */
    private ReadView readView;
    /**
     * Instance of Thread.
     */
    private ConcurrencyNotification notificationThread;
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
        secondaryView = new SecondaryView();
        addView = new AddView();
        dateView = new DateView();
        saveView = new SaveView();
        changeView = new ChangeView();
        deleteView = new DeleteView();
        displayView = new DisplayView();
        readView = new ReadView();
        notificationThread =
                new ConcurrencyNotification(listOfTasks, new NotificationView());
        notificationThread.setDaemon(true);
        notificationThread.start();
        LOGGER.info("The thread is running. Notification works");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runMainApplication() throws IOException {
        view.displayInfo();
        LOGGER.info("The main menu was called");
        int mainMenuNumber = view.getUserInput();
        LOGGER.info("Console was called");
        switch (mainMenuNumber) {
            case RESUME_APPLICATION_WORK:
                continueWork();
                runSecondaryMenu();
                break;
            case LOAD_TASK_LIST:
                readFromFile();
                runSecondaryMenu();
                break;
            case EMPTY_TASK_LIST:
                createEmptyTaskList();
                runSecondaryMenu();
                break;
            case TERMINATE_APPLICATION:
                view.closeInput();
                notificationThread.interrupt();
                System.exit(0);
            default:
                runMainApplication();
        }
    }

    /**
     * Private method that run more duties in application.
     *
     * @throws IOException input|output exception, failure during reading,
     *                     writing information
     */
    private void runSecondaryMenu() throws IOException {
        LOGGER.info("The additional menu was called");
        secondaryView.displayInfo();
        int additionalNumber = secondaryView.getIntegerValue();
        LOGGER.info("Console was called");
        switch (additionalNumber) {
            case ADD_TASK:
                processAddingTask();
                break;
            case CHANGE_EXISTED_TASK:
                processChangingTask();
                break;
            case DELETE_EXISTED_TASK:
                processDeletingTask();
                break;
            case PRINT_TASK_LIST:
                displayListOfTasks(listOfTasks);
                break;
            case PRINT_CALENDAR:
                displayCalendar();
                break;
            case SAVE_TASK:
                processSavingWork();
                break;
            case CLOSE_APP:
                view.closeInput();
                notificationThread.interrupt();
                System.exit(0);
            default:
                runSecondaryMenu();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processAddingTask() throws IOException {
        String title = addView.addTaskTitle();
        String wordAnswer = addView.selectTheTypeForTask();
        switch (wordAnswer.toLowerCase()) {
            case "yes":
                LocalDateTime start = dateView.inputDateTime();
                LocalDateTime end = dateView.inputDateTime();
                int interval = addView.addInterval();
                Task repTask = new Task(title, start, end, interval);
                listOfTasks.add(repTask);
                LOGGER.info("The repetead task was added");
                processSavingWork();
                runSecondaryMenu();
                break;
            case "no":
                LocalDateTime time = dateView.inputDateTime();
                Task normalTask = new Task(title, time);
                listOfTasks.add(normalTask);
                LOGGER.info("The non-repetead task was added");
                processSavingWork();
                runSecondaryMenu();
                break;
            default:
                runSecondaryMenu();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void processDeletingTask() throws IOException {
        displayDetailAboutTask(listOfTasks);
        int id = deleteView.removeSomeTask();
        listOfTasks.removeElement(id);
        LOGGER.info("Task was deleted");
        processSavingWork();
        runSecondaryMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processChangingTask() throws IOException {
        LOGGER.info("The process of changing task was started");
        LOGGER.info("The console was called");
        String answer = changeView.selectTheTypeForTask();
        for (Task smth : listOfTasks) {
            switch (answer.toLowerCase()) {
                case "yes":
                    LocalDateTime dateTimeStart_1, dateTimeEnd_1, dateTime_1;
                    int timeInterval_1;
                    displayDetailAboutTask(listOfTasks);
                    int taskIndex_1 = changeView.getTaskIndex();
                    listOfTasks.getTask(taskIndex_1);
                    changeView.changeFunctionalityOfTask();
                    int changeOption_1 = secondaryView.getIntegerValue();
                    switch (changeOption_1) {
                        case 1:
                            String taskName = addView.addTaskTitle();
                            smth.setTitle(taskName);
                            LOGGER.info("The title of the task was changed");
                            processSavingWork();
                            runSecondaryMenu();
                            break;
                        case 2:
                            LocalDateTime startTime = dateView.inputDateTime();
                            LocalDateTime endTime = dateView.inputDateTime();
                            smth.setStart(startTime);
                            smth.setEnd(endTime);
                            LOGGER.info("The start and end time was changed");
                            processSavingWork();
                            runSecondaryMenu();
                            break;
                        case 3:
                            if (smth.isRepeated()) {
                                smth.setRepeated(false);
                                dateTime_1 = dateView.inputDateTime();
                                smth.setTime(dateTime_1);
                                LOGGER.info("The task is nonreptead now");
                                processSavingWork();
                                runSecondaryMenu();
                                break;
                            } else {
                                smth.setRepeated(true);
                                dateTimeStart_1 = dateView.inputDateTime();
                                dateTimeEnd_1 = dateView.inputDateTime();
                                timeInterval_1 = addView.addInterval();
                                smth.setTime(dateTimeStart_1, dateTimeEnd_1, timeInterval_1);
                                processSavingWork();
                                runSecondaryMenu();
                                LOGGER.info("The task is repetead now");
                                break;
                            }
                        case 4:
                            int taskStatus = changeView.changeStatusOfTask();
                            switch (taskStatus) {
                                case 0:
                                    smth.setActive(false);
                                    LOGGER.info("The task isn't active");
                                    processSavingWork();
                                    runSecondaryMenu();
                                    break;
                                case 1:
                                    smth.setActive(true);
                                    LOGGER.info("The task is active");
                                    processSavingWork();
                                    runSecondaryMenu();
                                    break;
                                default:
                                    runSecondaryMenu();
                            }
                        case 5:
                            int taskInterval = addView.addInterval();
                            smth.setInterval(taskInterval);
                            LOGGER.info("The interval was changed");
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
                    int taskIndex_2 = changeView.getTaskIndex();
                    listOfTasks.getTask(taskIndex_2);
                    changeView.changeFunctionalityOfTask();
                    int changeOption_2 = secondaryView.getIntegerValue();
                    switch (changeOption_2) {
                        case 1:
                            String taskName = addView.addTaskTitle();
                            smth.setTitle(taskName);
                            LOGGER.info("The title of the task was changed");
                            processSavingWork();
                            runSecondaryMenu();
                            break;
                        case 2:
                            LocalDateTime time = dateView.inputDateTime();
                            smth.setTime(time);
                            LOGGER.info("The time was changed");
                            processSavingWork();
                            runSecondaryMenu();
                            break;
                        case 3:
                            if (smth.isRepeated()) {
                                smth.setRepeated(false);
                                dateTime_2 = dateView.inputDateTime();
                                smth.setTime(dateTime_2);
                                LOGGER.info("The task is nonreptead now");
                                processSavingWork();
                                runSecondaryMenu();
                                break;
                            } else {
                                smth.setRepeated(true);
                                dateTimeStart_2 = dateView.inputDateTime();
                                dateTimeEnd_2 = dateView.inputDateTime();
                                timeInterval_2 = addView.addInterval();
                                smth.setTime(dateTimeStart_2, dateTimeEnd_2, timeInterval_2);
                                processSavingWork();
                                runSecondaryMenu();
                                LOGGER.info("The task is repetead now");
                                break;
                            }
                        case 4:
                            int taskStatus = changeView.changeStatusOfTask();
                            switch (taskStatus) {
                                case 0:
                                    smth.setActive(false);
                                    LOGGER.info("The task isn't active");
                                    processSavingWork();
                                    runSecondaryMenu();
                                    break;
                                case 1:
                                    smth.setActive(true);
                                    LOGGER.info("The task is active");
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
        displayView.getInfoAboutCreation();
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
                LOGGER.error("File doesn't exist", e);
            }
            if (printWriter != null) {
                printWriter.print("");
                printWriter.close();
            }
        }
    }

    /**
     * Additional (private) method that creates file with subfolder.
     */
    private void createFileWithFolder() {
        Path path = Paths.get("temp_test" + File.separatorChar + pathToDefaultFileName + ".json");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            LOGGER.error("Can't create folder by path", e);
            e.printStackTrace();
        }
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            LOGGER.error("Can't create file in folder", e);
        } catch (IOException e) {
            LOGGER.error("Failure in reading path", e);
        }
    }

    /**
     * Private method that allow to read task list from GSON file.
     *
     * @param nameFile file name that stores the task list
     */
    private void readFileWithTasks(String nameFile) {
        try {
            read(listOfTasks,
                    (new FileReader("temp_test" + File.separatorChar + nameFile + ".json")));
        } catch (IOException e) {
            LOGGER.error("Error with reading process from the file", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayCalendar() throws IOException {
        LocalDateTime startDate = displayView.addTimeLimit_1();
        LocalDateTime limitDate = displayView.addTimeLimit_2();
        SortedMap<LocalDateTime, Set<Task>> defaultCalendar =
                Tasks.calendar(listOfTasks, startDate, limitDate);
        LOGGER.info("The calendar was created");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (SortedMap.Entry<LocalDateTime, Set<Task>> content : defaultCalendar.entrySet()) {
            for (Task task : content.getValue()) {
                String taskTitle = "Task title: " + task.getTitle();
                displayView.displayTaskTitle(taskTitle);
            }
            String taskDate = "Its date: " + content.getKey().format(formatter);
            displayView.displayTaskdate(taskDate);
        }
        runSecondaryMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processSavingWork() throws IOException {
        try {
            writeText(listOfTasks,
                    (new File("temp_test" + File.separatorChar + pathToDefaultFileName + ".json")));
        } catch (IOException e) {
            LOGGER.error("Error with reading process from the file", e);
        }
        String messageAboutPath = "temp_test" + File.separatorChar + pathToDefaultFileName + ".json";
        saveView.getInfoAboutSavingFile(messageAboutPath);
        runSecondaryMenu();
    }

    /**
     * Private additional method that read tasks from GSON file,
     * that name user input.
     */
    private void readFromFile() {
        String fileName = readView.getFileName();
        File file = new File("temp_test" + File.separatorChar + fileName + ".json");
        if (!file.exists()) {
            Path path = Paths.get("temp_test" + File.separatorChar + fileName + ".json");
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                LOGGER.error("Can't create folder by path", e);
                e.printStackTrace();
            }
            try {
                Files.createFile(path);
            } catch (FileAlreadyExistsException e) {
                LOGGER.error("Can't create file in folder", e);
            } catch (IOException e) {
                LOGGER.error("Failure in reading path", e);
            }
            String message = "temp_test" + File.separatorChar + fileName + ".json";
            readView.getMessageAboutDontFind(message);
        } else {
            readFileWithTasks(fileName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void continueWork() {
        createFileWithFolder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayListOfTasks(ArrayTaskList taskList) {
        if (taskList.size() == 0) {
            displayView.getMessageAboutEmptiness();
            try {
                runSecondaryMenu();
            } catch (IOException e) {
                LOGGER.error("Can't access to additional menu of application ", e);
            }
        }
        for (Task someTask : taskList) {
            String result = String.valueOf(someTask);
            displayView.getViewForList(result);
        }
        try {
            runSecondaryMenu();
        } catch (IOException e) {
            LOGGER.error("Can't access to additional menu of application ", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayDetailAboutTask(ArrayTaskList taskList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (taskList.size() == 0) {
            displayView.getMessageAboutEmptiness();
            try {
                runSecondaryMenu();
            } catch (IOException e) {
                LOGGER.error("Can't access to additional menu of application ", e);
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
                displayView.getViewForRepTask(resultRepTask);
            } else if (!t.isRepeated()) {
                String resultNorTask = i + "\tYou have the non-repetead task with title : " + t.getTitle()
                        + "\nTask starts at " + formatter.format(t.getTime());
                displayView.getViewForNorTask(resultNorTask);
            }
        }
    }
}
