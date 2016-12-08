package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("collaborator")
public class CollaboratorResource extends ResourceSupport {

	public static final class Rels { 
		public static final String SELF = "self";
		public static final String IMAGE_META_INFO = "imageMetaInfo";
		public static final String IMAGE = "actualImage";
	}

	private String collaboratorId;
	
	private String memberId;
	
	private String profileId;
	
	private String permissionList;
	
	private boolean isPublicCollaborator;
	
	/* The email of the user with permission */
	private String email;
	
	private String firstName;
	
	private String lastName;

	private String phoneNumber;

	public void setFirstName(String firstName) { 
		this.firstName = firstName;
	}
	
	public String getFirstName() { 
		return this.firstName;
	}
	
	public void setLastName(String lastName) { 
		this.lastName = lastName;
	}
	
	public String getLastName() { 
		return this.lastName;
	}
	
	public String getPhoneNumber() { 
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) { 
		this.phoneNumber = phoneNumber;
	}
	
	public void setPermissionList(String permissionList) { 
		this.permissionList = permissionList;
	}
	
	public String getPermissionList() { 
		return this.permissionList;
	}
	
	public boolean isPublicCollaborator() { 
		return this.isPublicCollaborator;
	}
	
	public void setPublicCollaborator(boolean isPublicCollaborator) { 
		this.isPublicCollaborator = isPublicCollaborator;
	}
	
	public void setEmail(String email) { 
		this.email = email;
	}
	
	public String getEmail() { 
		return this.email;
	}

	public void setCollaboratorId(String collaboratorId) {
		this.collaboratorId = collaboratorId;
	}
	
	public String getCollaboratorId() { 
		return this.collaboratorId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public void setProfileId(String profileId) { 
		this.profileId = profileId;
	}
	
	public String getProfileId() { 
		return profileId;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
