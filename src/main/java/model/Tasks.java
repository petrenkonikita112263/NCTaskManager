package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class implementing calendar and create static method that
 * can works with both type of the list (array and linked).
 *
 * @author Nikita
 */
public class Tasks implements Serializable {

    /**
     * Static incoming method that can work as with ArrayList and
     * LinkedList as well.
     *
     * @param tasks -
     * @param start - start time
     * @param end   - end time
     * @return -
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks,
                                          LocalDateTime start,
                                          LocalDateTime end) {
        AbstractTaskList tempListTask = new ArrayTaskList();
        LocalDateTime dateOfTask;
        for (Task smth : tasks) {
            dateOfTask = smth.nextTimeAfter(start);
            if ((dateOfTask != null) && (dateOfTask.compareTo(end) <= 0)) {
                tempListTask.add(smth);
            }
        }
        return tempListTask;
    }

    /**
     * Static method that creates calendar for a set period of time.
     *
     * @param tasks -
     * @param start - start time
     * @param end   - end time
     * @return -
     */
    public static SortedMap<LocalDateTime, Set<Task>>
    calendar(Iterable<Task> tasks, LocalDateTime start,
             LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> sortedMap = new TreeMap<>();
        Set<Task> set;
        LocalDateTime current;
        for (Task task:tasks) {
            current = task.nextTimeAfter(start.minusNanos(1));
            while (current != null && !current.isAfter(end)) {
                if (sortedMap.containsKey(current)) {
                    sortedMap.get(current).add(task);
                }
                else {
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
