package test;

import controller.CoreController;
import controller.MajorController;
import model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CopyOnWriteArrayList;

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
    public static void main(String[] args) {
        CopyOnWriteArrayList<Task> taskCollection = new CopyOnWriteArrayList<>();
        try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in))) {
            CoreController majorController = new MajorController(taskCollection, bufReader);
            majorController.runMainApplication();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
