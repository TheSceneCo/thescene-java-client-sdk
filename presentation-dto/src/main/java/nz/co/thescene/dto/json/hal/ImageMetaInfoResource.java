package nz.co.thescene.dto.json.hal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("imageMetaInfo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageMetaInfoResource extends FileResource {

    public static class Rels {
    	public static final String IMAGE = "image";
    }

	public ImageMetaInfoResource() {
    }

    @JsonCreator
    public ImageMetaInfoResource(@JsonProperty("sceneId") String fileId, @JsonProperty("name")String name) {
        super(fileId, name, "IMAGE");
    }

}
