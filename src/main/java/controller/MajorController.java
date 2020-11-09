package controller;

import model.Task;
import model.Tasks;
import view.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;

import static java.time.format.DateTimeFormatter.ofPattern;
import static model.TaskIO.*;
import static model.TypeOption.*;
import static org.apache.logging.log4j.LogManager.getLogger;


/**
 * Class that implements interface of controller.
 *
 * @author Nikita
 */
public class MajorController extends BaseController implements CoreController {

    /**
     * Instance of PrimaryView.
     */
    private final PrimaryView view = new PrimaryView();

    /**
     * Instance of SecondaryView.
     */
    private final SecondaryView secondaryView = new SecondaryView();
    /**
     * Instance of AddView.
     */
    private final AddView addView = new AddView();
    /**
     * Instance of DateView.
     */
    private final DateView dateView = new DateView();
    /**
     * Instance of SaveView.
     */
    private final SaveView saveView = new SaveView();
    /**
     * Instance of ChangeView.
     */
    private final ChangeView changeView = new ChangeView();
    /**
     * Instance of DeleteView.
     */
    private final DeleteView deleteView = new DeleteView();
    /**
     * Instance of DisplayView.
     */
    private final DisplayView displayView = new DisplayView();
    /**
     * Instance of ReadView.
     */
    private final ReadView readView = new ReadView();

    private final ConcurrencyNotification thread;

    /**
     * The name of the default file for application.
     */
    private final String emptyFile = "test_manager";

    public MajorController(CopyOnWriteArrayList<Task> listOfTasks, BufferedReader bufReader) {
        this.listOfTasks = listOfTasks;
        this.bufReader = bufReader;
        LOGGER = getLogger(MajorController.class);
        thread = new ConcurrencyNotification(listOfTasks, new NotificationView());
        thread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runMainApplication() throws IOException {
        bufReader = new BufferedReader(new InputStreamReader(System.in));
        view.displayInfo();
        LOGGER.info("The main menu was called");
        int mainMenuNumber = view.getUserInput(bufReader);
        LOGGER.info("Console was called");
        if (mainMenuNumber == RESUME_APPLICATION_WORK.getNumberCode()) {
            continueWork();
            runSecondaryMenu();
        } else if (mainMenuNumber == LOAD_TASK_LIST.getNumberCode()) {
            readFromFile();
            runSecondaryMenu();
        } else if (mainMenuNumber == EMPTY_TASK_LIST.getNumberCode()) {
            createEmptyTaskList();
            runSecondaryMenu();
        } else if (mainMenuNumber == TERMINATE_APPLICATION.getNumberCode()) {
            terminateApp();
        } else {
            runMainApplication();
        }
    }

    private void terminateApp() {
        try {
            bufReader.close();
        } catch (IOException e) {
            LOGGER.error("Can't close the main IO resources", e);
        }
        thread.interrupt();
        try {
            thread.sleep(5L);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        System.exit(0);
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
        int additionalNumber = secondaryView.getIntegerValue(bufReader);
        LOGGER.info("Console was called");
        if (additionalNumber == ADD_TASK.getNumberCode()) {
            processAddingTask();
            runSecondaryMenu();
        } else if (additionalNumber == CHANGE_EXISTED_TASK.getNumberCode()) {
            processChangingTask();
            runSecondaryMenu();
        } else if (additionalNumber == DELETE_EXISTED_TASK.getNumberCode()) {
            processDeletingTask();
            runSecondaryMenu();
        } else if (additionalNumber == PRINT_TASK_LIST.getNumberCode()) {
            displayListOfTasks(listOfTasks);
            runSecondaryMenu();
        } else if (additionalNumber == PRINT_CALENDAR.getNumberCode()) {
            displayCalendar();
            runSecondaryMenu();
        } else if (additionalNumber == SAVE_TASK.getNumberCode()) {
            processSavingWork();
            runSecondaryMenu();
        } else if (additionalNumber == CLOSE_APP.getNumberCode()) {
            terminateApp();
        } else {
            runSecondaryMenu();
        }
    }

    /**
     * {@inheritDoc}
     */
//    @Override
    public void processAddingTask() {
        String title = addView.addTaskTitle(bufReader);
        int userAnswer = addView.selectTheTypeForTask(bufReader);
        if (userAnswer == ADD_SCHEDULED_TASK.getNumberCode()) {
            LocalDateTime start = dateView.inputDateTime(bufReader);
            LocalDateTime end = dateView.inputDateTime(bufReader);
            int interval = addView.addInterval(bufReader);
            Task repTask = new Task(title, start, end, interval);
            listOfTasks.add(repTask);
            LOGGER.info("The repeated task was added");
            processSavingWork();
        } else if (userAnswer == ADD_ORDINARY_TASK.getNumberCode()) {
            LocalDateTime time = dateView.inputDateTime(bufReader);
            Task normalTask = new Task(title, time);
            listOfTasks.add(normalTask);
            LOGGER.info("The no-repeated task was added");
            processSavingWork();
        } else {
            processAddingTask();
        }
    }

    //    /**
//     * {@inheritDoc}
//     */
//    @Override
    public void processDeletingTask() {
        displayDetailAboutTask(listOfTasks);
        String taskName = deleteView.removeSomeTask(bufReader);
        listOfTasks.stream().forEach(t -> {
            if (t.getTitle().equals(taskName)) {
                listOfTasks.remove(t);
                LOGGER.info("Task with title - ", taskName, "  was deleted");
            } else {
                LOGGER.info("There's no any task with title - ", taskName, "  in your list");
            }
        });
        processSavingWork();
    }

    //    /**
//     * {@inheritDoc}
//     */
//    @Override
    public void processChangingTask() throws IOException {
        LOGGER.info("The process of changing task was started");
        int answerType = changeView.selectTheTypeForTask(bufReader);
        for (Task smth : listOfTasks) {
            if (answerType == CHANGE_SCHEDULED_TASK.getNumberCode()) {
                displayDetailAboutTask(listOfTasks);
                int taskIndex_1 = changeView.getTaskIndex(bufReader, listOfTasks);
                Task selectedTask = listOfTasks.get(taskIndex_1);
                changeView.changeScheduledTask();
                int changeOption_1 = secondaryView.getIntegerValue(bufReader);
                if (changeOption_1 == CHANGE_TITLE_SCHEDULED_TASK.getNumberCode()) {
                    String taskName = addView.addTaskTitle(bufReader);
                    selectedTask.setTitle(taskName);
                    LOGGER.info("The title of the task was changed");
                    processSavingWork();
                } else if (changeOption_1 == CHANGE_TIME_SCHEDULED_TASK.getNumberCode()) {
                    LocalDateTime startTime = dateView.inputDateTime(bufReader);
                    LocalDateTime endTime = dateView.inputDateTime(bufReader);
                    selectedTask.setStart(startTime);
                    selectedTask.setEnd(endTime);
                    LOGGER.info("The start and end time was changed");
                    processSavingWork();
                } else if (changeOption_1 == CHANGE_FORM_SCHEDULED_TASK_TO_ORDINARY.getNumberCode()) {
                    switchTaskType(selectedTask);
                } else if (changeOption_1 == CHANGE_STATUS_SCHEDULED_TASK.getNumberCode()) {
                    int taskStatus = changeView.changeStatusOfTask(bufReader);
                    if (taskStatus == DISABLE_SCHEDULED_TASK.getNumberCode()) {
                        selectedTask.setActive(false);
                        LOGGER.info("The task isn't active");
                        processSavingWork();
                    } else if (taskStatus == ENABLE_SCHEDULED_TASK.getNumberCode()) {
                        selectedTask.setActive(true);
                        LOGGER.info("The task is active");
                        processSavingWork();
                    }
                } else if (changeOption_1 == CHANGE_INTERVAL_SCHEDULED_TASK.getNumberCode()) {
                    int taskInterval = addView.addInterval(bufReader);
                    selectedTask.setInterval(taskInterval);
                    LOGGER.info("The interval was changed");
                    processSavingWork();
                } else {
                    processChangingTask();
                }
            } else if (answerType == CHANGE_ORDINARY_TASK.getNumberCode()) {
                displayDetailAboutTask(listOfTasks);
                int taskIndex = changeView.getTaskIndex(bufReader, listOfTasks);
                Task selectedTask = listOfTasks.get(taskIndex);
                changeView.changeOrdinaryTask();
                int changeOption_2 = secondaryView.getIntegerValue(bufReader);
                if (changeOption_2 == CHANGE_TITLE_ORDINARY_TASK.getNumberCode()) {
                    String taskName = addView.addTaskTitle(bufReader);
                    selectedTask.setTitle(taskName);
                    LOGGER.info("The title of the task was changed");
                    processSavingWork();
                } else if (changeOption_2 == CHANGE_TIME_ORDINARY_TASK.getNumberCode()) {
                    LocalDateTime time = dateView.inputDateTime(bufReader);
                    selectedTask.setTime(time);
                    LOGGER.info("The time was changed");
                    processSavingWork();
                } else if (changeOption_2 == CHANGE_FROM_ORDINARY_TO_SCHEDULED.getNumberCode()) {
                    switchTaskType(selectedTask);
                } else if (changeOption_2 == CHANGE_STATUS_ORDINARY_TASK.getNumberCode()) {
                    int taskStatus = changeView.changeStatusOfTask(bufReader);
                    if (taskStatus == DISABLE_ORDINARY_TASK.getNumberCode()) {
                        smth.setActive(false);
                        LOGGER.info("The task isn't active");
                        processSavingWork();
                    } else if (taskStatus == ENABLE_ORDINARY_TASK.getNumberCode()) {
                        smth.setActive(true);
                        LOGGER.info("The task is active");
                        processSavingWork();
                    }
                } else {
                    processChangingTask();
                }
            } else {
                processChangingTask();
            }
        }
    }

    private void switchTaskType(Task selectedTask) {
        LocalDateTime dateTime;
        LocalDateTime dateTimeStart;
        LocalDateTime dateTimeEnd;
        int timeInterval;
        if (selectedTask.isRepeated()) {
            selectedTask.setRepeated(false);
            dateTime = dateView.inputDateTime(bufReader);
            selectedTask.setTime(dateTime);
            LOGGER.info("The task is no-repeated now");
            processSavingWork();
        } else {
            selectedTask.setRepeated(true);
            dateTimeStart = dateView.inputDateTime(bufReader);
            dateTimeEnd = dateView.inputDateTime(bufReader);
            timeInterval = addView.addInterval(bufReader);
            selectedTask.setTime(dateTimeStart, dateTimeEnd, timeInterval);
            LOGGER.info("The task is repeated now");
            processSavingWork();
        }
    }

    /**
     * Private method that display info message about
     * new created list.
     */
    private void createEmptyTaskList() {
        checkFileForContent();
        writeText(listOfTasks, (new File(getFileLocation(emptyFile))));
    }

    /**
     * Additional method that check default empty file for existing and
     * remove content from it, if should one write to the file.
     */
    private void checkFileForContent() {
        File file = new File(getFileLocation(emptyFile));
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                LOGGER.info(newFile ? "The file was created" : "The file has "
                        + "already existed");
            } catch (IOException e) {
                LOGGER.error("Can't create empty file", e);
            }
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
     * {@inheritDoc}
     */
//    @Override
    public void displayCalendar() {
        LocalDateTime startDate = displayView.addTimeLimit_1(bufReader);
        LocalDateTime limitDate = displayView.addTimeLimit_2(bufReader);
        ConcurrentNavigableMap<LocalDateTime, CopyOnWriteArraySet<Task>> defaultCalendar =
                Tasks.calendar(listOfTasks, startDate, limitDate);
        LOGGER.info("The calendar was created");
        DateTimeFormatter formatter = ofPattern("yyyy-MM-dd HH:mm");
        for (SortedMap.Entry<LocalDateTime, CopyOnWriteArraySet<Task>> content : defaultCalendar.entrySet()) {
            for (Task task : content.getValue()) {
                String taskTitle = "Task title: " + task.getTitle();
                displayView.displayTaskTitle(taskTitle);
            }
            String taskDate = "Its date: " + content.getKey().format(formatter);
            displayView.displayTaskdate(taskDate);
        }
    }

    /**
     * {@inheritDoc}
     */
//    @Override
    public void processSavingWork() {
        String fileName = readView.getFileName(bufReader);
        String fileLocation = getFileLocation(fileName);
        writeText(listOfTasks, (new File(fileLocation)));
        saveView.getInfoAboutSavingFile(fileLocation);
    }

    /**
     * Private additional method that read tasks from GSON file,
     * that name user input.
     */
    private void readFromFile() {
        String fileName = readView.getFileName(bufReader);
        readText(listOfTasks, new File(getFileLocation(fileName)));
    }

    //    /**
//     * {@inheritDoc}
//     */
//    @Override
    public void continueWork() {
        readText(listOfTasks, new File(getFileLocation(emptyFile)));
    }

    //    /**
//     * {@inheritDoc}
//     */
//    @Override
    public void displayListOfTasks(CopyOnWriteArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            displayView.getMessageAboutEmptiness();
        }
        for (Task someTask : taskList) {
            String result = String.valueOf(someTask);
            displayView.getViewForList(result);
        }
    }

    //    /**
//     * {@inheritDoc}
//     */
//    @Override
    public void displayDetailAboutTask(CopyOnWriteArrayList<Task> taskList) {
        DateTimeFormatter formatter = ofPattern("yyyy-MM-dd HH:mm");
        if (taskList.size() == 0) {
            displayView.getMessageAboutEmptiness();
        }
        IntStream.range(0, taskList.size()).forEach(i -> {
            Task t = taskList.get(i);
            if (t.isRepeated()) {
                String resultRepTask = i + "\tYou have the repeated task with title : " + t.getTitle()
                        + "\nTask starts at " + formatter.format(t.getStartTime())
                        + "\nTask ends at " + formatter.format(t.getEndTime())
                        + "\nthe interval between start and end time is "
                        + t.getRepeatInterval();
                displayView.getViewForRepTask(resultRepTask);
            } else if (!t.isRepeated()) {
                String resultNorTask = i + "\tYou have the non-repeated task with title : " + t.getTitle()
                        + "\nTask starts at " + formatter.format(t.getTime());
                displayView.getViewForNorTask(resultNorTask);
            }
        });
    }
}
