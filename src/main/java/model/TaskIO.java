package model;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * This class prevent you to save and read your task list
 * to/from simple type of file or GSON text file.
 *
 * @author Nikita
 */
public class TaskIO {

    /**
     * Static method that write list into the stream.
     *
     * @param taskList     - list of the tasks
     * @param outputStream - to where our task are saved
     */
    public static void write(AbstractTaskList taskList,
                             OutputStream outputStream) {

//        create binary stream to write information
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {

//            write how many tasks in our list (taskList)
            dataOutputStream.writeInt(taskList.size());

//            run thoough the taskList
            for (Task smth : taskList) {

                dataOutputStream.writeInt(smth.getId());

//                write the int number of length of the title
                dataOutputStream.writeInt(smth.getTitle().length());

//                write the whole title of the task
                dataOutputStream.writeUTF(smth.getTitle());

//                if (smth.isActive()) {
//                    dataOutputStream.writeInt(1);
//                } else {
//                    dataOutputStream.writeInt(0);
//                }
//                use lambdas to write 1 - if task is active, alternatively - 0
                dataOutputStream.writeInt(smth.isActive() ? 1 : 0);

//                write int number of the value of the interval
                dataOutputStream.writeInt(smth.getRepeatInterval());

//                write the startDate of task: date -> milliseconds
                dataOutputStream.writeLong(smth.getStartTime()
                        .toEpochSecond(ZoneOffset.UTC));

//                if task is repeated
                if (smth.isRepeated()) {

//                    dataOutputStream.writeLong(smth.getStartTime().getNano());

//                    write the endDate as startDate
                    dataOutputStream.writeLong(smth.getEndTime()
                            .toEpochSecond(ZoneOffset.UTC));
                }
            }

//            catch clauses - or add exception to the main method
        } catch (Exception mainExp) {
            mainExp.printStackTrace();
        } finally {
            try {

//                clear the buffer
                dataOutputStream.flush();
            } catch (IOException e_1) {
                e_1.printStackTrace();
            }
            try {

//                close the stream
                dataOutputStream.close();
            } catch (IOException e_2) {
                e_2.printStackTrace();
            }
        }
    }

    /**
     * Static method that read list from the stream.
     *
     * @param taskList    - list of the tasks
     * @param inputStream - from input stream we get all our saved info
     */
    public static void read(AbstractTaskList taskList,
                            InputStream inputStream) {

//        create binary input stream
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(inputStream);

//            read how many tasks are there
            int quantityOfTask = dataInputStream.readInt();

//            run throught the collection
            for (int i = 0; i < quantityOfTask; i++) {

                int idTask = dataInputStream.readInt();

//                read how long is title
                int titleLength = dataInputStream.readInt();

//                read the title of the task
                String titleOfTask = dataInputStream.readUTF();

//                read task is active if 1 == 1 -> true
                boolean statusActive = dataInputStream.readInt() == 1;

//                read the interval of the task
                int valueOfInterval = dataInputStream.readInt();

//                read time, and transform milliseconds -> LocalDateTime
                LocalDateTime startDateTime = LocalDateTime
                        .ofEpochSecond(dataInputStream.readLong(),
                                0, ZoneOffset.UTC);

//                if task is repeated
                if (valueOfInterval != 0) {

//                    long startTime = dataInputStream.readLong();
//                    LocalDateTime startDateTime = Instant.ofEpochMilli(startTime).
//                            atZone(ZoneId.systemDefault()).toLocalDateTime();
//                    long endTime = dataInputStream.readLong();
//                    LocalDateTime endDateTime = Instant.ofEpochMilli(endTime).
//                            atZone(ZoneId.systemDefault()).toLocalDateTime();
                    LocalDateTime endDateTime = LocalDateTime
                            .ofEpochSecond(dataInputStream.readLong(),
                            0, ZoneOffset.UTC);
                    Task newTask = new Task(idTask, titleOfTask, startDateTime,
                            endDateTime, valueOfInterval);

//                    set the status of the task UNNECESSARY
                    newTask.setActive(statusActive);

//                    add task to the list
                    taskList.add(newTask);
                } else {

//                    long time = dataInputStream.readLong();
//                    LocalDateTime timeDate = Instant.ofEpochMilli(time).
//                            atZone(ZoneId.systemDefault()).toLocalDateTime();
                    Task newTask = new Task(idTask, titleOfTask, startDateTime);
                    newTask.setActive(statusActive);
                    taskList.add(newTask);
                }
            }
        } catch (Exception mainExp) {
            mainExp.printStackTrace();
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Static method that write list into binary file.
     *
     * @param taskList - list of the tasks
     * @param file     - name of the file, which store our list
     * @throws IOException - input|output exception, failure during reading,
     * writing and searching file
     */
    public static void writeBinary(AbstractTaskList taskList,
                                   File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            write(taskList, fileOutputStream);
        } finally {
            fileOutputStream.close();
        }
    }

    /**
     * Static method that read list from binary file.
     *
     * @param taskList - list of the tasks
     * @param file     - name of the file, from which read the list
     * @throws IOException - input|output exception, failure during reading,
     * writing and searching file
     */
    public static void readBinary(AbstractTaskList taskList,
                                  File file) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream =
                    new BufferedOutputStream(new FileOutputStream(file));
        } finally {
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
    }

    /**
     * Static method that write tasks to stream in GSON format.
     *
     * @param taskList - list of the tasks
     * @param writer   - writing text, that is based on characters
     * @throws IOException - input|output exception, failure
     * during reading, writing and searching file
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
     * @param taskList - list of the tasks
     * @param reader   - reading text, that is based on characters
     * @throws IOException - input|output exception, failure
     * during reading, writing and searching file
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
     * @param taskList - list of the tasks
     * @param file     - to which file write the whole list
     * @throws IOException - input|output exception, failure
     * during reading, writing and searching file
     */
    public static void writeText(AbstractTaskList taskList,
                                 File file) throws IOException {
        Charset utf8 = StandardCharsets.UTF_8;
        PrintWriter printWriter = null;
        Gson gson = new Gson();
        ArrayTaskList arrayTaskList = (ArrayTaskList) taskList;
        try {
            printWriter = new PrintWriter(
                    new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            printWriter.write(gson
                    .toJson(arrayTaskList, ArrayTaskList.class));
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    /**
     * Static method that read tasks from GSON text file.
     *
     * @param taskList - list of the tasks
     * @param file     - from which file read the whole list
     * @throws IOException - input|output exception, failure during reading,
     * writing and searching file
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
//            gson.fromJson(fileReader, (Type) taskList);
//            read(taskList, fileReader);
        } finally {
            fileReader.close();
        }
    }

//    public static void write(AbstractTaskList taskList, Writer writer) {
//
//        BufferedWriter bufferedWriter = new BufferedWriter(writer);
//        try {
//            Iterator iterator = taskList.iterator();
//            while (iterator.hasNext()) {
//                Task newTask = (Task) iterator.next();
//                bufferedWriter.write(newTask.toString() + "\n");
//            }
//        } catch (IOException mainExp) {
//            mainExp.printStackTrace();
//        } finally {
//            try {
//                bufferedWriter.flush();
//            } catch (IOException e_1) {
//                e_1.printStackTrace();
//            }
//            try {
//                bufferedWriter.close();
//            } catch (IOException e_2) {
//                e_2.printStackTrace();
//            }
//        }
//    }

//    public static void read(AbstractTaskList taskList, Reader reader) {
//        BufferedReader bufferedReader = new BufferedReader(reader);
//        try {
//            String stringOfTask;
//            while ((stringOfTask = bufferedReader.readLine()) != null) {
//                System.out.println(stringOfTask);
//                ;
//            }
//        } catch (IOException mainExp) {
//            mainExp.printStackTrace();
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
