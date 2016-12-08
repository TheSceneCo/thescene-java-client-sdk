package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("profileType")
public class ProfileTypeResource extends ResourceSupport {

	private String profileTypeName;

	@JsonCreator
	public ProfileTypeResource(@JsonProperty("profileTypeName") String profileTypeName) {
		this.profileTypeName = profileTypeName;
	}
	
	public String getProfileTypeName() { 
		return this.profileTypeName;
	}

	public void setProfileTypeName(String profileTypeName) { 
		this.profileTypeName = profileTypeName;
	}
	
	public String toString() { 
		return profileTypeName;
	}
	
}
