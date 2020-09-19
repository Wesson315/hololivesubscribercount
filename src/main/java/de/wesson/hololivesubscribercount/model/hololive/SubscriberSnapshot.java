package de.wesson.hololivesubscribercount.model.hololive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@IdClass(SubscriberSnapshotPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberSnapshot {
    @Id
    private String channelID;
    @Id
    private long snapshotTime;
    private long subscriberCount;








}
