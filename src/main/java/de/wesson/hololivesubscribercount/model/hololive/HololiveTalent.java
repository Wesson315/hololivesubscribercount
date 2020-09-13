package de.wesson.hololivesubscribercount.model.hololive;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;


/**
 * Describes a Channel of a Hololive talent.
 **/
@Data
@NoArgsConstructor
@Entity
public class HololiveTalent {


    @Id
    private String channelID;
    private String channelName;
    private String idolName;
    private String thumbnailID;
    private String creationDate;
    private long subscriberCount;
    private long viewCount;
    private long videoCount;


    public static ArrayList<HololiveTalent> talents = new ArrayList<>();

    public HololiveTalent(String channelID, String channelName, String idolName) {
        this.channelID = channelID;
        this.channelName = channelName;
        this.idolName = idolName;
        if(channelID.startsWith("U"))
        talents.add(this);
    }





}
