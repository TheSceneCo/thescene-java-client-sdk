package nz.co.thescene.client.clients;

import lombok.Data;
import nz.co.thescene.client.errors.SceneClientException;

@Data
public class SceneClientUnauthorizedException extends SceneClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String error;

	private String error_description;
}
