package nz.co.thescene.dto.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("grant")
public class GrantRequest {

	private String memberId;
	
	private String grantedAuthorityId;
	
	@JsonCreator
	public GrantRequest(@JsonProperty("memberId") String memberId, @JsonProperty("grantedAuthorityId") String grantedAuthorityId) { 
		this.memberId = memberId;
		this.grantedAuthorityId = grantedAuthorityId;
	}
	
	public String getMemberId() { 
		return memberId;
	}
	
	public String getGrantedAuthorityId() { 
		return grantedAuthorityId;
	}
	
	public void setMemberId(String memberId) { 
		this.memberId = memberId;
	}
	
	public void setGrantedAuthorityId(String grantedAuthorityId) { 
		this.grantedAuthorityId = grantedAuthorityId;
	}
}
