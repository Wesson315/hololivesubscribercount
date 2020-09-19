package de.wesson.hololivesubscribercount.model.timer.runnables;

import de.wesson.hololivesubscribercount.controller.SnapshotController;
import de.wesson.hololivesubscribercount.controller.TalentController;
import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import de.wesson.hololivesubscribercount.model.youtube.YoutubeAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MetadataRefresh implements Runnable {


    private TalentController talentController;
    private SnapshotController snapshotController;
    private Logger logger = LoggerFactory.getLogger(MetadataRefresh.class);
    public MetadataRefresh(TalentController talentController, SnapshotController snapshotController) {
        this.talentController = talentController;
        this.snapshotController = snapshotController;
    }

    @Override
    public void run() {
        logger.info("Refreshing subscriber count");
        List<HololiveTalent> allTalents = talentController.getAllTalents();
        ExecutorService ex = Executors.newCachedThreadPool();
        for (HololiveTalent talent : allTalents) {
            ex.submit(getTask(talent));
        }

        ex.shutdown();

    }

    protected Runnable getTask(HololiveTalent talent) {
        if (talent == null) return new EmptyRunnable();
        return () -> {
            try {
                new YoutubeAPI().updateTalent(talent);
                talentController.addOrUpdateTalent(talent);
                snapshotController.takeSnapshot(talent);
                logger.debug(String.format("Updated Talent %s", talent.getIdolName()));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Error updating talent " + talent.getIdolName(), e);
            }
        };
    }
}
