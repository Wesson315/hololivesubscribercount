package de.wesson.hololivesubscribercount.model.youtube;

import de.wesson.hololivesubscribercount.HololivesubscribercountApplication;
import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import de.wesson.hololivesubscribercount.model.util.DateUtil;
import de.wesson.hololivesubscribercount.model.youtube.wrapper.Thumbnails;
import de.wesson.hololivesubscribercount.model.youtube.wrapper.YoutubeChannelListResponse;
import de.wesson.hololivesubscribercount.model.youtube.wrapper.YoutubeChannelResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class YoutubeAPI {

    private static final String TOKEN_API = "[API_KEY]";
    private static final String TOKEN_CHANNEL = "[CHANNEL_ID]";

    private static final String CALL ="https://content.googleapis.com/youtube/v3/channels?part=statistics,snippet&id="+TOKEN_CHANNEL+"&key="+TOKEN_API;


    /**
     * Calls the Youtube API with the specified parameters
     * Gets the statistic and the snippet.
     * @param apiKey
     * @param channelID
     * @return
     */
    public YoutubeChannelListResponse call(String apiKey, String channelID){
        String preparedCall = CALL.replace(TOKEN_API, apiKey).replace(TOKEN_CHANNEL, channelID);
        // In case I need to add something in the future, make it a function
        RestTemplate template = buildRestTemplate();
        YoutubeChannelListResponse response = template.getForObject(preparedCall, YoutubeChannelListResponse.class);
        return response;

    }

    public void updateTalent(HololiveTalent talent){
        String apiKey = HololivesubscribercountApplication.getApiKey();//Settings.getInstance().getApiKey();
        String channelID = talent.getChannelID();
        YoutubeChannelListResponse response = call(apiKey,channelID);
        for(YoutubeChannelResponse channel : response.getItems()){
            if(channel.getId().equalsIgnoreCase(talent.getChannelID())){
                String thumbnailID = getBiggestAvailableThumbnailUrl(channel.getSnippet().getThumbnails());
                long videoCount = Long.parseLong(channel.getStatistics().getVideoCount());
                long viewCount = Long.parseLong(channel.getStatistics().getViewCount());
                long subCount = Long.parseLong(channel.getStatistics().getSubscriberCount());
                String publishedAt = DateUtil.formatRFC3339((channel.getSnippet().getPublishedAt()));
                // Comment when refetching avatars
                talent.setThumbnailID(getThumbnailPath(talent.getChannelID()));
                // Uncomment when fetching avatars
               // talent.setThumbnailID(thumbnailID);
                talent.setVideoCount(videoCount);
                talent.setViewCount(viewCount);
                talent.setCreationDate(publishedAt);
                talent.setSubscriberCount(subCount);
            }
        }


    }



    public String getThumbnailPath(String channelID) {
        return "data/avatars/"+channelID+".png";
    }

    /**
     * Builds a rest Template
     * @return
     */
    private RestTemplate buildRestTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.build();
    }

    private static String getBiggestAvailableThumbnailUrl(Thumbnails thumbnails) {
        if (thumbnails.getMaxres() != null) return thumbnails.getMaxres().getUrl();
        if (thumbnails.getHigh() != null) return thumbnails.getHigh().getUrl();
        return thumbnails.getDefault().getUrl();
    }

}

