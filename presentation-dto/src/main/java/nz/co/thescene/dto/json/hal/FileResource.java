package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("file")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileResource extends ResourceSupport {

	@JsonProperty(required = true)
	private String name;

	@JsonProperty(required = true)
	private String type;

	@JsonProperty(required = true)
	private String fileId;

	public FileResource() {
	}

	@JsonCreator
	public FileResource(@JsonProperty("sceneId")String fileId, @JsonProperty("name") String name,
			@JsonProperty("type") String type) {
		this.name = name;
		this.type = type;
		this.fileId = fileId;
	}
	
	public void setName(String name) { 
		this.name = name;
	}
	
	public void setType(String type) { 
		this.type = type;
	}
	
	public String getName() { 
		return name;
	}
	
	public String getType() { 
		return type;
	}
	
	public String getFileId() { 
		return fileId;
	}
	
	@Override
	public String toString() { 
		return type + "\t" + name;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileResource other = (FileResource) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		return true;
	}
	
}
