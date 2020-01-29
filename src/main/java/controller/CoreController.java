package controller;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Main interface of View part of MVC project.
 *
 * @author Nikita
 */
public interface CoreController {

    /**
     * Main method that run application and display main menu.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void runMainApplication() throws IOException;

    /**
     * Method that allow add task to the list.
     */
    void addSomeTask();

    /**
     * Method that allow to change all information about task
     * like id, title, time and status.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void changeTask() throws IOException;

    /**
     * Method that allow to delete task from the list.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void removeSomeTask() throws IOException;

    /**
     * Method that allow to create the calendar for set
     * period of time.
     *
     * @param limitDate - the period of time for that calendar
     *                  will be created
     */
    void createCalendar(LocalDateTime limitDate);

    /**
     * Method that allow to write task list to GSON txt file.
     */
    void saveFileWithTasks();

    /**
     * Method that allow to read task list from GSON txt file.
     */
    void readFileWithTasks();

    /**
     * Method that allow to see chosen task.
     */
    void showTaskDetails();

    /**
     * Method that print all tasks.
     */
    void showListOfTask();

    /**
     * Method that allow to change all information about task
     * like id, title, time and status.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void saveWorkSession() throws IOException;

    /**
     * Method that allow to create empty array list.
     */
    void createEmptyList();

    /**
     * Method that allow to continue work with application
     * from previous session.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void continueWork() throws IOException;
}
