package nz.co.thescene.client.auth;

import lombok.Data;

@Data
public class AuthenticationResponse {

	private String access_token;

	private String token_type;

	private String refresh_token;
	
	private String expires_in;
	
	private String scope;
	
	private String jti;
	
	private String error;
	
	private String error_description;
	
	/* Below properties are populated when something goes wrong */
	private String timestamp;
	
	private String status;
	
	private String exception;
	
	private String message;
	
	private String path;
	
}