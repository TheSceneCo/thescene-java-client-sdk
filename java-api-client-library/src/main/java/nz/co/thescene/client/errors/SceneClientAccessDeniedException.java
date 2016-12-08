package nz.co.thescene.client.errors;

import lombok.Data;

@Data
public class SceneClientAccessDeniedException extends SceneClientException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String error;
	
	private String error_description;

}
