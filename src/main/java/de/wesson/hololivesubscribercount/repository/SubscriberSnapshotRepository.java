package de.wesson.hololivesubscribercount.repository;

import de.wesson.hololivesubscribercount.model.hololive.snapshots.SubscriberSnapshot;
import de.wesson.hololivesubscribercount.model.hololive.snapshots.SubscriberSnapshotPK;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubscriberSnapshotRepository extends MongoRepository<SubscriberSnapshot, SubscriberSnapshotPK> {

    List<SubscriberSnapshot> getAllByChannelID(String channelID);

    List<SubscriberSnapshot> getAllByChannelIDAndSnapshotTimeBetween(String channelID, long from, long to);

    List<SubscriberSnapshot> getAllByChannelIDAndSnapshotTimeBefore(String channelID, long time);

    List<SubscriberSnapshot> getAllByChannelIDAndSnapshotTimeAfter(String channelID, long time);
}
