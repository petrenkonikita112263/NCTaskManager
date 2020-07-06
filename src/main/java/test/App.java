package test;

import controller.MajorController;

import java.io.IOException;

/**
 * Main class that run MVC project.
 *
 * @author Nikita
 */
public class App {
    /**
     * Main static method of the class.
     *
     * @param args command-line arguments
     * @throws IOException input|output exception, failure during reading,
     *                     writing information
     */
    public static void main(String[] args) throws IOException {
        MajorController mc = new MajorController();
        mc.runMainApplication();
    }
}
