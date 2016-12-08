package nz.co.thescene.dto.json.hal;


import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("member")
public class MemberResource extends ResourceSupport {

	public static final class Rels { 
		public final static String EVENTS = "events";
		public static final String GRANTS = "grants";
		public static final String FOLDERS = "folders";
		public static final String IMAGE_META_INFO = "imageMetaInfo";
		public static final String IMAGE = "actualImage";
		public static final String SELF = "self";
		public static final String MEMBERS = "members";
		public static final String MEMBER = "member";
		public static final String THREADS = "threads";
	}

	private String firstName;

	private String lastName;

	private String email;

	private String phoneNumber;
	
	private String memberId;

	private String photo;
	
	/* This property is only here as a convinience to avoid the client having to
	 * make several extra requests */
	private String imagePath;
	
	public MemberResource() {

	}

	@JsonCreator
	public MemberResource(@JsonProperty("memberId") String memberId, @JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName, @JsonProperty("email") String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.memberId = memberId;
	}
	
	public void setImagePath(String imagePath) { 
		this.imagePath = imagePath;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getMemberId() {
		return memberId;
	}

	public String toString() {
		return firstName + " " + lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getPhoneNumber() { 
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) { 
		this.phoneNumber = phoneNumber;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean hasImage() {
		return super.hasLink(Rels.IMAGE);
	}
}
