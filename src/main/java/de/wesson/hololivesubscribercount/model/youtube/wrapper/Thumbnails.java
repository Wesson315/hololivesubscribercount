package de.wesson.hololivesubscribercount.model.youtube.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnails {

    private Thumbnail default__;
    private Thumbnail medium;
    private Thumbnail high;
    private Thumbnail maxres;


    public Thumbnail getDefault() {
        return default__;
    }

    public void setDefault(Thumbnail default__) {
        this.default__ = default__;
    }
}
