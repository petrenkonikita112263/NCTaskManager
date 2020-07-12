package view;

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
    int getUserInput();

    /**
     * This method will close the Buffered Stream, if it's not empty the error writes to log.
     */
    public void closeInput();

}
