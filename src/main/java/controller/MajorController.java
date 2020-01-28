package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import model.Tasks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.PrimaryView;

import java.io.*;
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
    private static final Logger log =
            LogManager.getLogger("info.log");

    /**
     * Instead of using Scanner, use BufferedReader for input.
     */
    private static BufferedReader bufReader;

    /**
     * Constant that holds name of the file for saving task list.
     */
    private final String nameOfFile = "TaskManager.txt";

    /**
     * Instance of list.
     */
    private ArrayTaskList listOfTasks;

    /**
     * Instance of view.
     */
    private PrimaryView view;

    /**
     * Empty file.
     */
    private File dataHoldingFile = new File(nameOfFile);

    /**
     * EVC constructor.
     */
    public MajorController() {
        listOfTasks = new ArrayTaskList();
        view = new PrimaryView();
        ConcurrencyNotification notificationThread =
                new ConcurrencyNotification(listOfTasks, this);
        notificationThread.start();
        log.info("The thread is running. Notification works");
    }

    /**
     * Private method that allow user to input info in console.
     *
     * @return - get the input number from console
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    private int getUserInput() throws IOException {
        log.info("User start works with console");
        bufReader = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(bufReader.readLine());
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
        log.info("The main menu was called");
//        view.displayInfo();
        int defaultNumber_1 = getUserInput();
        log.info("Console was called");
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
                createEmptyList();
//                view.displayAdditionalInfo();
                runSecondaryMenu();
                break;
            case 4:
                System.exit(0);
//                return;
            default:
                log.error("Wrong input by user!!!!" + new AssertionError());
        }
    }

    /**
     * Privatre method that run more duties in application.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    private void runSecondaryMenu() throws IOException {
        log.info("The additional menu was called");
        view.displayAdditionalInfo();
        int defaultNumber = getUserInput();
        log.info("Console was called");
        switch (defaultNumber) {
            case 1:
                addSomeTask();
                break;
            case 2:
                changeTask();
                break;
            case 3:
                removeSomeTask();
                break;
            case 4:
                showListOfTask();
                break;
            case 5:
                showTaskDetails();
                break;
            case 6:
                System.out.println("Set the time period for which you want "
                        + "to get the calendar: ");
                System.out.println("Like this: xxxx, - year\n "
                        + "xx, - month\n xx, - day\n"
                        + "xx, - hour\n xx - minute\r");
                LocalDateTime someTime = LocalDateTime.of(Integer
                                .parseInt(bufReader.readLine()),
                        Integer.parseInt(bufReader.readLine()),
                        Integer.parseInt(bufReader.readLine()),
                        Integer.parseInt(bufReader.readLine()),
                        Integer.parseInt(bufReader.readLine()));
                createCalendar(someTime);
                log.info("The calendar was created");
                break;
            case 7:
                saveWorkSession();
                break;
            case 8:
                return;
            default:
                log.error("Wrong input by user!!!!" + new AssertionError());
        }
    }

    /**
     * Implementing (override) addSomeTask() method
     * from interface.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    @Override
    public void addSomeTask() throws IOException {
        System.out.println("Enter the id of the task: \n");
        int id = Integer.parseInt(bufReader.readLine());
        System.out.println("Enter the title for task: ");
        String title = bufReader.readLine();
        System.out.println("What type of task it will be "
                + "repeative or not? (yes/no)\r");
        String wordAnswer = bufReader.readLine();
        if (wordAnswer.toLowerCase().equals("no")) {
            System.out.println("Enter the date "
                    + "(year = 2020, month = 12, "
                    + "day = 31(30)(28)(29), hour = 24,"
                    + " minute = 60)\n");
            LocalDateTime someTime = LocalDateTime.of(Integer
                            .parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()));
            Task someTask = new Task(id, title, someTime);
            addTask(someTask);
            log.info("The not-repeated task was created and added to the list");
        } else if (wordAnswer.toLowerCase().equals("yes")) {
            System.out.println("First please write start time for "
                    + "your task " + "like this "
                    + "(yyyy,mm,dd,hh,mm): \r");
            LocalDateTime start = LocalDateTime.of(Integer
                            .parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()));
            System.out.println("Secondly, type the end time for "
                    + "this task "
                    + "like start time: \r");
            LocalDateTime end = LocalDateTime.of(Integer
                            .parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()),
                    Integer.parseInt(bufReader.readLine()));
            System.out.println("At last enter the interval "
                    + "" + "for this task (integer value): \r");
            int taskInterval = Integer.parseInt(bufReader.readLine());
            Task someTask = new Task(id, title, start, end, taskInterval);
            addTask(someTask);
            log.info("The repeated task was created and added to the list");
        } else {
            log.error("Wrong input, make "
                    + "sure that you enter the word yes or no!!");
            addSomeTask();
        }
    }

    /**
     * Private method that add task to the list.
     *
     * @param task - take the task from addSomeTask()
     * @return - the size of the list in number
     */
    private int addTask(Task task) {
        listOfTasks.add(task);
        log.info("The task was successfully added");
        saveFileWithTasks();
        return count();
    }

    /**
     * Private method that count the size of the task list.
     *
     * @return - the size of the list in number
     */
    private int count() {
        return listOfTasks.size();
    }

    /**
     * Implementing (override) removeSomeTask() method
     * from interface.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    @Override
    public void removeSomeTask() throws IOException {
        view.displayListOfTasks(listOfTasks);
        System.out.println("Please write the id number of your task, "
                + "that you want to delete");
        int id = Integer.parseInt(bufReader.readLine());
        boolean result = removeTask(id);
        if (result) {
            log.info("The task was removed");
        } else {
            log.error("You try to delete task that doesn't exist");
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
        for (int i = 0, n = count(); i < n; i++) {
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
    public void changeTask() throws IOException {
        log.info("The process of changing task was started");
        int taskId;
        String taskName;
        int taskInterval;
        int taskStatus;
        view.changeOptions();
        int optionValue = getUserInput();
        log.info("The console was called");
        for (Task smth : listOfTasks) {
            switch (optionValue) {
                case 1:
                    System.out.println("Enter the id of the task: \r");
                    taskId = Integer.parseInt(bufReader.readLine());
                    smth.setId(taskId);
                    log.info("The id of the task was changed");
                    break;
                case 2:
                    System.out.println("First you need name "
                            + "it by title (only string type): \r");
                    taskName = bufReader.readLine();
                    smth.setTitle(taskName);
                    log.info("The title of the task was changed");
                    break;
                case 3:
                    System.out.println("You want to change repeative or no "
                            + "repeative task: (yes\no)\r");
                    String answer = bufReader.readLine();
                    if (answer.toLowerCase().equals("yes")) {
                        System.out.println("First please write start time for "
                                + "your task " + "like this "
                                + "(yyyy,mm,dd,hh,mm): \r");
                        LocalDateTime start = LocalDateTime.of(Integer
                                        .parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()));
                        System.out.println("Secondly, type the end time for "
                                + "this task "
                                + "like start time: \r");
                        LocalDateTime end = LocalDateTime.of(Integer
                                        .parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()));
                        System.out.println("At last enter the interval "
                                + "" + "for this task (integer value): \r");
                        taskInterval = Integer.parseInt(bufReader.readLine());
                        smth.setTime(start, end, taskInterval);
                        log.info("The repeated time and interval were changed");
                    } else if (answer.toLowerCase().equals("no")) {
                        System.out.println("Please write the time for "
                                + "your task"
                                + "like this (yyyy,mm,dd,hh,mm): \r");
                        LocalDateTime time = LocalDateTime.of(Integer
                                        .parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()),
                                Integer.parseInt(bufReader.readLine()));
                        smth.setTime(time);
                        log.info("The time of the task was changed");
                    } else {
                        log.error("Wrong input, make sure "
                                + "that you write yes or no!!!");
                        changeTask();
                    }
                case 4:
                    System.out.println("Set the status of your task\n "
                            + "1 - active; 0 - notactive: \r");
                    taskStatus = Integer.parseInt(bufReader.readLine());
                    if (taskStatus == 1) {
                        smth.setActive(true);
                        log.info("The task is now active");
                    } else if (taskStatus == 0) {
                        smth.setActive(false);
                        log.info("The task turn off");
                    } else {
                        log.error("Wrong input, make "
                                + "sure that you enter number 1 or 0!!");
                        changeTask();
                    }
                default:
                    log.error("Wrong input by user!!!!" + new AssertionError());
            }
        }
    }

    /**
     * Implementing (override) saveFileWithTasks() method
     * from interface.
     */
    @Override
    public void saveFileWithTasks() {
        try {
            TaskIO.writeText(listOfTasks, dataHoldingFile);
        } catch (IOException e) {
            log.error("Error with writing process to the file" + e);
        }
    }

    /**
     * Implementing (override) readFileWithTasks() method
     * from interface.
     */
    @Override
    public void readFileWithTasks() {
        try {
            TaskIO.readText(listOfTasks, dataHoldingFile);
        } catch (IOException e) {
            log.error("Error with reading process from the file" + e);
        }
    }

    /**
     * Implementing (override) createCalendar() method
     * from interface.
     *
     * @param limitDate - the period of time for which the
     *                  calendar will be created
     */
    @Override
    public void createCalendar(LocalDateTime limitDate) {
        SortedMap<LocalDateTime, Set<Task>> defaultCalendar =
                Tasks.calendar(listOfTasks, LocalDateTime.now(), limitDate);
        view.displayCreatedCalendar(defaultCalendar);
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
                System.out.println(t.getTitle() + "\t"
                        + t.getStartTime() + "\t" + t.getEndTime() + "\t"
                        + t.getRepeatInterval());
            } else if (!t.isActive()) {
                System.out.println(t.getTitle() + "\t" + t.getTime());
            } else {
                System.out.println(t.getTitle() + "\t" + t.getStartTime() + "\t"
                        + t.getEndTime());
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

    /**
     * The name of the file that stores the session of application.
     */
    private String nameFile = "savepoint.txt";

    /**
     * The path where the file is stored.
     */
    private String pathForFile = "save" + File.separatorChar + "point"
            + File.separatorChar + nameFile;
    /**
     * The name of directory to store files.
     */
    private String dirPath = "save";

    /**
     * Implementing (override) configApplication() method
     * from interface.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing text file
     */
    @Override
    public void saveWorkSession() throws IOException {
        createFolder(dirPath);
        writeToFile();
        log.info("The session was saved");
    }

    /**
     * Private method that creates empty file and write the content to it.
     *
     * @throws IOException - failure during writing to the file
     */
    private void writeToFile() throws IOException {
        try {
            File fileTxt = new File(pathForFile);
            log.info("The file starts creating.........");
            if (fileTxt.createNewFile()) {
                log.info("Your file is created here '"
                        + pathForFile + "'");
            } else {
                log.info("File've already existed");
                PrintWriter writer = new PrintWriter(pathForFile);
                writer.print("");
                writer.close();
            }
            TaskIO.writeText(listOfTasks, fileTxt);
            log.info("The process of "
                    + "writing was successfully completed");
        } finally {
            log.info("The process "
                    + "of saved session was successfully completed");
        }
    }

    /**
     * Private static method that create empty folder by specified way.
     *
     * @param dirPath - path to the directory
     */
    private static void createFolder(String dirPath) {
        File fileDirectory = new File(dirPath);
        if (!fileDirectory.exists()) {
            log.info("The directory starts creating........");
            if (fileDirectory.mkdir()) {
                log.info("The directory '"
                        + dirPath + "' is created");
            } else {
                log.error("Don't enough "
                        + "permission to create directory");
            }
        }
        log.info("\nThe process of creating folder was finished");
    }

    /**
     * Implementing (override) createEmptyList() method
     * from interface.
     */
    @Override
    public void createEmptyList() {
        ArrayTaskList newTaskList = new ArrayTaskList();
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
        File fileTxt = new File(pathForFile);
        TaskIO.readText(listOfTasks, fileTxt);
    }
}
