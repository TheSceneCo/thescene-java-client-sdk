package nz.co.thescene.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import nz.co.thescene.client.auth.AuthenticationResponse;
import nz.co.thescene.client.auth.TokenRetrievalService;
import nz.co.thescene.client.auth.grants.ClientCredentialsGrant;
import nz.co.thescene.client.auth.grants.PasswordGrant;
import nz.co.thescene.client.auth.grants.RefreshTokenGrant;

/**
 * This class ensures there is only one API Client Context. The context stores
 * the endpoint url and any authentication tokens, refresh tokens etc that have
 * been retrieved.
 * 
 * @author John Deverall
 * @email john@thescene.co
 */
public final class APIClientContext {

	private volatile static APIClientContext uniqueInstance;

	private String baseUrl;

	private String clientId;

	private String clientSecret;

	private String accessToken;
	
	private String refreshToken;
	
	private Map<Link, ResourceSupport> cachedResources;
	
	private APIClientContext() {
		cachedResources = new HashMap<Link, ResourceSupport>();
	}

	/**
	 * Creates a new api client context. Subsequent calls to this method have no
	 * effect but will return the previously created api client context.
	 * 
	 * @return
	 * @author John Deverall
	 */
	static APIClientContext getInstance() {
		if (uniqueInstance == null) {
			synchronized (APIClientContext.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new APIClientContext();
				}
			}
		}
		return uniqueInstance;
	}

	/**
	 * Clears the API Client Context.
	 * 
	 * @return
	 */
	public void clearAll() {
		this.baseUrl = null;
		this.clientSecret = null;
		this.accessToken = null;
		this.clientId = null;
		this.clearCachedResources();
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}
	
	public Link getBaseUrlLink() { 
		return new Link(this.baseUrl, "self");
	}

	public APIClientContext setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		clearCachedResources();
		return this;
	}

	public String getClientId() {
		return this.clientId;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public APIClientContext setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		clearCachedResources();
		return this;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public APIClientContext setClientCredentials(String clientId, String clientSecret) { 
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		clearCachedResources();
		return this;
	}
	
	public void exchangeResourceOwnerCredentialsForAccessToken(String username, String password) {
		accessToken = null;
		refreshToken = null;
		AuthenticationResponse response = TokenRetrievalService.getToken(new PasswordGrant(username, password));
		accessToken = response.getAccess_token();
		refreshToken = response.getRefresh_token();
		clearCachedResources(); // our link options might change with new credentials
	}
	
	public void exchangeClientCredentialsForAccessToken() { 
		accessToken = null;
		AuthenticationResponse response = TokenRetrievalService.getToken(new ClientCredentialsGrant());
		accessToken = response.getAccess_token();
		clearCachedResources(); // our link options might change with new credentials
	}
	
	public void refreshAccessToken() {
		accessToken = null;
		AuthenticationResponse response = TokenRetrievalService.getToken(new RefreshTokenGrant(refreshToken));
		accessToken = response.getAccess_token();
	}

	public void clearAccessToken() {
		accessToken = null;
		clearCachedResources();
	}
	
	public boolean hasToken() { 
		return accessToken != null;
	}

	public <T> ResourceSupport getCachedResource(Link baseUrlLink) {
		return cachedResources.get(baseUrlLink);
	}
	
	public void cacheResource(Link link, ResourceSupport resource) {
		cachedResources.put(link, resource);
	}
	
	public void clearCachedResources() { 
		cachedResources.clear();
	}
	
	public void removeCachedResource(Link link) { 
		cachedResources.remove(link);
	}
}