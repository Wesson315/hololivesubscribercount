package de.wesson.hololivesubscribercount.model.timer;

import de.wesson.hololivesubscribercount.controller.TalentController;
import de.wesson.hololivesubscribercount.model.timer.runnables.MetadataRefresh;
import it.sauronsoftware.cron4j.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Singleton to manage the two schedulers for subcount and meta data
 */
public class CronoManager {

    private static CronoManager instance;
    private Scheduler subcountScheduler, metaScheduler;
    private final Logger logger = LoggerFactory.getLogger(CronoManager.class);
    public static CronoManager getInstance() {
        if (instance == null)
            instance = new CronoManager();
        return instance;
    }

    private CronoManager() {

    }

    /**
     * Starts the two scheudlers.
     * The sub cound scheduler starts evers 20 minutes
     * The Metadatascheduler starts every day once at midnight GMT+1
     */
    public void startScheduler(TalentController controller) {
        logger.info("Starting Crono Schedulers!");
        if (subcountScheduler == null && metaScheduler == null) {
            // Sub Counter

            subcountScheduler = new Scheduler();
            String subCronoString = "0,20,40 * * * *";

            logger.info("Pattern for Subscriber Crono: " + subCronoString);

            subcountScheduler.schedule(subCronoString, new MetadataRefresh(controller));
            subcountScheduler.start();
/*
            // Metadata Counter
            metaScheduler = new Scheduler();
            String metaCronoString = "* 1 * * *";
            logger.info("Pattern for Meta Crono: " + metaCronoString);
            metaScheduler.schedule(metaCronoString, new MetadataRefresh(controller));
            metaScheduler.start();*/
            logger.info("Timers started successfully!");
        }else{
            logger.warn("Attempted to start new schedulers while the old ones have no been stopped!");
        }
    }

    public void stopScheduler() {
        if (subcountScheduler != null && metaScheduler != null) {
            subcountScheduler.stop();
            metaScheduler.stop();

            subcountScheduler = null;
            metaScheduler = null;
        }else{
            logger.warn("Attempted to stop schedulers while not having started them in the first place!");
        }
    }
}
