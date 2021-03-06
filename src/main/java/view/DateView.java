package view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateView extends PrimaryView {

    /**
     * Method that allow to input date|change date.
     *
     * @return LocalDateTime that user inputs
     */
    public LocalDateTime inputDateTime() {
        System.out.println("Please enter the time for task like in braces "
                + "or just copy this (2020-02-18 20:14) without braces:"
                + " and change numbers \r");
        String date = "";
        LocalDateTime time = null;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = bufReader.readLine();
            time = LocalDateTime.parse(date, timeFormatter);
            checkTime(time);
        } catch (IOException exp) {
            LOGGER.error("Error in input date to the console", exp);
            inputDateTime();
        } catch (DateTimeParseException otherExp) {
            System.out.println("Input date isn't based on required format");
            LOGGER.error("Error in formatting input date to LocalDateTime", otherExp);
            inputDateTime();
        }
        return time;
    }

    /**
     * Private additional method that validate LocalDateTime
     * from user input.
     *
     * @param time time value for task
     */
    private void checkTime(LocalDateTime time) {
        if (time.isBefore(LocalDateTime.now()) || time.isEqual(LocalDateTime.now())) {
            System.out.println("You enter wrong date, date can't be before today or equals now date");
            inputDateTime();
        }
    }

}
