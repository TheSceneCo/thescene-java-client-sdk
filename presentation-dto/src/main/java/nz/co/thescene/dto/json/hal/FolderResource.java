package nz.co.thescene.dto.json.hal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("folder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderResource extends FileResource {

	private static String type = "FOLDER";
	
	public static class Rels {
		public static String PARENT = "parent";
		public static String FOLDERS = "folders";
		public static String FILES = "files";
		public static String FILES_AND_FOLDERS = "filesAndFolders";
		public static String IMAGES = "images";
		public static String SELF = "self";
	};

	public FolderResource() {
	}

	@JsonCreator
	public FolderResource(@JsonProperty("sceneId") String fileId, @JsonProperty("name") String name) {
		super(fileId, name, type);
	}

	public boolean hasParent() {
		return hasLink(Rels.PARENT);
	}

	public boolean hasChildFolders() {
		return hasLink(Rels.FOLDERS.toString().toLowerCase());
	}

	public boolean hasImages() {
		return hasLink(Rels.IMAGES.toString().toLowerCase());
	}
}
