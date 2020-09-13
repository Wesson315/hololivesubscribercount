package de.wesson.hololivesubscribercount.model.youtube.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Snippet {
    private String title;
    private String description;
    private String publishedAt;
    private Thumbnails thumbnails;
    private Localized localized;
    private String country;
}
