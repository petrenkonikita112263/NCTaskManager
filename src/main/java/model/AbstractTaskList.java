package model;

import java.util.stream.Stream;

/**
 * Abstract class that holds common methods for ArrayTaskList class
 * and LinkedTaskList class.
 * @author Nikita
 */
public abstract class AbstractTaskList implements Iterable<Task> {

    abstract void add(Task task);

    abstract boolean remove(Task task);

    abstract int size();

    abstract Task getTask(int index);

    /**
     * Common method getStream() that replace incoming method in ArrayTaskList
     * class and LinkedTaskList class.
     *
     * @return - get stream
     */
    public Stream<Task> getStream() {
        return null;
    }

//    /**
//     * incoming method for both type of collections.
//     *
//     * @param from - from what time
//     * @param to   - until what time
//     * @return incomingAbstractTaskLsst
//     */
//    public AbstractTaskList incoming(int from, int to) {
//        AbstractTaskList abstractTaskList;
//        if (this instanceof LinkedTaskList) {
//            abstractTaskList = new LinkedTaskList();
//        } else {
//            abstractTaskList = new ArrayTaskList();
//        }
//        abstractTaskList.getStream()                                        // call getStream()
//                .filter(time -> (time.nextTimeAfter(from) != -1)            // we want only this time
//                        && (time.nextTimeAfter(from) <= to))
//                .forEach(abstractTaskList::add);                            // lambda expression for ForEach loop
//        return abstractTaskList;
//
////        for (int i = 0; i < size(); i++) {
////            if ((getTask(i).nextTimeAfter(from) != -1)
////                    && (getTask(i).nextTimeAfter(from) <= to)) {
////                incomingAbstarctTaskList.add(getTask(i));
////            }
////        }
////        return incomingAbstarctTaskList;
//    }
}
