package nz.co.thescene.client.auth;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.auth.grants.Grant;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class TokenRetrievalService {
	
	private static AuthenticationResponse authenticationResponse;
	
	public static AuthenticationResponse getToken(Grant grant) { 
		
		String credential = Credentials.basic(SceneClient.getClientContext().getClientId(),
				SceneClient.getClientContext().getClientSecret());
		
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "");
		
		Request request = new Request.Builder().url(SceneClient.getClientContext().getBaseUrl() + grant.getUri()).header("Authorization", credential).post(requestBody).build();
		grant = null;
		
		OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

		Response response = null;

		try {
			response = okHttpClient.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parseAuthenticationResponse(response);
		
	}
	
	public static AuthenticationResponse parseAuthenticationResponse(Response response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			authenticationResponse = mapper.readValue(response.body().bytes(), AuthenticationResponse.class);
			
			if (authenticationResponse.getStatus() != null) { 
				log.error(authenticationResponse.getStatus());
				log.error(authenticationResponse.getTimestamp());
				log.error(authenticationResponse.getException());
				log.error(authenticationResponse.getMessage());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authenticationResponse;
	}

}
