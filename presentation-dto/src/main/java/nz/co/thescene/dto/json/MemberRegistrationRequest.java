package nz.co.thescene.dto.json;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("member")
public class MemberRegistrationRequest {

	@NotEmpty
	@Size(max = 100)
	private String firstName;

	@NotEmpty
	@Size(max = 100)
	private String lastName;

	@Email
	private String email;

	@NotEmpty
	@Size(min = 8, max = 256)
	private String password;

	@Size(max = 20)
	private String phoneNumber;

	public MemberRegistrationRequest() { 
		
	}
	
	@JsonCreator
	public MemberRegistrationRequest(@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName, @JsonProperty("email") String email,
			@JsonProperty("password") String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	/*
	 * public MemberDTO withSefRef() {
	 * add(linkTo(methodOn(MemberController.class).getPerson(memberId)).
	 * withSelfRel()); return this; }
	 * 
	 * public MemberDTO withRel(String rel) {
	 * add(linkTo(methodOn(MemberController.class).getPerson(memberId)).withRel(
	 * rel)); return this; }
	 */
	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String toString() {
		return "ToString.. [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + "]";
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
