package view;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * View class that implements interface of Core class.
 *
 * @author Nikita
 */
public class PrimaryView implements CoreViewable {

    /**
     * Adding logger to the class.
     */
    protected static final Logger LOGGER = getLogger(PrimaryView.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayInfo() {
        System.out.println("The application Task Manager is running. "
                + "You have these options, so type number in range(1,4): \n");
        System.out.println("\t 1 - To continue work with your app"
                + " from previous savepoint");
        System.out.println("\t 2 - To load list of task");
        System.out.println("\t 3 - To create empty task list");
        System.out.println("\t 4 - To terminate your work with Task Manager");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getUserInput(BufferedReader bufferedReader) {
        LOGGER.info("User start works with console");
        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Required integer number, but your type isn't based on it");
            LOGGER.error("The number is out of the limit", e);
        } catch (IOException e) {
            LOGGER.error("Can't get access to console by BufferedReader", e);
        }
        return 0;
    }
}
