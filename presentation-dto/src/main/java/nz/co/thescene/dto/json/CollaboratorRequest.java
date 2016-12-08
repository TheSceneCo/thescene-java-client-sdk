package nz.co.thescene.dto.json;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("collaborator")
public class CollaboratorRequest {

	private String memberId;
	
	private String permissionList;
	
	private boolean publicCollaborator;
	
	public void setPermissionList(String permissionList) { 
		this.permissionList = permissionList;
	}
	
	public String getPermissionList() { 
		return this.permissionList;
	}
	
	public boolean getPublicCollaborator() { 
		return this.publicCollaborator;
	}
	
	public void setPublicCollaborator(boolean isPublicCollaborator) { 
		this.publicCollaborator = isPublicCollaborator;
	}
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
