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
        AbstractTaskList tempListTask = new LinkedTaskList();
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

//        create Map Collection -> TreeMap,
//        where elements placed in order by Key
        TreeMap<LocalDateTime, Set<Task>> collectionOfTask =
                new TreeMap<>();

//        AbstractTaskList tempNewListOfTask =
//        (LinkedTaskLIst) incoming(tasks, start, end);
//        for (Task smth : tempNewListOfTask) {

//        create list from Iterable<Task> or
//        we can use AbstractTaskList -> LinkedTaskList
//        fill this list by incoming() method
        Iterable<Task> iterTask = incoming(tasks, start, end);
        LocalDateTime localDateTime;
        for (Task smth : iterTask) {
            localDateTime = smth.nextTimeAfter(start);
            while (localDateTime.compareTo(end) <= 0) {

//                check if there's this key we add a task to the collection
                if (collectionOfTask.containsKey(localDateTime)) {
                    collectionOfTask.get(localDateTime).add(smth);

//                else we create a new key using HashSet collection
//                and add the task
                } else {
                    HashSet hashSet = new HashSet();
                    hashSet.add(smth);
                    collectionOfTask.put(localDateTime, hashSet);
                }
                if (smth.nextTimeAfter(localDateTime) != null) {
                    localDateTime = smth.nextTimeAfter(localDateTime);
                }
            }
        }
        return collectionOfTask;
    }
}
