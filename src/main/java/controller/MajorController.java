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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        logger.info("The thread is running. Notification works");
    }

    /**
     * Private method that allow user to input info in console.
     *
     * @return - get the input number from console
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    private int getUserInput() throws IOException {
        logger.info("User start works with console");
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
        logger.info("The main menu was called");
//        view.displayInfo();
        int defaultNumber_1 = getUserInput();
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
                new ArrayTaskList();
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
        int defaultNumber = getUserInput();
        logger.info("Console was called");
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
                logger.info("The calendar was created");
                break;
            case 7:
                saveWorkSession();
                break;
            case 8:
                return;
            default:
                System.out.println("Wrong input by user!!!!" + new AssertionError());
        }
    }

    /**
     * Implementing (override) addSomeTask() method
     * from interface.
     */
    @Override
    public void addSomeTask() {
        int id = 0;
        System.out.println("Enter the id of the task: \r");
        try {
            id = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
        String title = null;
        System.out.println("Enter the title for task: \r");
        try {
            title = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in input word to the console", exp);
        }
        String wordAnswer = null;
        System.out.println("What type of task it will be "
                + "repeative or not? (yes/no) \r");
        try {
            wordAnswer = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in input word to the console", exp);
        }
        if (wordAnswer.toLowerCase().equals("no")) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime someTime = null;
            System.out.println("Enter the date "
                    + "(year = 2020, month = 12, "
                    + "day = 31(30)(28)(29), hour = 24,"
                    + " minute = 60): \r");
            try {
                someTime = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
            } catch (IOException exp) {
                logger.error("Error in input date to the console", exp);
            } catch (DateTimeParseException otherExp) {
                System.out.println("Please input only number, in other case it can't be "
                        + "transformed to LocalDateTime format");
                logger.error("Error in formatting input date to LocalDateTime", otherExp);
            }
            if (someTime == null) {
                System.out.println("Empty LocalDateTime field");
                return;
            }
            Task someTask = new Task(id, title, someTime);
            addTask(someTask);
            logger.info("The not-repeated task was created and added to the list");
        } else if (wordAnswer.toLowerCase().equals("yes")) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = null;
            System.out.println("First please write start time for "
                    + "your task " + "like this "
                    + "(yyyy,mm,dd,hh,mm): \r");
            try {
                start = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
            } catch (IOException exp) {
                logger.error("Error in input date to the console", exp);
            } catch (DateTimeParseException otherExp) {
                System.out.println("Please input only number, in other case it can't be "
                        + "transformed to LocalDateTime format");
                logger.error("Error in formatting input date to LocalDateTime", otherExp);
            }
            if ((start == null)) {
                System.out.println("Empty LocalDateTime field");
                return;
            }
            LocalDateTime end = null;
            System.out.println("Secondly, type the end time for "
                    + "this task "
                    + "like start time: \r");
            try {
                end = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
            } catch (IOException exp) {
                logger.error("Error in input date to the console", exp);
            } catch (DateTimeParseException otherExp) {
                System.out.println("Please input only number, in other case it can't be "
                        + "transformed to LocalDateTime format");
                logger.error("Error in formatting input date to LocalDateTime", otherExp);
            }
            if ((end == null)) {
                System.out.println("Empty LocalDateTime field");
                return;
            }
            int taskInterval = 0;
            System.out.println("At last enter the interval "
                    + "" + "for this task (integer value): \r");
            try {
                taskInterval = Integer.parseInt(bufReader.readLine());
            } catch (IOException exp) {
                logger.error("Error in input number to the console", exp);
            }
            Task someTask = new Task(id, title, start, end, taskInterval);
            addTask(someTask);
            logger.info("The repeated task was created and added to the list");
        } else {
            logger.error("Wrong input, make "
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
        logger.info("The task was successfully added");
//        saveFileWithTasks();
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
     */
    @Override
    public void removeSomeTask() {
        view.displayListOfTasks(listOfTasks);
        int id = 0;
        System.out.println("Please write the id number of your task, "
                + "that you want to delete");
        try {
            id = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
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
        logger.info("The process of changing task was started");
        view.changeOptions();
        int optionValue = getUserInput();
        logger.info("The console was called");
        for (Task smth : listOfTasks) {
            switch (optionValue) {
                case 1:
                    int taskId = 0;
                    System.out.println("Enter the id of the task: \r");
                    try {
                        taskId = Integer.parseInt(bufReader.readLine());
                    } catch (IOException exp) {
                        logger.error("Error in input number to the console", exp);
                    }
                    smth.setId(taskId);
                    logger.info("The id of the task was changed");
                    break;
                case 2:
                    String taskName = null;
                    System.out.println("First you need name "
                            + "it by title (only string type): \r");
                    try {
                        taskName = bufReader.readLine();
                    } catch (IOException exp) {
                        logger.error("Error in input word to the console", exp);
                    }
                    smth.setTitle(taskName);
                    logger.info("The title of the task was changed");
                    break;
                case 3:
                    String answer = null;
                    System.out.println("You want to change repeative or no "
                            + "repeative task: (yes\no)\r");
                    try {
                        answer = bufReader.readLine();
                    } catch (IOException exp) {
                        logger.error("Error in input word to the console", exp);
                    }
                    if (answer.toLowerCase().equals("yes")) {
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime start = null;
                        System.out.println("First please write start time for "
                                + "your task " + "like this "
                                + "(yyyy,mm,dd,hh,mm): \r");
                        try {
                            start = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
                        } catch (IOException exp) {
                            logger.error("Error in input date to the console", exp);
                        } catch (DateTimeParseException otherExp) {
                            System.out.println("Please input only number, in other case it can't be "
                                    + "transformed to LocalDateTime format");
                            logger.error("Error in formatting input date to LocalDateTime", otherExp);
                        }
                        LocalDateTime end = null;
                        System.out.println("Secondly, type the end time for "
                                + "this task "
                                + "like start time: \r");
                        try {
                            end = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
                        } catch (IOException exp) {
                            logger.error("Error in input date to the console", exp);
                        } catch (DateTimeParseException otherExp) {
                            System.out.println("Please input only number, in other case it can't be "
                                    + "transformed to LocalDateTime format");
                            logger.error("Error in formatting input date to LocalDateTime", otherExp);
                        }
                        int taskInterval = 0;
                        System.out.println("Enter the interval for your task\r");
                        try {
                            taskInterval = Integer.parseInt(bufReader.readLine());
                        } catch (IOException exp) {
                            logger.error("Error in input number to the console", exp);
                        }
                        if ((start == null) || (end == null)
                                || (taskInterval == 0)) {
                            System.out.println("Wrong input make sure try again");
                            return;
                        } else {
                            smth.setTime(start, end, taskInterval);
                            logger.info("The repeated time and interval were changed");
                        }
                    } else if (answer.toLowerCase().equals("no")) {
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime time = null;
                        System.out.println("Please write the time for "
                                + "your task"
                                + "like this (yyyy,mm,dd,hh,mm): \r");
                        try {
                            time = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
                        } catch (IOException exp) {
                            logger.error("Error in input date to the console", exp);
                        } catch (DateTimeParseException otherExp) {
                            System.out.println("Please input only number, in other case it can't be "
                                    + "transformed to LocalDateTime format");
                            logger.error("Error in formatting input date to LocalDateTime", otherExp);
                        }
                        smth.setTime(time);
                        logger.info("The time of the task was changed");
                    } else {
                        logger.error("Wrong input, make sure "
                                + "that you write yes or no!!!");
                        changeTask();
                    }
                case 4:
                    int taskStatus = 0;
                    System.out.println("Set the status of your task\n "
                            + "1 - active; 0 - notactive: \r");
                    try {
                        taskStatus = Integer.parseInt(bufReader.readLine());
                    } catch (IOException exp) {
                        logger.error("Error in input number to the console", exp);
                    }
                    if (taskStatus == 1) {
                        smth.setActive(true);
                        logger.info("The task is now active");
                    } else if (taskStatus == 0) {
                        smth.setActive(false);
                        logger.info("The task turn off");
                    } else {
                        logger.error("Wrong input, make "
                                + "sure that you enter number 1 or 0!!");
                        changeTask();
                        return;
                    }
                default:
                    System.out.println("Wrong input by user!!!!" + new AssertionError());
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
            logger.error("Error with writing process to the file" + e);
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
            logger.error("Error with reading process from the file" + e);
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
                System.out.printf("Id : %d\n" + t.getId());
                System.out.printf("Title : %s\n" + t.getTitle());
                System.out.printf("Start Time : %s\n" + t.getStartTime());
                System.out.printf("End Time : %s\n" + t.getEndTime());
                System.out.printf("Repetition of period : %s\n" + t.getRepeatInterval());
            } else if (!t.isActive()) {
                System.out.printf("Id : %d\n" + t.getId());
                System.out.printf("Title : %s\n" + t.getTitle());
                System.out.printf("Task Time : %s\n" + t.getTime());
            } else {
                System.out.printf("Id : %d\n" + t.getId());
                System.out.printf("Title : %s\n" + t.getTitle());
                System.out.printf("Start Time : %s\n" + t.getStartTime());
                System.out.printf("End Time : %s\n" + t.getEndTime());
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
        logger.info("The session was saved");
    }

    /**
     * Private method that creates empty file and write the content to it.
     *
     * @throws IOException - failure during writing to the file
     */
    private void writeToFile() throws IOException {
        try {
            File fileTxt = new File(pathForFile);
            logger.info("The file starts creating.........");
            if (fileTxt.createNewFile()) {
                logger.info("Your file is created here '"
                        + pathForFile + "'");
            } else {
                logger.info("File've already existed");
                PrintWriter writer = new PrintWriter(pathForFile);
                writer.print("");
                writer.close();
            }
            TaskIO.writeText(listOfTasks, fileTxt);
            logger.info("The process of "
                    + "writing was successfully completed");
        } finally {
            logger.info("The process "
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
            logger.info("The directory starts creating........");
            if (fileDirectory.mkdir()) {
                logger.info("The directory '"
                        + dirPath + "' is created");
            } else {
                logger.error("Don't enough "
                        + "permission to create directory");
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
        File fileTxt = new File(pathForFile);
        TaskIO.readText(listOfTasks, fileTxt);
    }
}
