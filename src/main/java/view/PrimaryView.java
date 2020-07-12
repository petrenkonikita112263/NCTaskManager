package view;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * View class that implements interface of Core class.
 *
 * @author Nikita
 */
public class PrimaryView implements CoreViewable {

    /**
     * Adding logger to the class.
     */
    protected static final Logger LOGGER = Logger.getLogger(PrimaryView.class);

    /**
     * Instead of using Scanner, use BufferedReader for input.
     */
    protected static BufferedReader bufReader;

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

    /**{@inheritDoc}*/
    @Override
    public int getUserInput() {
        LOGGER.info("User start works with console");
        try {
            bufReader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(bufReader.readLine());
        } catch (IOException e) {
            LOGGER.error("Can't get access to console by BufferedReader", e);
        } catch (NumberFormatException e) {
            System.out.println("Required integer number, but your type isn't based on it");
            LOGGER.error("Wrong input type", e);
            getUserInput();
        }
        return 0;
    }

    /**{@inheritDoc}*/
    @Override
    public void closeInput() {
        try {
            bufReader.close();
        } catch (IOException e) {
            LOGGER.error("Can't close the BufferedReader ", e);
        }
    }
}
