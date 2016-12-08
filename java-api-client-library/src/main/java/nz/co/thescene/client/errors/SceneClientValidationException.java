package nz.co.thescene.client.errors;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties
public class SceneClientValidationException extends SceneClientException {
	
	private String timestamp;
	
	private String status;
	
	private String error;
	
	private String exception;
	
	private Error[] errors;
	
	private String message;
	
	private String path;
	
}
