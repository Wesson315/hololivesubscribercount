package de.wesson.hololivesubscribercount.model.timer.runnables;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Empty Runnable to prevent Null-Pointer exceptions
 */
public class EmptyRunnable implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(EmptyRunnable.class);
    @Override
    public void run() {
    logger.warn("Attention! An Empty Runnable was called!");
    }
}
