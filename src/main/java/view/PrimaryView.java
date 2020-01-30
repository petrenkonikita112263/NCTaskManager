package view;

import model.ArrayTaskList;
import model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * View class that implements interface of Core class.
 *
 * @author Nikita
 */
public class PrimaryView implements CoreViewable {

    /**
     * Adding logger to the class.
     */
    private static final Logger logger = LogManager.getLogger(PrimaryView.class);

    /**
     * Instead of using Scanner, use BufferedReader for input.
     */
    private static BufferedReader bufReader;

    /**
     * Implementation (override) displayInfo() method from
     * interface.
     */
    @Override
    public void displayInfo() {
        System.out.println("The application Task Manager is running. "
                + "You have these options: \n");
        System.out.println("\t 1 - To continue work with your app"
                + " from previous savepoint");
        System.out.println("\t 2 - To load list of task");
        System.out.println("\t 3 - To create empty task list");
        System.out.println("\t 4 - To terminate your work with Task Manager");
    }

    /**
     * Implementation (override) displayAdditionalInfo() method from
     * interface.
     */
    @Override
    public void displayAdditionalInfo() {
        System.out.println("\t1 - To add task to the list");
        System.out.println("\t2 - To change the task in the list");
        System.out.println("\t3 - To delete task from the list");
        System.out.println("\t4 - Display all tasks from the list");
        System.out.println("\t5 - Display all information about every tasks in the list");
        System.out.println("\t6 - Create calendar");
        System.out.println("\t7 - To write your task list to the file");
        System.out.println("\t8 - Terminate the application");
    }

    /**
     * Implementation (override) displayCreatedCalendar() method from
     * interface.
     */
    @Override
    public void displayCreatedCalendar(SortedMap<LocalDateTime,
            Set<Task>> values) {
        for (Map.Entry<LocalDateTime, Set<Task>> content : values.entrySet()) {
            for (Task task : content.getValue()) {
                System.out.println(content.getKey());
                System.out.println(task.getTitle());
            }
        }
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
     * Implementation (override) changeOptions() method from
     * interface.
     */
    @Override
    public void changeOptions() {
        System.out.println("\t1 - Change id (number) of your task");
        System.out.println("\t2 - Change title of your task");
        System.out.println("\t3 - Change time and interval of your task");
        System.out.println("\t4- Set your task active or diactive it");
    }

    /**
     * Public method that allow user to input info in console.
     *
     * @return - get the input number from console
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    public int getUserInput() throws IOException {
        logger.info("User start works with console");
        bufReader = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(bufReader.readLine());
    }

    public int addTaskIndex() {
        int id = 0;
        System.out.println("Enter the id of the task: \r");
        try {
            id = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
        return id;
    }

    public String addTaskTitle() {
        String title = null;
        System.out.println("Enter the title for task: \r");
        try {
            title = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in input word to the console", exp);
        }
        return title;
    }

    public String addTypeOfTask() {
        String wordAnswer = null;
        System.out.println("What type of task it will be "
                + "repeative or not? (yes/no) \r");
        try {
            wordAnswer = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in input word to the console", exp);
        }
        return wordAnswer;
    }

    public LocalDateTime addTimeForTask() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime someTime = null;
        System.out.println("Enter the date "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
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
            return addTimeForTask();
        }
        return someTime;
    }

    public LocalDateTime addStartTimeForTask() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = null;
        System.out.println("First please write start time for "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
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
            return addStartTimeForTask();
        }
        return start;
    }

    public LocalDateTime addEndTimeForTask() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime end = null;
        System.out.println("Secondly, type the end time for "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
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
            return addEndTimeForTask();
        }
        return end;
    }

    public int addInterval() {
        int taskInterval = 0;
        System.out.println("At last enter the interval "
                + "for this task (integer value): \r");
        try {
            taskInterval = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
        return taskInterval;
    }

    public int removeSomeTask() {
        int id = 0;
        System.out.println("Please write the id number of your task, "
                + "that you want to delete");
        try {
            id = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
        return id;
    }

    public int changeIdOfTask() {
        int taskId = 0;
        System.out.println("Enter the id of the task: \r");
        try {
            taskId = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
        return taskId;
    }

    public String changeTitleOfTask() {
        String taskName = null;
        System.out.println("First you need name "
                + "it by title (only string type): \r");
        try {
            taskName = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in input word to the console", exp);
        }
        return taskName;
    }

    public String changeTypeOfTask() {
        String answer = null;
        System.out.println("You want to change repeative or no "
                + "repeative task: (yes\no)\r");
        try {
            answer = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in input word to the console", exp);
        }
        return answer;
    }

    public LocalDateTime changeTimeOfTask() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = null;
        System.out.println("Enter the date "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        try {
            time = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
        } catch (IOException exp) {
            logger.error("Error in input date to the console", exp);
        } catch (DateTimeParseException otherExp) {
            System.out.println("Please input only number, in other case it can't be "
                    + "transformed to LocalDateTime format");
            logger.error("Error in formatting input date to LocalDateTime", otherExp);
        }
        return time;
    }

    public LocalDateTime changeStartTimeOfTask() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = null;
        System.out.println("First please write start time for "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        try {
            start = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
        } catch (IOException exp) {
            logger.error("Error in input date to the console", exp);
        } catch (DateTimeParseException otherExp) {
            System.out.println("Please input only number, in other case it can't be "
                    + "transformed to LocalDateTime format");
            logger.error("Error in formatting input date to LocalDateTime", otherExp);
        }
        return start;
    }

    public LocalDateTime changeEndTimeOfTask() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
        return end;
    }

    public int changeIntervalOfTask() {
        int taskInterval = 0;
        System.out.println("Enter the interval for your task\r");
        try {
            taskInterval = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
        return taskInterval;
    }

    public int changeStatusOfTask() {
        int taskStatus = 0;
        System.out.println("Set the status of your task\n "
                + "1 - active; 0 - notactive: \r");
        try {
            taskStatus = Integer.parseInt(bufReader.readLine());
        } catch (IOException exp) {
            logger.error("Error in input number to the console", exp);
        }
        return taskStatus;
    }

    public LocalDateTime addTimeLimit() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime limitdate = null;
        System.out.println("Set the time period for which you want "
                + "to get the calendar.\n"
                + "Enter the date "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        try {
            limitdate = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
        } catch (IOException exp) {
            logger.error("Error in input date to the console", exp);
        } catch (DateTimeParseException otherExp) {
            System.out.println("Please input only number, in other case it can't be "
                    + "transformed to LocalDateTime format");
            logger.error("Error in formatting input date to LocalDateTime", otherExp);
        }
        return limitdate;
    }

    public int getAction() {
        System.out.println("TYpe what do you want to perform: ");
        System.out.println("\t 1 - To saved task and exit\n\t 2- To load list of task"
                + "\n\t 3 - To return to main menu of application");
        int inputNumber = 0;
        try {
            inputNumber = Integer.parseInt(bufReader.readLine());
        } catch (IOException mainExp) {
            logger.error("Error with typing number or wrong format for number", mainExp);
        }
        return inputNumber;
    }

    public String getFileName() {
        String nameFile = null;
        System.out.println("Print the name of the file:\r");
        try {
            nameFile = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in typing word to the console", exp);
        }
        return nameFile;
    }

    public void changeTask() throws IOException {
//        logger.info("The process of changing task was started");
//        view.changeOptions();
//        int optionValue = getUserInput();
//        logger.info("The console was called");
//        for (Task smth : listOfTasks) {
//            switch (optionValue) {
//                case 1:
//                    int taskId = 0;
//                    System.out.println("Enter the id of the task: \r");
//                    try {
//                        taskId = Integer.parseInt(bufReader.readLine());
//                    } catch (IOException exp) {
//                        logger.error("Error in input number to the console", exp);
//                    }
//                    smth.setId(taskId);
//                    logger.info("The id of the task was changed");
//                    break;
//                case 2:
//                    String taskName = null;
//                    System.out.println("First you need name "
//                            + "it by title (only string type): \r");
//                    try {
//                        taskName = bufReader.readLine();
//                    } catch (IOException exp) {
//                        logger.error("Error in input word to the console", exp);
//                    }
//                    smth.setTitle(taskName);
//                    logger.info("The title of the task was changed");
//                    break;
//                case 3:
//                    String answer = null;
//                    System.out.println("You want to change repeative or no "
//                            + "repeative task: (yes\no)\r");
//                    try {
//                        answer = bufReader.readLine();
//                    } catch (IOException exp) {
//                        logger.error("Error in input word to the console", exp);
//                    }
//                    if (answer.toLowerCase().equals("yes")) {
//                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                        LocalDateTime start = null;
//                        System.out.println("First please write start time for "
//                                + "your task " + "like this "
//                                + "(yyyy,mm,dd,hh,mm): \r");
//                        try {
//                            start = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
//                        } catch (IOException exp) {
//                            logger.error("Error in input date to the console", exp);
//                        } catch (DateTimeParseException otherExp) {
//                            System.out.println("Please input only number, in other case it can't be "
//                                    + "transformed to LocalDateTime format");
//                            logger.error("Error in formatting input date to LocalDateTime", otherExp);
//                        }
//                        LocalDateTime end = null;
//                        System.out.println("Secondly, type the end time for "
//                                + "this task "
//                                + "like start time: \r");
//                        try {
//                            end = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
//                        } catch (IOException exp) {
//                            logger.error("Error in input date to the console", exp);
//                        } catch (DateTimeParseException otherExp) {
//                            System.out.println("Please input only number, in other case it can't be "
//                                    + "transformed to LocalDateTime format");
//                            logger.error("Error in formatting input date to LocalDateTime", otherExp);
//                        }
//                        int taskInterval = 0;
//                        System.out.println("Enter the interval for your task\r");
//                        try {
//                            taskInterval = Integer.parseInt(bufReader.readLine());
//                        } catch (IOException exp) {
//                            logger.error("Error in input number to the console", exp);
//                        }
//                        if ((start == null) || (end == null)
//                                || (taskInterval == 0)) {
//                            System.out.println("Wrong input make sure try again");
//                            return;
//                        } else {
//                            smth.setTime(start, end, taskInterval);
//                            logger.info("The repeated time and interval were changed");
//                        }
//                    } else if (answer.toLowerCase().equals("no")) {
//                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                        LocalDateTime time = null;
//                        System.out.println("Please write the time for "
//                                + "your task"
//                                + "like this (yyyy,mm,dd,hh,mm): \r");
//                        try {
//                            time = LocalDateTime.parse(bufReader.readLine(), timeFormatter);
//                        } catch (IOException exp) {
//                            logger.error("Error in input date to the console", exp);
//                        } catch (DateTimeParseException otherExp) {
//                            System.out.println("Please input only number, in other case it can't be "
//                                    + "transformed to LocalDateTime format");
//                            logger.error("Error in formatting input date to LocalDateTime", otherExp);
//                        }
//                        smth.setTime(time);
//                        logger.info("The time of the task was changed");
//                    } else {
//                        logger.error("Wrong input, make sure "
//                                + "that you write yes or no!!!");
//                        changeTask();
//                    }
//                case 4:
//                    int taskStatus = 0;
//                    System.out.println("Set the status of your task\n "
//                            + "1 - active; 0 - notactive: \r");
//                    try {
//                        taskStatus = Integer.parseInt(bufReader.readLine());
//                    } catch (IOException exp) {
//                        logger.error("Error in input number to the console", exp);
//                    }
//                    if (taskStatus == 1) {
//                        smth.setActive(true);
//                        logger.info("The task is now active");
//                    } else if (taskStatus == 0) {
//                        smth.setActive(false);
//                        logger.info("The task turn off");
//                    } else {
//                        logger.error("Wrong input, make "
//                                + "sure that you enter number 1 or 0!!");
//                        changeTask();
//                        return;
//                    }
//                default:
//                    System.out.println("Wrong input by user!!!!" + new AssertionError());
//            }
//        }
    }
}
