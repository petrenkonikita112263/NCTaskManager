package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * This class prevent you to save and read your task list
 * to/from simple type of file or GSON text file.
 *
 * @author Nikita
 */
public class TaskIO {

    /**
     * Adding logger to the class.
     */
    private static final Logger logger = getLogger(TaskIO.class);

    /**
     * Static method that write tasks to GSON text file.
     *
     * @param taskList list of the tasks
     * @param file     to which file write the whole list
     */
    public static void writeText(CopyOnWriteArrayList<Task> taskList,
                                 File file) {
        PrintWriter printWriter = null;
        Gson gson = new Gson();
        try {
            printWriter = new PrintWriter(
                    new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            logger.error("Error writing content to the file ", e);
        }
        try {
            if (printWriter != null) {
                printWriter.write(gson
                        .toJson(taskList, CopyOnWriteArrayList.class));
            }
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    /**
     * Static method that read tasks from GSON text file.
     *
     * @param taskList list of the tasks
     * @param file     from which file read the whole list
     */
    public static void readText(CopyOnWriteArrayList<Task> taskList,
                                File file) {
        FileReader fileReader = null;
        Gson gson = new Gson();
        Type userListType = new TypeToken<CopyOnWriteArrayList<Task>>() {
        }.getType();
        CopyOnWriteArrayList<Task> arrayTaskList;
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            arrayTaskList = gson.fromJson(in, userListType);
            taskList.addAll(arrayTaskList);
        } catch (FileNotFoundException e) {
            logger.error("Can't find file with tasklist", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    logger.error("Can't close the stream", e);
                }
            }
        }
    }

    public static String getFileLocation(String fileName) {
        StringJoiner stringJoiner = new StringJoiner(File.separator);
        stringJoiner.add("tasks storage").add(fileName + ".json");
        return stringJoiner.toString();
    }
}
