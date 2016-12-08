package nz.co.thescene.client.auth.grants;

import nz.co.thescene.client.SceneClient;

public final class ClientCredentialsGrant extends Grant {

	@Override
	public String getUri() {

		if (SceneClient.getClientContext().getClientId() == null || SceneClient.getClientContext().getClientSecret() == null) { 
			throw new IllegalArgumentException("Client ID and Client Secret are not set");
		}

		return "/oauth/token?grant_type=client_credentials&client_id=" + SceneClient.getClientContext().getClientId() + "&client_secret=" + SceneClient.getClientContext().getClientSecret();
		
	}
	
}
