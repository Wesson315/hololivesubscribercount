package de.wesson.hololivesubscribercount.model.youtube.wrapper;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Bypassing the broken Youtube API
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeChannelListResponse {


    private String kind;
    private String etag;
    private Map<String,String>pageInfo;
    private List<YoutubeChannelResponse> items;


}
