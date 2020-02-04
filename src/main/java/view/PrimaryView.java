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
        for (SortedMap.Entry<LocalDateTime, Set<Task>> content : values.entrySet()) {
            for (Task task : content.getValue()) {
                System.out.println(task.getTitle());
            }
            System.out.println(content.getKey());
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
     * Implementation (override) displatDetailAboutTask() method from
     * interface.
     */
    @Override
    public void displayDetailAboutTask(ArrayTaskList taskList) {
        System.out.println("--------ALL YOUR TASKS--------");
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.getTask(i);
            if (t.isRepeated()) {
                System.out.println("Id : " + t.getId() + "\tTitle : " + t.getTitle()
                        + "\nTask starts at " + t.getStartTime()
                        + "\nTask ends at " + t.getEndTime()
                        + "\nthe interval between start and end time is "
                        + t.getRepeatInterval());
            } else {
                System.out.println("Id : " + t.getId() + "\tTitle : " + t.getTitle()
                        + "\nTask starts at " + t.getStartTime()
                        + "\nTask ends at " + t.getEndTime());
            }
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
        System.out.println("Enter the date "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        return inputDateTime();
    }

    public LocalDateTime addStartTimeForTask() {
        System.out.println("First please write start time for "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        return inputDateTime();
    }

    public LocalDateTime addEndTimeForTask() {
        System.out.println("Secondly, type the end time for "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        return inputDateTime();
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
        System.out.println("Enter the date "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        return inputDateTime();
    }

    public LocalDateTime changeStartTimeOfTask() {
        System.out.println("First please write start time for "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        return inputDateTime();
    }

    public LocalDateTime changeEndTimeOfTask() {
        System.out.println("Secondly, type the end time for "
                + "this task "
                + "like start time: \r");
        return inputDateTime();
    }

    private LocalDateTime inputDateTime() {
        String date = "";
        LocalDateTime time = null;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = bufReader.readLine();
        } catch (IOException exp) {
            logger.error("Error in input date to the console", exp);
        }
        try {
            time = LocalDateTime.parse(date, timeFormatter);
        } catch (DateTimeParseException otherExp) {
            System.out.println("Please input only number, in other case it can't be "
                    + "transformed to LocalDateTime format");
            logger.error("Error in formatting input date to LocalDateTime", otherExp);
        }
        return time;
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

    public LocalDateTime addTimeLimit_1() {
        System.out.println("Enter the date from which your calendar starts "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        return inputDateTime();
    }

    public LocalDateTime addTimeLimit_2() {
        System.out.println("Set the end time period for which you want "
                + "to get the calendar.\n"
                + "Enter the date "
                + "or just copy this (2020-01-29 19:46) without braces:"
                + " and change numbers \r");
        return inputDateTime();
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
}
