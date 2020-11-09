package controller;

import model.Task;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BaseController {

    /**
     * Adding logger to the class.
     */
    protected Logger LOGGER;

    /**
     * Instance of list.
     */
    protected CopyOnWriteArrayList<Task> listOfTasks;

    /**
     * Instance of scanner reader.
     */
    protected BufferedReader bufReader;

}
