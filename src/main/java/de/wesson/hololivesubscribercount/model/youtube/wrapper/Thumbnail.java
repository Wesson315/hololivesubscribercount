package de.wesson.hololivesubscribercount.model.youtube.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnail {

    private String url;
    private int width, height;
}
