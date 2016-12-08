package nz.co.thescene.client.auth.grants;

public final class PasswordGrant extends Grant {

	private final String username;
	
	private final String password;
	
	public PasswordGrant(String username, String password) { 
		this.username = username;
		this.password = password;
	}
	
	@Override
	public final String getUri() {
		String uri = "/oauth/token?grant_type=password&client_id=live-test&username="
				+ username + "&password="
				+ password;
		return uri;
	}

}
