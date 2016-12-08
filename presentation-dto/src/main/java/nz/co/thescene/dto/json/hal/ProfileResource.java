package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import nz.co.thescene.dto.parameters.ProfileTypeParameter;

public abstract class ProfileResource extends ResourceSupport {

	public static final class Rels {
		public static final String COLLABORATORS = "collaborators";
		public static final String CONTENT_SECTIONS = "contentSections";
		public static final String CONTENT_SECTION_TEMPLATES = "contentSectionTemplates";
		public static final String IMAGE = "image";
		public static final String SELF = "self";
		public static final String PROFILE = "profile";
		public static final String TAGS = "tags";
		public static final String LOCATION = "location";
		public static final String OWNER = "owner";
		public static final String CREATOR = "creator";
		public static final String NEW = "new";
		public static final String CATEGORY = "categories";
	}

	private String profileId;
	private ProfileTypeParameter parameter;

	public ProfileResource(ProfileTypeParameter parameter){
		this.parameter = parameter;
	}

	public ProfileResource(String profileId, ProfileTypeParameter parameter) {
		this.profileId = profileId;
		this.parameter = parameter;
	}

	public boolean hasLocation() {
		return this.hasLink("location");
	}

	private String profilePhoto;

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	public ProfileTypeParameter getProfileTypeParameter() { 
		return parameter;
	}
}
