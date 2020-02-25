import controller.MajorController;

import java.io.IOException;

/**
 * Main class that run MVC project.
 */
public class TestTaskManager {

    /**
     * Main satic method of the class.
     *
     * @param args - command-line arguments
     * @throws IOException - input|output exception, failure during reading,
     *                     writing information
     */
    public static void main(String[] args) throws IOException {


//        create controller instance
        MajorController mc = new MajorController();

//        run main menu from controller
        mc.runMainApplication();
    }

}
