package nz.co.thescene.dto.json;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("grantedAuthority")
public class GrantedAuthorityRequest {
	
	@NotNull
	private String grantedAuthorityName;
	
	public GrantedAuthorityRequest() {}
	
	@JsonCreator
	public GrantedAuthorityRequest(@JsonProperty("grantedAuthorityName") String grantedAuthorityName)  {
		this.grantedAuthorityName = grantedAuthorityName;
	}
	
	public String getGrantedAuthorityName() { 
		return grantedAuthorityName;
	}
	
	public void setGrantedAuthorityName(String grantedAuthorityName) { 
		this.grantedAuthorityName = grantedAuthorityName;
	}
	
	@Override
	public String toString() {
		return grantedAuthorityName;
	}

}
