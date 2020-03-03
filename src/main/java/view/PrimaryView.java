package view;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        System.out.println("\t4 - Display all tasks with information inside ArrayList");
        System.out.println("\t5 - Create calendar");
        System.out.println("\t6 - To write your task list to the file");
        System.out.println("\t7 - Terminate the application");
    }

    /**
     * Method that prints task title in console when calendar is running.
     */
    public void displayTaskTitle(String title) {
        System.out.println(title);
    }

    /**
     * Method that prints task date time in console when calendar is running.
     */
    public void displayTaskdate(String date) {
        System.out.println(date);
    }

    /**
     * Information message that reports about created new empty list.
     */
    public void getInfoAboutCreation() {
        System.out.println("The empty task list was created");
    }

    /**
     * If user tries to print empty task list, he'll get report about empty task list.
     */
    public void getMessageAboutEmptiness() {
        System.out.println("The list is empty, firstly add some tasks in it");
    }

    /**
     * Print content of list.
     */
    public void getViewForList(String stringText) {
        System.out.println(stringText);
    }

    /**
     * Print content of repetead task.
     */
    public void getViewForRepTask(String textTask) {
        System.out.println(textTask);
    }

    /**
     * Print content of non-repeated task.
     */
    public void getViewForNorTask(String textTask) {
        System.out.println(textTask);
    }

    /**
     * Public method that allow user to input info in console.
     *
     * @return - get the input number from console
     */
    public int getUserInput() {
        logger.info("User start works with console");
        try {
            bufReader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(bufReader.readLine());
        } catch (IOException e) {
            logger.error("Can't get access to console by BufferedReader", e);
        }
        return 0;
    }

    /**
     * This method will close the Buffered Stream, if it's not empty the error writes to log.
     */
    public void closeInput() {
        try {
            bufReader.close();
        } catch (IOException e) {
            logger.error("Can't close the BufferedReader ", e);
        }
    }

    /**
     * Ask user to type one of these numbers (1-7)
     */
    public int getNumberForFurtherAction() throws IOException {
        return Integer.parseInt(bufReader.readLine());
    }

    /**
     * Method that allow user to input id for task.
     *
     * @return - id of it
     */
    public int getTaskIndex() {
        int id = 0;
        System.out.println("Enter the id of the task " +
                "\nP.S. Note and bring to the attention that ArrayList element starts from 0: \r");
        try {
            id = Integer.parseInt(bufReader.readLine());
            if ((id < 0) || (id > Integer.MAX_VALUE)) {
                System.out.println("This number can't be negative or bigger than Integer");
                getTaskIndex();
            }
        } catch (IOException exp_1) {
            logger.error("Error in input number to the console", exp_1);
        } catch (NumberFormatException exp_2) {
            logger.error("Wrong type of id for task", exp_2);
        }
        return id;
    }

    /**
     * Private additional method that validate int number
     * from user input.
     *
     * @param number - any integer number in range of Integer
     */
    private void checkIntValue(int number) {
        if ((number > 60) || (number < 0) || (number > Integer.MAX_VALUE)) {
            System.out.println("Time in seconds can't be negative or move than 60");
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
            checkAddedTaskTitle(title);
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
     * Private additional method that validate task title
     * from user input.
     *
     * @param titleOfTask - task title that user try to add to Task
     */
    private void checkAddedTaskTitle(String titleOfTask) {
        if ((titleOfTask == null) || (titleOfTask.trim().isEmpty())) {
            System.out.println("Task can't exist with empty name or whitespaces\n"
                    + "You don't write any word. Please try again");
            addTaskTitle();
        }
    }

    /**
     * Private additional method that validate task title
     * from user input.
     *
     * @param titleOfTask - task title that user try to add to Task
     */
    private void checkChangedTaskTitle(String titleOfTask) {
        if ((titleOfTask == null) || (titleOfTask.trim().isEmpty())) {
            System.out.println("Task can't exist with empty name or whitespaces\n"
                    + "You don't write any word. Please try again");
            changeTitleOfTask();
        }
    }

    /**
     * Private additional method that validate name of the file
     * from user input.
     *
     * @param nameOfFile - name of the file that user type
     */
    private void checkFileName(String nameOfFile) {
        if ((nameOfFile == null) || (nameOfFile.trim().isEmpty())) {
            System.out.println("File can't exist with empty name or whitespaces\n"
                    + "You don't write any word. Please try again");
            getFileName();
        }
    }

    /**
     * Private additional method that validate LocalDateTime
     * from user input.
     *
     * @param time - time value for task
     */
    private void checkTime(LocalDateTime time) {
        if ((time.isBefore(LocalDateTime.now())) || (time == null)
                || (time.isEqual(LocalDateTime.now()))) {
            System.out.println("You enter wrong date, date can't be before today or equals now date");
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
        System.out.println("At last enter the interval in seconds "
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
            if ((id < 0) || (id > Integer.MAX_VALUE)) {
                System.out.println("This number can't be negative or bigger than Integer");
                removeSomeTask();
            }
        } catch (IOException exp_1) {
            logger.error("Error in input number to the console", exp_1);
        } catch (NumberFormatException exp_2) {
            logger.error("Wrong type of id for task", exp_2);
        }
        return id;
    }

    /**
     * Print menu options for change task in the list.
     */
    public void changeFunctionalityOfTask() {
        System.out.println("For your task you can change these parameters:\n" +
                "If you're changing repetead task your options are 1, 2, 3, 4, 5."
                + " \nBut if you're changing non-repeated task your options are 1, 2, 3, 4.\n"
                + "\t1 - Change title of the task" +
                "\t2 - Change time" +
                "\t3 - Change type" +
                "\t4 - Change status" +
                "\t5 - Change interval"
                + "\nBack to menu for repeteted task type\t - 6; for non-repetead type\t - 7");
    }

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
            checkChangedTaskTitle(taskName);
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
                + " or you want to add(change) non-repetitive task(only time) = 'no'" +
                "\nP.S if you are in menu change task press Enter to back to additional menu from it: \r");
        try {
            answer = bufReader.readLine();
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
        System.out.println("Enter the new interval in seconds for your task\r");
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
     * Method that ask user to change type of the task.
     *
     * @return - 0 - disable it, 1 - enable it
     */
    public int changeStatusOfTask() {
        int taskStatus = 0;
        System.out.println("Change status of task"
                + "\n0 - disable task; \n1 - enable task; \npress Enter to back to additional menu: \r");
        try {
            taskStatus = Integer.parseInt(bufReader.readLine());
            if ((taskStatus < 0) || (taskStatus > Integer.MAX_VALUE)) {
                System.out.println("This number can't be negative or bigger than Integer");
                changeStatusOfTask();
            }
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

    /**
     * Additional method that check local date time - it can't be past date.
     */
    private void checkCalendarDate_1(LocalDateTime time) {
        if ((time.isBefore(LocalDateTime.now())) || (time == null)) {
            System.out.println("You enter wrong date, date can't be before today or nothing info");
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

    /**
     * Additional method that check local date time - it can't be past date.
     */
    private void checkCalendarDate_2(LocalDateTime time) {
        if ((time.isBefore(LocalDateTime.now())) || (time == null)) {
            System.out.println("You enter wrong date, date can't be before today or nothing info");
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
        System.out.println("\t 1 - To saved task and exit"
                + "\n\t Press ENTER - To return to main menu of application");
        int inputNumber = 0;
        try {
            inputNumber = Integer.parseInt(bufReader.readLine());
            if ((inputNumber < 0) || (inputNumber > Integer.MAX_VALUE)) {
                System.out.println("This number can't be negative or bigger than Integer");
                getAction();
            }
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
            checkFileName(nameFile);
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
