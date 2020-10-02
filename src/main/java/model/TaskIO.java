package model;

import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;
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
     * Static method that write list into the stream.
     *
     * @param taskList     list of the tasks
     * @param outputStream to where our task are saved
     */
    public static void write(AbstractTaskList taskList,
                             OutputStream outputStream) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {
            dataOutputStream.writeInt(taskList.size());
            for (Task smth : taskList) {
                dataOutputStream.writeInt(smth.getTitle().length());
                dataOutputStream.writeUTF(smth.getTitle());
                dataOutputStream.writeInt(smth.isActive() ? 1 : 0);
                dataOutputStream.writeInt(smth.getRepeatInterval());
                dataOutputStream.writeLong(smth.getStartTime()
                        .toEpochSecond(UTC));
                if (smth.isRepeated()) {
                    dataOutputStream.writeLong(smth.getEndTime()
                            .toEpochSecond(UTC));
                }
            }
        } catch (Exception mainExp) {
            logger.error("Error in writing data information about task ", mainExp);
        } finally {
            try {
                dataOutputStream.flush();
            } catch (IOException e_1) {
                logger.error("Error in flushing the content of the buffer "
                        + "to the output stream ", e_1);
                e_1.printStackTrace();
            }
            try {
                dataOutputStream.close();
            } catch (IOException e_2) {
                logger.error("Error in closing the output stream ", e_2);
            }
        }
    }

    /**
     * Static method that read list from the stream.
     *
     * @param taskList    list of the tasks
     * @param inputStream from input stream we get all our saved info
     */
    public static void read(AbstractTaskList taskList,
                            InputStream inputStream) {
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(inputStream);
            int quantityOfTask = dataInputStream.readInt();
            for (int i = 0; i < quantityOfTask; i++) {
                int idTask = dataInputStream.readInt();
                int titleLength = dataInputStream.readInt();
                String titleOfTask = dataInputStream.readUTF();
                boolean statusActive = dataInputStream.readInt() == 1;
                int valueOfInterval = dataInputStream.readInt();
                LocalDateTime startDateTime = LocalDateTime
                        .ofEpochSecond(dataInputStream.readLong(),
                                0, UTC);
                if (valueOfInterval != 0) {
                    LocalDateTime endDateTime = LocalDateTime
                            .ofEpochSecond(dataInputStream.readLong(),
                                    0, UTC);
                    Task newTask = new Task(titleOfTask, startDateTime,
                            endDateTime, valueOfInterval);
                    newTask.setActive(statusActive);
                    taskList.add(newTask);
                } else {
                    Task newTask = new Task(titleOfTask, startDateTime);
                    newTask.setActive(statusActive);
                    taskList.add(newTask);
                }
            }
        } catch (Exception mainExp) {
            logger.error("Error in reading data information about task ", mainExp);
        } finally {
            try {
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
            } catch (IOException e) {
                logger.error("Error in closing the input stream ", e);
            }
        }
    }

    /**
     * Static method that write list into binary file.
     *
     * @param taskList list of the tasks
     * @param file     name of the file, which store our list
     * @throws IOException input|output exception, failure during reading,
     *                     writing and searching file
     */
    public static void writeBinary(AbstractTaskList taskList,
                                   File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            write(taskList, fileOutputStream);
        }
    }

    /**
     * Static method that read list from binary file.
     *
     * @param taskList list of the tasks
     * @param file     name of the file, from which read the list
     * @throws IOException input|output exception, failure during reading,
     *                     writing and searching file
     */
    public static void readBinary(AbstractTaskList taskList,
                                  File file) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream =
                    new BufferedOutputStream(new FileOutputStream(file));
        } finally {
            if (bufferedOutputStream != null) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        }
    }

    /**
     * Static method that write tasks to stream in GSON format.
     *
     * @param taskList list of the tasks
     * @param writer   writing text, that is based on characters
     * @throws IOException input|output exception, failure
     *                     during reading, writing and searching file
     */
    public static void write(AbstractTaskList taskList,
                             Writer writer) throws IOException {
        Gson gson = new Gson();
        try {
            gson.toJson(taskList, writer);
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * Static method that read tasks from stream.
     *
     * @param taskList list of the tasks
     * @param reader   reading text, that is based on characters
     * @throws IOException input|output exception, failure
     *                     during reading, writing and searching file
     */
    public static void read(AbstractTaskList taskList,
                            Reader reader) throws IOException {
        Gson gson = new Gson();
        AbstractTaskList abstractTaskList = gson
                .fromJson(reader, taskList.getClass());
        try {
            for (Task smth : abstractTaskList) {
                taskList.add(smth);
            }
        } finally {
            reader.close();
        }
    }

    /**
     * Static method that write tasks to GSON text file.
     *
     * @param taskList list of the tasks
     * @param file     to which file write the whole list
     */
    public static void writeText(AbstractTaskList taskList,
                                 File file) {
        PrintWriter printWriter = null;
        Gson gson = new Gson();
        ArrayTaskList arrayTaskList = (ArrayTaskList) taskList;
        try {
            printWriter = new PrintWriter(
                    new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            logger.error("Error writing content to the file ", e);
        }
        try {
            if (printWriter != null) {
                printWriter.write(gson
                        .toJson(arrayTaskList, ArrayTaskList.class));
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
     * @throws IOException input|output exception, failure during reading,
     *                     writing and searching file
     */
    public static void readText(AbstractTaskList taskList,
                                File file) throws IOException {
        FileReader fileReader = null;
        Gson gson = new Gson();
        ArrayTaskList arrayTaskList;
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            arrayTaskList = gson.fromJson(in, (Type) taskList);
            for (Task smth : arrayTaskList) {
                taskList.add(smth);
            }
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }
}
