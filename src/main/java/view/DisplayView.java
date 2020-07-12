package view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DisplayView extends PrimaryView {

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
     * Method that asks user to start date from which calendar starts.
     *
     * @return get the start date
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
            LOGGER.error("Error in input date to the console", exp);
            addTimeLimit_1();
        } catch (DateTimeParseException otherExp) {
            System.out.println("Input date isn't based on required format");
            LOGGER.error("Error in formatting input date to LocalDateTime", otherExp);
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
     * @return get the end date of task
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
            LOGGER.error("Error in input date to the console", exp);
            addTimeLimit_2();
        } catch (DateTimeParseException otherExp) {
            System.out.println("Input date isn't based on required format");
            LOGGER.error("Error in formatting input date to LocalDateTime", otherExp);
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

}
