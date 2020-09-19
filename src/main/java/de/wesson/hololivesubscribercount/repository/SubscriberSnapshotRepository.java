package de.wesson.hololivesubscribercount.repository;

import de.wesson.hololivesubscribercount.model.hololive.SubscriberSnapshot;
import de.wesson.hololivesubscribercount.model.hololive.SubscriberSnapshotPK;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubscriberSnapshotRepository extends PagingAndSortingRepository<SubscriberSnapshot, SubscriberSnapshotPK> {

    List<SubscriberSnapshot> getAllByChannelID(String channelID);

    List<SubscriberSnapshot> getAllByChannelIDAndSnapshotTimeBetween(String channelID, long from, long to);

    List<SubscriberSnapshot> getAllByChannelIDAndSnapshotTimeBefore(String channelID, long time);

    List<SubscriberSnapshot> getAllByChannelIDAndSnapshotTimeAfter(String channelID, long time);
}
