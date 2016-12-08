package nz.co.thescene.dto.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("profilePhoto")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfilePhotoRequest {

    @JsonProperty(required = true)
    private String imageId;


    public ProfilePhotoRequest() {
    }

    @JsonCreator
    public ProfilePhotoRequest(@JsonProperty("imageId") String imageId) {
        this.imageId = imageId;
    }

    public String getImageMetaInfo() {
        return imageId;
    }

    public void setImageMetaInfo(String imageId) {
        this.imageId = imageId;
    }
}
