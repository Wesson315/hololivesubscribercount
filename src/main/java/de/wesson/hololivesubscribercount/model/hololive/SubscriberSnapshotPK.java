package de.wesson.hololivesubscribercount.model.hololive;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubscriberSnapshotPK implements Serializable {

    private String channelID;
    private long snapshotTime;
}
