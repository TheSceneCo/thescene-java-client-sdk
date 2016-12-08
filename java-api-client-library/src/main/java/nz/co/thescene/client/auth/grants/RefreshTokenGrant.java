package nz.co.thescene.client.auth.grants;

public class RefreshTokenGrant extends Grant {

	private final String refreshToken;
	
	public RefreshTokenGrant(String refreshToken) { 
		this.refreshToken = refreshToken;
	}
	
	@Override
	public String getUri() {
		String uri = "/oauth/token?grant_type=refresh_token&refresh_token="
				+ refreshToken;
		return uri;
	}

}
