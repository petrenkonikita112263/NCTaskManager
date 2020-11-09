package view;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadView extends PrimaryView {

    /**
     * Method that ask user to input name for the file and return it.
     *
     * @return get the name of the file from the user input
     */
    public String getFileName(BufferedReader bufferedReader) {
        String nameFile = null;
        System.out.println("Print the name of the file (without extension):\r");
        try {
            nameFile = bufferedReader.readLine();
            checkFileName(nameFile, bufferedReader);
        } catch (IOException exp) {
            LOGGER.error("Error in typing word to the console", exp);
            getFileName(bufferedReader);
        } catch (IllegalArgumentException otherExp) {
            System.out.println("Required string, but your type isn't based on it");
            LOGGER.error("Not String type", otherExp);
            getFileName(bufferedReader);
        }
        return nameFile;
    }

    /**
     * Private additional method that validate name of the file
     * from user input.
     *
     * @param nameOfFile name of the file that user type
     */
    private void checkFileName(String nameOfFile, BufferedReader bufferedReader) {
        if ((nameOfFile == null) || (nameOfFile.trim().isEmpty())) {
            System.out.println("File can't exist with empty name or whitespaces\n"
                    + "You don't write any word. Please try again");
            getFileName(bufferedReader);
        }
    }

    /**
     * Print message if application can't find file.
     * And create another one default file.
     */
    public void getMessageAboutDontFind(String message) {
        System.out.println("Unfortunately the program couldn't find this file\n"
                + " but your work continue the new default file was created "
                + "by these path " + message);
    }

}
