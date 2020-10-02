package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class implementing calendar and create static method that
 * can works with both type of the list.
 *
 * @author Nikita
 */
public class Tasks implements Serializable {

    /**
     * Static method that creates calendar for a set period of time.
     *
     * @param tasks list of tasks
     * @param start start time
     * @param end   end time
     * @return portion of map where keys are in range from {@code LocalDateTime} inclusive to
     * {@code Set<Task>} exclusive
     */
    public static SortedMap<LocalDateTime, Set<Task>>
    calendar(Iterable<Task> tasks, LocalDateTime start,
             LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> sortedMap = new TreeMap<>();
        Set<Task> set;
        LocalDateTime current;
        for (Task task : tasks) {
            current = task.nextTimeAfter(start.minusNanos(1));
            while (current != null && !current.isAfter(end)) {
                if (sortedMap.containsKey(current)) {
                    sortedMap.get(current).add(task);
                } else {
                    set = new HashSet<>();
                    set.add(task);
                    sortedMap.put(current, set);
                }
                current = task.nextTimeAfter(current);
            }
        }
        return sortedMap;
    }
}
