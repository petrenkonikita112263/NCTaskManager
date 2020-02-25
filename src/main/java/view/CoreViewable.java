package view;

import model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Main interface of View part of MVC project.
 *
 * @author Nikita
 */
public interface CoreViewable {

    /**
     * Method that displays main menu application.
     */
    void displayInfo();

    /**
     * Method displays additional functionality of
     * application.
     */
    void displayAdditionalInfo();

    /**
     * Method displays the created calendar.
     *
     * @param values - SortedMap collection where
     *               key - date and value - is task
     */
    void displayCreatedCalendar(SortedMap<LocalDateTime, Set<Task>> values);

}
