/**
 * Provides the classes necessary to create an
 * app and the classes on uses to work
 *
 * @ author petrenko
 * @ version 1
 * @since 1.0
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Task class creates a no repetitive task with a title and time.
 * It also creates repetitive task with a title, start time, end time and
 * interval. The Task class gives you opportunity to change status of the task.
 *
 * @author Nikita
 */
public class Task implements Cloneable, Serializable {

    /**
     * id - number of the task.
     */
    private int id;

    /**
     * title - name of the task.
     */
    private String title;

    /**
     * time - value of time.
     */
    private LocalDateTime time;

    /**
     * start - when the task starts.
     */
    private LocalDateTime start;

    /**
     * end - when the task ends.
     */
    private LocalDateTime end;

    /**
     * interval - how fast task progresses.
     */
    private int interval;

    /**
     * active - status of task.
     */
    private boolean active;

    /**
     * repeated - can task be repeated.
     */
    private boolean repeated;

    /**
     * DVC 1st constructor with default title and time.
     */
    public Task() {
        this(0, "Nothing", LocalDateTime.now());
    }

    /**
     * EVC 2nd constructor for nonrepeative task.
     *
     * @param id    - number of the task
     * @param title - title of task
     * @param time  - time for this task
     */
    public Task(int id, String title, LocalDateTime time) {
        this.id = id;
        this.title = title;
        if (time == null) {
            throw new IllegalArgumentException("Time can't be negative");
        }
        this.time = time;
    }

        /**
     * EVC 3th constructor for repeative task.
     *
     * @param id       - number of the task
     * @param title    - title of task
     * @param start    - start time for this task
     * @param end      - end time for task
     * @param interval - interval of time (from start to end)
     */
    public Task(int id, String title, LocalDateTime start, LocalDateTime end,
                int interval) {
        this.id = id;
        this.title = title;
        if (start == null) {
            throw new IllegalArgumentException("Start time can't be negative");
        }
        this.start = start;
        if (end == null) {
            throw new IllegalArgumentException("End time can't be negative");
        }
        this.end = end;
        if (interval < 0) {
            throw new IllegalArgumentException("Interval time "
                    + "can't be negative");
        }
        this.interval = interval;
        repeated = true;
        active = false;
    }

    /**
     * Getter for the title variable.
     *
     * @return title - get the title of task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title variable.
     *
     * @param title - set the title of task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the status task: active or isn't active.
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for the status task: active or isn't active.
     *
     * @param active - set boolean type
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter for the status: repeative or nonrepeative task.
     *
     * @return repeated
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * Setter for the status: repeative or nonrepeative task.
     *
     * @param repeated - set boolean type
     */
    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    /**
     * Getter for get id of the task
     *
     * @return int - return the number
     */
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter time for the nonrepeative task.
     *
     * @return LocalDateTime - get the whole time
     */
    public LocalDateTime getTime() {
        if (this.repeated) {
            return start;
        } else {
            return time;
        }
    }

    /**
     * Setter time for the nonrepeative task.
     *
     * @param time - set time for task
     */
    public void setTime(LocalDateTime time) {
        if (this.repeated) {
            this.repeated = false;
        }
        this.time = time;
    }

    /**
     * Getters start, end and interval for repeative task.
     *
     * @return LocalDateTime - get the whole time or start time
     */
    public LocalDateTime getStartTime() {
        if (this.repeated) {
            return start;
        } else {
            return time;
        }
    }

    /**
     * Getter for end time of task
     *
     * @return LocalDateTime - get end time for repeated or the whole time for
     * no repeated task
     */
    public LocalDateTime getEndTime() {
        if (this.repeated) {
            return end;
        } else {
            return time;
        }
    }

    /**
     * Getter for interval value
     *
     * @return int - get interval of task or get 0 if this not repeated
     */
    public int getRepeatInterval() {
        if (this.repeated) {
            return interval;
        } else {
            return 0;
        }
    }

    /**
     * Setter for repeative task.
     *
     * @param start    - set start time
     * @param end      - set end time
     * @param interval - set interval
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if ((start == null) || (end == null) || (interval < 0)) {
            throw new IllegalArgumentException("These values can't be "
                    + "negative. They must be positive");
        }
        if (!this.repeated) {
            this.repeated = true;
        } else {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
    }

    /**
     * Public method that returns int value.
     *
     * @param current - value of pointed time
     * @return tempTime - get the time for repeated task
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null) {
            throw new IllegalArgumentException("Wrong value for the date");
        } else if (!isActive()) {
            return null;
        } else if ((!isRepeated()) && (isActive())
                && (getTime().isAfter(current))) {
            return getTime();
        } else if ((isRepeated()) && (isActive())
                && (getEndTime().isAfter(current))) {
            LocalDateTime tempTime = getStartTime();
            while (!tempTime.isAfter(getEndTime())) {
                if (tempTime.isAfter(getEndTime())) {
                    return null;
                } else if (current.isBefore(tempTime)) {
                    return tempTime;
                }
                tempTime = tempTime.plusSeconds(getRepeatInterval());
            }
            if (current.isAfter(getEndTime())) {
                return null;
            }
        }
        return null;
    }

    /**
     * The toString() returns a string description of this instance.
     */
    @Override
    public String toString() {
        if (!this.repeated) {
            if (!this.active) {
                return "Id: " + id + "[The nonrepeative task " + "(" + title + ")\n"
                        + "was completed at " + time + " time]\n";
            } else {
                return "Id: " + id +  "[The nonrepeative task " + "(" + title + ")\n"
                        + "is active and gonna end at " + time + " time]\n";
            }
        } else if (!this.active) {
            return "Id: " + id +  "[The repeative task " + "(" + title + ")\n"
                    + "was started at " + start + " and ended at " + end
                    + " with interval = " + interval + " in minutes" + "]\n";
        } else {
            return "Id: " + id +  "[The repeative task " + "(" + title + ")\n"
                    + "is active, it starts at " + start
                    + " and gonna end at " + end
                    + " with interval = " + interval + " in minutes" + "]\n";
        }
    }

    /**
     * Override hashCode() method from Object class.
     *
     * @return - int number based on 31 * titleTask(in hashCode)
     */
    @Override
    public int hashCode() {
        int result = 31;
        return this.getTitle().hashCode() * result;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if ((obj == null) ||
//                (obj.getClass() != this.getClass()))
//            return false;
//        Task task = (Task) obj;
//        return ((task.title.equals(this.title))
//                && (task.time == this.time));
//    }

    /**
     * Override equals() method from Object class.
     *
     * @param o - object of the class
     * @return - get true if this o is the object of Task class
     * elsewhere get false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return interval == task.interval
                && active == task.active
                && repeated == task.repeated
                && Objects.equals(title, task.title)
                && Objects.equals(time, task.time)
                && Objects.equals(start, task.start)
                && Objects.equals(end, task.end);
    }

    /**
     * Override clone() method.
     * Shallow cloning of the class Task.
     *
     * @return - copy of the class
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}
