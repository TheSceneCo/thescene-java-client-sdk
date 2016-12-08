package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("member")
public class GrantResource extends ResourceSupport {

	private String sceneId;
	
	private String grantedAuthorityName;

	@JsonCreator
	public GrantResource(@JsonProperty("sceneId") String sceneId, @JsonProperty("grantedAuthorityName") String grantedAuthorityName) {
		this.sceneId = sceneId;
		this.grantedAuthorityName = grantedAuthorityName;
	}
	
	public String getSceneId() { 
		return sceneId;
	}
	
	public String getGrantedAuthorityName() { 
		return grantedAuthorityName;
	}
	
	@Override
	public String toString() {
		return grantedAuthorityName;
	}

}
