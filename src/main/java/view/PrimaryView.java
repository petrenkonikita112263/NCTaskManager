package view;

import model.Task;
import org.apache.log4j.Logger;

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
    private static final Logger logger = Logger.getLogger(PrimaryView.class);

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
     * Implementation (override) changeOptions() method from
     * interface.
     */
    @Override
    public void changeOptions() {
//        System.out.println("\t1 - Change id (number) of your task");
        System.out.println("\t1 - Change title of your task");
        System.out.println("\t2 - Change time and interval of your task");
        System.out.println("\t3- Set your task active or diactive it");
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

//    /**
//     * Method that allow user to input id for task.
//     *
//     * @return - id of it
//     */
//    public int addTaskIndex() {
//        int id = 0;
//        System.out.println("Enter the id of the task: \r");
//        try {
//            id = Integer.parseInt(bufReader.readLine());
//            checkIntValue(id);
//        } catch (IOException exp_1) {
//            logger.error("Error in input number to the console", exp_1);
//        } catch (NumberFormatException exp_2) {
//            logger.error("Wrong type of id for task", exp_2);
//        }
//        return id;
//    }

    /**
     * Private additional method that validate int number
     * from user input.
     *
     * @param number - any integer number in range of Integer
     */
    private void checkIntValue(int number) {
        if ((number > 60) || (number < 0)) {
            logger.error("Task can't exist with this value of Integer");
            addInterval();
        }
    }

    /**
     * Method that allow user to input title for task.
     *
     * @return - title
     */
    public String addTaskTitle() {
        String title = null;
        System.out.println("Enter the title for task: \r");
        try {
            title = bufReader.readLine();
            checkStringValue(title);
        } catch (IOException exp_1) {
            logger.error("Error in input word to the console", exp_1);
            addTaskTitle();
        } catch (IllegalArgumentException exp_2) {
            logger.error("Task can't exist without title", exp_2);
            addTaskTitle();
        }
        return title;
    }

    /**
     * Private additional method that validate string value
     * from user input.
     *
     * @param word - one word
     */
    private void checkStringValue(String word) {
        if (word == null) {
            logger.error("Task can't exist with this String value");
            selectTheTypeForTask();
        }
    }

//    /**
//     * Method that allow user to choice which type of task
//     * to add to the list.
//     *
//     * @return - get user answer for further action
//     */
//    public String addTypeOfTask() {
//        String wordAnswer = null;
//        System.out.println("What type of task it will be "
//                + "repeative or not? (yes/no) \r");
//        try {
//            wordAnswer = bufReader.readLine();
//        } catch (IOException exp) {
//            logger.error("Error in input word to the console", exp);
//        }
//        return wordAnswer;
//    }

    /**
     * Private additional method that validate LocalDateTime
     * from user input.
     *
     * @param time - time value for task
     */
    private void checkTime(LocalDateTime time) {
        if ((time.isBefore(LocalDateTime.now())) || (time == null)) {
            logger.error("Task can't exist with this time");
            inputDateTime();
        }
    }

    /**
     * Method that allow user to input interval for task.
     *
     * @return - input interval
     */
    public int addInterval() {
        int taskInterval = 0;
        System.out.println("At last enter the interval in minutes "
                + "for this task (integer value): \r");
        try {
            taskInterval = Integer.parseInt(bufReader.readLine());
            checkIntValue(taskInterval);
        } catch (IOException exp_1) {
            logger.error("Error in input number to the console", exp_1);
            addInterval();
        } catch (NumberFormatException exp_2) {
            logger.error("Wrong type of interval for task", exp_2);
            addInterval();
        }
        return taskInterval;
    }

    /**
     * Method that ask user to set id of task that will be deleted.
     *
     * @return - the id of task for which remove will be called
     */
    public int removeSomeTask() {
        int id = 0;
        System.out.println("Please write the id number of your task, "
                + "that you want to delete");
        try {
            id = Integer.parseInt(bufReader.readLine());
            checkIntValue(id);
        } catch (IOException exp_1) {
            logger.error("Error in input number to the console", exp_1);
        } catch (NumberFormatException exp_2) {
            logger.error("Wrong type of id for task", exp_2);
        }
        return id;
    }

//    /**
//     * Method that ask user to change id of the task.
//     *
//     * @return - new id
//     */
//    public int changeIdOfTask() {
//        int taskId = 0;
//        System.out.println("Enter the id of the task: \r");
//        try {
//            taskId = Integer.parseInt(bufReader.readLine());
//            checkIntValue(taskId);
//        } catch (IOException exp_1) {
//            logger.error("Error in input number to the console", exp_1);
//        } catch (NumberFormatException exp_2) {
//            logger.error("Wrong type of id for task", exp_2);
//        }
//        return taskId;
//    }

    /**
     * Method that ask user to change title of the task.
     *
     * @return - new title
     */
    public String changeTitleOfTask() {
        String taskName = null;
        System.out.println("First you need name "
                + "it by title (only string type): \r");
        try {
            taskName = bufReader.readLine();
            checkStringValue(taskName);
        } catch (IOException exp_1) {
            logger.error("Error in input word to the console", exp_1);
        } catch (IllegalArgumentException exp_2) {
            logger.error("Task can't exist without title", exp_2);
        }
        return taskName;
    }

    /**
     * Method that ask user to change type of the task.
     *
     * @return - new type repeated or not
     */
    public String selectTheTypeForTask() {
        String answer = null;
        System.out.println("Do you want to add(change) repetitive task (start, end, interval) = 'yes'"
                + " or you want to add(change) non-repetitive task(only time) = 'no': \r");
        try {
            answer = bufReader.readLine();
            checkStringValue(answer);
        } catch (IOException exp) {
            logger.error("Error in input word to the console", exp);
        } catch (IllegalArgumentException exp_2) {
            logger.error("Not String type", exp_2);
        }
        return answer;
    }

    /**
     * Method that allow to input date|change date.
     *
     * @return - LocalDateTime that user inputs
     */
    public LocalDateTime inputDateTime() {
        System.out.println("Please enter the time for task like in braces "
                + "or just copy this (2020-02-18 20:14) without braces:"
                + " and change numbers \r");
        String date = "";
        LocalDateTime time = null;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = bufReader.readLine();
            time = LocalDateTime.parse(date, timeFormatter);
            checkTime(time);
        } catch (IOException exp) {
            logger.error("Error in input date to the console", exp);
            inputDateTime();
        } catch (DateTimeParseException otherExp) {
            logger.error("Error in formatting input date to LocalDateTime", otherExp);
            inputDateTime();
        }
        return time;
    }

    /**
     * Method that ask user to change interval of the task.
     *
     * @return - new interval
     */
    public int changeIntervalOfTask() {
        int taskInterval = 0;
        System.out.println("Enter the new interval in minutes for your task\r");
        try {
            taskInterval = Integer.parseInt(bufReader.readLine());
            checkIntValue(taskInterval);
        } catch (IOException exp_1) {
            logger.error("Error in input number to the console", exp_1);
            changeIntervalOfTask();
        } catch (NumberFormatException exp_2) {
            logger.error("Wrong type of interval for task", exp_2);
            changeIntervalOfTask();
        }
        return taskInterval;
    }

    /**
     * Method that ask user to change status of the task.
     *
     * @return - 0 - disable task, 1 - enable it
     */
    public int changeStatusOfTask() {
        int taskStatus = 0;
        System.out.println("Set the status of your task\n "
                + "1 - active; 0 - notactive: \r");
        try {
            taskStatus = Integer.parseInt(bufReader.readLine());
            checkIntValue(taskStatus);
        } catch (IOException exp_1) {
            logger.error("Error in input number to the console", exp_1);
        } catch (NumberFormatException exp_2) {
            logger.error("This integer number can't exist", exp_2);
        }
        return taskStatus;
    }

    /**
     * Method that asks user to start date from which calendar starts.
     *
     * @return - get the start date
     */
    public LocalDateTime addTimeLimit_1() {
        System.out.println("Enter the date from which your calendar starts "
                + "or just copy this (2020-02-18 20:14) without braces:"
                + " and change numbers \r");
        String date = "";
        LocalDateTime time = null;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = bufReader.readLine();
            time = LocalDateTime.parse(date, timeFormatter);
            checkCalendarDate_1(time);
        } catch (IOException exp) {
            logger.error("Error in input date to the console", exp);
            addTimeLimit_1();
        } catch (DateTimeParseException otherExp) {
            logger.error("Error in formatting input date to LocalDateTime", otherExp);
            addTimeLimit_1();
        }
        return time;
    }

    private void checkCalendarDate_1(LocalDateTime time) {
        if ((time.isBefore(LocalDateTime.now())) || (time == null)) {
            logger.error("Calendar can't exist with this time");
            addTimeLimit_1();
        }
    }

    /**
     * Method that asks user to end date to which calendar ends.
     *
     * @return - get the end date of task
     */
    public LocalDateTime addTimeLimit_2() {
        System.out.println("Set the end time period for which you want "
                + "to get the calendar.\n"
                + "or just copy this (2022-02-18 20:14) without braces:\r");
        String date = "";
        LocalDateTime time = null;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = bufReader.readLine();
            time = LocalDateTime.parse(date, timeFormatter);
            checkCalendarDate_2(time);
        } catch (IOException exp) {
            logger.error("Error in input date to the console", exp);
            addTimeLimit_2();
        } catch (DateTimeParseException otherExp) {
            logger.error("Error in formatting input date to LocalDateTime", otherExp);
            addTimeLimit_2();
        }
        return time;
    }

    private void checkCalendarDate_2(LocalDateTime time) {
        if ((time.isBefore(LocalDateTime.now())) || (time == null)) {
            logger.error("Calendar can't exist with this time");
            addTimeLimit_2();
        }
    }

    /**
     * Method that asks user for further action and return it number.
     *
     * @return - get int number that is responsible to action
     */
    public int getAction() {
        System.out.println("Type what do you want to perform: ");
        System.out.println("\t 1 - To saved task and exit\n\t 2- To load list of task"
                + "\n\t 3 - To return to main menu of application");
        int inputNumber = 0;
        try {
            inputNumber = Integer.parseInt(bufReader.readLine());
            checkIntValue(inputNumber);
        } catch (IOException mainExp) {
            logger.error("Error with typing number or wrong format for number", mainExp);
        } catch (NumberFormatException otherExp) {
            logger.error("This integer number can't exist", otherExp);
        }
        return inputNumber;
    }

    /**
     * Method that ask user to input name for the file and return it.
     *
     * @return - get the name of the file from the user input
     */
    public String getFileName() {
        String nameFile = null;
        System.out.println("Print the name of the file:\r");
        try {
            nameFile = bufReader.readLine();
            checkStringValue(nameFile);
        } catch (IOException exp) {
            logger.error("Error in typing word to the console", exp);
            getFileName();
        } catch (IllegalArgumentException otherExp) {
            logger.error("Not String type", otherExp);
            getFileName();
        }
        return nameFile;
    }
}
