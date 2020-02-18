package controller;

import model.ArrayTaskList;

import java.io.IOException;

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
    void processAddingTask() throws IOException;

    /**
     * Method that allow to change all information about task
     * like id, title, time and status.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void processChangingTask() throws IOException;

    /**
     * Method that allow to delete task from the list.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void processDeletingTask() throws IOException;

    /**
     * Method that allow to create the calendar for set
     * period of time.
     */
    void createCalendar() throws IOException;

//    /**
//     * Method that allow to see chosen task.
//     */
//    void showTaskDetails() throws IOException;
//
//    /**
//     * Method that print all tasks.
//     */
//    void showListOfTask() throws IOException;

    /**
     * Method that allow to change all information about task
     * like id, title, time and status.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void processSavingWork() throws IOException;

    /**
     * Method that allow to continue work with application
     * from previous session.
     *
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    void continueWork() throws IOException;

    /**
     * Method that prints the content of ArrayList
     *
     * @param taskList - ArrayList of tasks
     */
    void displayListOfTasks(ArrayTaskList taskList);

    /**
     * Method that prints complete information on every task
     * (it's title, time, interval, etc.)
     */
    void displayDetailAboutTask(ArrayTaskList taskList);
}
