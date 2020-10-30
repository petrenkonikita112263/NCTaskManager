//package view;
//
//import java.io.IOException;
//
//import static java.lang.Integer.parseInt;
//
//public class ChangeView extends PrimaryView {
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
//     * Method that allow user to input id for task.
//     *
//     * @return id of it
//     */
//    public int getTaskIndex() {
//        int id = 0;
//        System.out.println("Enter the id of the task " +
//                "\nP.S. Note and bring to the attention that ArrayList element starts from 0: \r");
//        try {
//            id = parseInt(bufReader.readLine());
//            if (id < 0) {
//                System.out.println("This number can't be negative or bigger than Integer");
//                getTaskIndex();
//            }
//        } catch (IOException exp_1) {
//            LOGGER.error("Error in input number to the console", exp_1);
//            getTaskIndex();
//        } catch (NumberFormatException exp_2) {
//            System.out.println("Required integer number, but your type isn't based on it");
//            LOGGER.error("Wrong type of id for task", exp_2);
//            getTaskIndex();
//        }
//        return id;
//    }
//
//    /**
//     * Print menu options for change task in the list.
//     */
//    public void changeFunctionalityOfTask() {
//        System.out.println("For your task you can change these parameters:\n" +
//                "If you're changing repetead task your options are 1, 2, 3, 4, 5."
//                + " \nBut if you're changing non-repeated task your options are 1, 2, 3, 4.\n"
//                + "\t1 - Change title of the task" +
//                "\t2 - Change time" +
//                "\t3 - Change type" +
//                "\t4 - Change status" +
//                "\t5 - Change interval"
//                + "\nBack to menu for repeteted task type\t - 6; for non-repetead type\t - 7");
//    }
//
//    /**
//     * Method that ask user to change type of the task.
//     *
//     * @return 0 - disable it, 1 - enable it
//     */
//    public int changeStatusOfTask() {
//        int taskStatus = 0;
//        System.out.println("Change status of task"
//                + "\n0 - disable task; \n1 - enable task; \npress Enter to back to additional menu: \r");
//        try {
//            taskStatus = parseInt(bufReader.readLine());
//            if (taskStatus < 0 || taskStatus > 1) {
//                System.out.println("This number can't be negative or bigger than  option number or even Integer");
//                changeStatusOfTask();
//            }
//        } catch (IOException exp_1) {
//            LOGGER.error("Error in input number to the console", exp_1);
//            changeStatusOfTask();
//        } catch (NumberFormatException exp_2) {
//            System.out.println("Required integer number, but your type isn't based on it");
//            LOGGER.error("This integer number can't exist", exp_2);
//            changeStatusOfTask();
//        }
//        return taskStatus;
//    }
//
//
//}
