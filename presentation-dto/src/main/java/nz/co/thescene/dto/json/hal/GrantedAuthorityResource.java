package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("grantedAuthority")
public class GrantedAuthorityResource extends ResourceSupport {

	private String grantedAuthorityId; 
	
	private String grantedAuthorityName;
	
	@JsonCreator
	public GrantedAuthorityResource(@JsonProperty("grantedAuthorityId") String grantedAuthorityId, @JsonProperty("grantedAuthorityName") String grantedAuthorityName)  {
		this.grantedAuthorityId = grantedAuthorityId;
		this.grantedAuthorityName = grantedAuthorityName;
	}
	
	public String getGrantedAuthorityId() { 
		return grantedAuthorityId;
	}
	
	public String getGrantedAuthorityName() { 
		return grantedAuthorityName;
	}
	
	@Override
	public String toString() {
		return grantedAuthorityName;
	}
	
}
