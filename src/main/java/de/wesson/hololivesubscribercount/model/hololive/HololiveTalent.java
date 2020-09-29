package de.wesson.hololivesubscribercount.model.hololive;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;


/**
 * Describes a Channel of a Hololive talent.
 **/
@Data
@NoArgsConstructor
//@Entity
public class HololiveTalent {


   // @Id
    @MongoId
    private String channelID;
    private String channelName;
    private String idolName;
    private String thumbnailID;
    private String creationDate;
    private String twitterLink;
    private String wikiLink;
    private long subscriberCount;
    private long viewCount;
    private long videoCount;


    public static ArrayList<HololiveTalent> talents = new ArrayList<>();

    public HololiveTalent(String channelID, String channelName, String idolName, String twitterLink, String wikiLink) {
        this.channelID = channelID;
        this.channelName = channelName;
        this.idolName = idolName;
        this.twitterLink = twitterLink;
        this.wikiLink = wikiLink;
        if(channelID.startsWith("U"))
        talents.add(this);
    }





}
