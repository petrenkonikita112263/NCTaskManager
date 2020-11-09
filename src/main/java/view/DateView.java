package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public class DateView extends PrimaryView {

    /**
     * Method that allow to input date|change date.
     *
     * @return LocalDateTime that user inputs
     */
    public LocalDateTime inputDateTime(BufferedReader bufferedReader) {
        System.out.println("Please enter the time for task like in braces "
                + "or just copy this (2020-02-18 20:14) without braces:"
                + " and change numbers \r");
        String date;
        LocalDateTime time = null;
        DateTimeFormatter timeFormatter = ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = bufferedReader.readLine();
            time = LocalDateTime.parse(date, timeFormatter);
            checkTime(time, bufferedReader);
        } catch (DateTimeParseException otherExp) {
            System.out.println("Input date isn't based on required format");
            LOGGER.error("Error in formatting input date to LocalDateTime", otherExp);
        } catch (IOException exp) {
            LOGGER.error("Error in input date to the console", exp);
        }
        return time;
    }

    /**
     * Private additional method that validate LocalDateTime
     * from user input.
     *
     * @param time time value for task
     */
    private void checkTime(LocalDateTime time, BufferedReader bufferedReader) {
        if (time.isBefore(now()) || time.isEqual(now())) {
            System.out.println("You enter wrong date, date can't be before today or equals now date");
            inputDateTime(bufferedReader);
        }
    }

}
