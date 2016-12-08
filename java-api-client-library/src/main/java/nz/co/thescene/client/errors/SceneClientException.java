package nz.co.thescene.client.errors;

import lombok.Data;

@Data
public class SceneClientException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int responseStatus;

	@Override
	public String toString() { 
		return "" + responseStatus;
	}
	
}
