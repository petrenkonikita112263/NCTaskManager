//package view;
//
//import java.io.IOException;
//
//import static java.lang.Integer.parseInt;
//
//public class AddView extends PrimaryView {
//
//    /**
//     * Method that allow user to input title for task.
//     *
//     * @return title of it
//     */
//    public String addTaskTitle() {
//        String title = null;
//        System.out.println("Enter the title for task: \r");
//        try {
//            title = bufReader.readLine();
//            checkAddedTaskTitle(title);
//        } catch (IOException exp_1) {
//            LOGGER.error("Error in input word to the console", exp_1);
//            addTaskTitle();
//        } catch (IllegalArgumentException exp_2) {
//            System.out.println("Required string, but your type isn't based on it");
//            LOGGER.error("Task can't exist with this title", exp_2);
//            addTaskTitle();
//        }
//        return title;
//    }
//
//    /**
//     * Method that ask user to change type of the task.
//     *
//     * @return new type repeated or not
//     */
//    public String selectTheTypeForTask() {
//        String answer = null;
//        System.out.println("Do you want to add(change) repetitive task (start, end, interval) = 'yes'"
//                + " or you want to add(change) non-repetitive task(only time) = 'no'" +
//                "\nP.S if you are in menu change task press Enter to back to additional menu from it: \r");
//        try {
//            answer = bufReader.readLine();
//        } catch (IOException exp) {
//            LOGGER.error("Error in input word to the console", exp);
//            selectTheTypeForTask();
//        } catch (IllegalArgumentException exp_2) {
//            System.out.println("Required string, but your type isn't based on it");
//            LOGGER.error("Not String type", exp_2);
//            selectTheTypeForTask();
//        }
//        return answer;
//    }
//
//    /**
//     * Private additional method that validate task title
//     * from user input.
//     *
//     * @param titleOfTask task title that user try to add to Task
//     */
//    private void checkAddedTaskTitle(String titleOfTask) {
//        if ((titleOfTask == null) || (titleOfTask.trim().isEmpty())) {
//            System.out.println("Task can't exist with empty name or whitespaces\n"
//                    + "You don't write any word. Please try again");
//            addTaskTitle();
//        }
//    }
//
//    /**
//     * Method that allow user to input interval for task.
//     *
//     * @return input interval
//     */
//    public int addInterval() {
//        int taskInterval = 0;
//        System.out.println("At last enter the interval in minutes "
//                + "for this task (integer value): \r");
//        try {
//            taskInterval = parseInt(bufReader.readLine());
//            checkIntValue(taskInterval);
//        } catch (IOException exp_1) {
//            LOGGER.error("Error in input number to the console", exp_1);
//            addInterval();
//        } catch (NumberFormatException exp_2) {
//            LOGGER.error("Wrong type of interval for task", exp_2);
//            System.out.println("Required integer number, but your type isn't based on it");
//            addInterval();
//        }
//        return taskInterval;
//    }
//
//    /**
//     * Private additional method that validate int number
//     * from user input.
//     *
//     * @param number any integer number in range of Integer
//     */
//    private void checkIntValue(int number) {
//        if (number > 60 || number < 0) {
//            System.out.println("Time in minutes can't be negative or move than 60");
//            addInterval();
//        }
//    }
//
//}
