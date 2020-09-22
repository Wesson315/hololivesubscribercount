package de.wesson.hololivesubscribercount.controller;


import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import de.wesson.hololivesubscribercount.model.hololive.snapshots.SubscriberSnapshot;
import de.wesson.hololivesubscribercount.model.util.DateUtil;
import de.wesson.hololivesubscribercount.repository.SubscriberSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SnapshotController {

    private Logger logger = LoggerFactory.getLogger(SnapshotController.class);

    @Autowired
    private SubscriberSnapshotRepository repository;

    @GetMapping("/subhist")
    public List<SubscriberSnapshot> getSubscriberHistory(@RequestParam(name = "channelID") String channelID) {
        return repository.getAllByChannelID(channelID);
    }

    @GetMapping("/subhist/before")
    public List<SubscriberSnapshot> getSubscriberHistoryBefore(@RequestParam(name = "channelID") String channelID, @RequestParam(name = "before") long time) {
        return repository.getAllByChannelIDAndSnapshotTimeBefore(channelID, time);
    }

    @GetMapping("/subhist/after")
    public List<SubscriberSnapshot> getSubscriberHistoryAfter(@RequestParam(name = "channelID") String channelID, @RequestParam(name = "after") long time) {
        return repository.getAllByChannelIDAndSnapshotTimeAfter(channelID, time);
    }

    @GetMapping("/subhist/between")
    public List<SubscriberSnapshot> getSubscriberHistoryBetween(@RequestParam(name = "channelID") String channelID, @RequestParam(name = "from") long from, @RequestParam(name = "to") long to) {
        return repository.getAllByChannelIDAndSnapshotTimeBetween(channelID, from, to);
    }

    public void takeSnapshot(HololiveTalent talent){
        SubscriberSnapshot snapshot = new SubscriberSnapshot(talent.getChannelID(), Instant.now().toEpochMilli(),talent.getSubscriberCount());
        repository.save(snapshot);
        logger.debug("Created Snapshot [{}] {} {}",talent.getIdolName(), DateUtil.format(snapshot.getSnapshotTime()), snapshot.getSubscriberCount());
    }
}
