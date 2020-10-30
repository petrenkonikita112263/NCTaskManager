package view;

import java.io.BufferedReader;

/**
 * Main interface of View part of MVC project.
 *
 * @author Nikita
 */
public interface CoreViewable {

    /**
     * Method that displays main menu application.
     */
    void displayInfo();

    /**
     * Public method that allow user to input info in console.
     *
     * @return get the input number from console
     */
    int getUserInput(BufferedReader bufferedReader);
}
