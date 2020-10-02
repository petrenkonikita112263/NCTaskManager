package test;

import controller.CoreController;
import controller.MajorController;

import java.io.IOException;

/**
 * Main class that run MVC project.
 *
 * @author Nikita
 */
public class App {
    /**3
     * Main static method of the class.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) throws IOException {
        CoreController controller = new MajorController();
        controller.runMainApplication();
    }
}
