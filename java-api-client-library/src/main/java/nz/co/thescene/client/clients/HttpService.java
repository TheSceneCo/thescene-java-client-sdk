package nz.co.thescene.client.clients;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpMethod.*;

import nz.co.thescene.client.APIClientContext;
import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientAccessDeniedException;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.client.errors.SceneClientValidationException;
import nz.co.thescene.client.traverson.Traverson;
import nz.co.thescene.client.traverson.Traverson.TraversalBuilder;
import nz.co.thescene.dto.json.hal.ApiResource;
import nz.co.thescene.dto.json.hal.ImageMetaInfoResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class HttpService {

	private final SimpleEntry<String, String> HAL_JSON_ACCEPT_TYPE = new SimpleEntry<>("Accept",
			"application/hal+json");

	private final SimpleEntry<String, String> JSON_CONTENT_TYPE = new SimpleEntry<>("Content-Type", "application/json");

	private final SimpleEntry<String, String> MULTIPART_CONTENT_TYPE = new SimpleEntry<>("Content-Type",
			"multipart...");

	// headers.add("Accept", "application/hal+json");

	public ApiResource discover() {
		APIClientContext clientContext = SceneClient.getClientContext();
		ApiResource cachedResource = (ApiResource) clientContext.getCachedResource(clientContext.getBaseUrlLink());
		if (cachedResource != null) {
			return cachedResource;
		} else {
			ApiResource apiResource = get(ApiResource.class, SceneClient.getClientContext().getBaseUrlLink());
			clientContext.cacheResource(clientContext.getBaseUrlLink(), apiResource);
			return apiResource;
		}
	}

	private <T> T get(Class<T> returnType, Link link, String... hops) {
		T response = null;
		try {
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return get(returnType, link, hops);
			}
		}
		return response;
	}

	// FIXME: No exception handling here
	public MemberResource memberInfo() {
		ApiResource discover = null;
		try {
			discover = discover();
		} catch (UnsupportedOperationException e) {
			throw new UnsupportedOperationException(
					"You cannot use the memberinfo endpoint without a password grant. Register a member which you can use to obtain a password grant.",
					e);
		}

		Link memberInfoLink = discover.getLink("memberinfo");
		if (memberInfoLink != null) {
			TraversalBuilder traversalBuilder = configureTraversalBuilder(memberInfoLink, new String[] {});
			return traversalBuilder.toObject(MemberResource.class);
		} else {
			return null;
		}
	}

	/**
	 * Send an HTTP GET request with request parameters to get multiple objects. 
	 * @param returnType The type of object that should be returned.
	 * @param startResource The resource containing the start url. 
	 * @param parameters The request request parameters for this request. 
	 * @param startRel The reference to the start url in the containing resource. 
	 * @param hops The references to other hops that should be made in this request sequence. 
	 * @return
	 */
	public <T> T get(ParameterizedTypeReference<T> returnType, ResourceSupport startResource,
			Map<String, Object> parameters, String startRel, String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel, parameters);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return get(returnType, startResource, startRel, hops);
			}
		}
		return response;
	}

	/**
	 * Send an HTTP GET request without any request parameters to get multiple objects.
	 * @param returnType
	 * @param startResource
	 * @param startRel
	 * @param hops
	 * @return
	 */
	public <T> T get(ParameterizedTypeReference<T> returnType, ResourceSupport startResource, String startRel,
			String... hops) {
		return get(returnType, startResource, null, startRel, hops);
	}

	/**
	 * Get a single resource with parameters. 
	 * @param returnType
	 * @param startResource
	 * @param startRel
	 * @param hops
	 * @return
	 */
	public <T> T get(Class<T> returnType, ResourceSupport startResource, Map<String, Object> parameters, String startRel, String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel, parameters);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return get(returnType, startResource, startRel, hops);
			}
		}
		return response;
	}
	
	public BufferedImage getImage(ImageMetaInfoResource imageMetaInfo) throws MalformedURLException, IOException { 
		Link link = imageMetaInfo.getLink(ImageMetaInfoResource.Rels.IMAGE);
		String t = "s";
		URL imageURL = new URL(link.getHref());
		URLConnection openConnection = imageURL.openConnection();
		openConnection.setRequestProperty("Authorization",  "Bearer " + SceneClient.getClientContext().getAccessToken());
		openConnection.setRequestProperty("Accept", "image/jpeg");
		//headers.add("Authorization", "Bearer " + token);
		BufferedImage image = ImageIO.read(openConnection.getInputStream());
		return image;
		
		/*String credential = Credentials.basic(SceneClient.getClientContext().getClientId(),
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
*/
		
	}
	
	/**
	 * Get a single resource without parameters.
	 * @param returnType
	 * @param startResource
	 * @param startRel
	 * @param hops
	 * @return
	 */
	public <T> T get(Class<T> returnType, ResourceSupport startResource, String startRel, String... hops) { 
		return get(returnType, startResource, null, startRel, hops);
	}
	
	public <T, D> T post(ParameterizedTypeReference<T> returnType, ResourceSupport startResource, String startRel,
			D payload, String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(POST, payload, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return post(returnType, startResource, startRel, payload, hops);
			}
		}
		return response;
	}

	public <T, D> T postToBaseUrl(ParameterizedTypeReference<T> returnType, D payload, String... hops) {
		Link rel = SceneClient.getClientContext().getBaseUrlLink();
		T response = null;
		try {
			TraversalBuilder traversalBuilder = configureTraversalBuilder(rel, hops);
			response = traversalBuilder.toObject(POST, payload, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return postToBaseUrl(returnType, payload, hops);
			}
		}
		return response;
	}

	public <T, D> Resources<T> postFiles(
			ParameterizedTypeReference<Resources<T>> returnType, ResourceSupport startResource,
			String startRel, MultiValueMap<String, Object> files, String... hops) {
		
		//LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		//map.add(fileName, new ClassPathResource(fileName));

		// Set Content-Type header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		// Set Accept header
		List<MediaType> acceptTypes = new ArrayList<MediaType>();
		acceptTypes.add(MediaTypes.HAL_JSON);
		headers.setAccept(acceptTypes);

		Link link = checkLink(startResource, startRel);
		TraversalBuilder traversalBuilder = configureTraversalBuilder(link, headers, hops);

		Resources<T> returnedFileMetaInfo = null;
		
		try { 
			returnedFileMetaInfo = traversalBuilder.toObject(POST, files, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) { 
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) { 
				SceneClient.getClientContext().refreshAccessToken();
				return postFiles(returnType, startResource, startRel, files, hops);
			}
		}
		
		return returnedFileMetaInfo;
	}

	public <T, D> T post(Class<T> returnType, ResourceSupport startResource, String startRel, D payload,
			String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(POST, payload, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return post(returnType, startResource, startRel, payload, hops);
			}
		}
		return response;
	}

	public <T, D> T put(Class<T> returnType, ResourceSupport startResource, String startRel, D payload,
			String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(PUT, payload, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return put(returnType, startResource, startRel, payload, hops);
			}
		}
		return response;

	}

	public <T, D> T put(ParameterizedTypeReference<T> returnType, ResourceSupport startResource, String startRel,
			D payload, String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(PUT, payload, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return put(returnType, startResource, startRel, payload, hops);
			}
		}
		return response;
	}
	
	/**
	 * Sends a request sequence where the final 'hop' is an HTTP DELETE request with a request body.   
	 * @param returnType The object type to return.
	 * @param startResource The resource to take the start link from.
	 * @param startRel The 'rel' reference to the start link in the start resource. 
	 * @param payload The request body to send with this request. 
	 * @param hops subsequent to the start of this request sequence. 
	 * @return The single object that was deleted. 
	 */
	public <T, D> T delete(Class<T> returnType, ResourceSupport startResource, String startRel, D payload,
			String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(DELETE, payload, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return delete(returnType, startResource, startRel, payload, hops);
			}
		}
		return response;
	}

	/**
	 * Sends a request sequence where the final 'hop' is an HTTP DELETE request with parameters. 
	 * @param returnType
	 * @param startResource
	 * @param startRel
	 * @param hops
	 * @return
	 */
	public <T, D> T delete(Class<T> returnType, Map<String, Object> parameters, ResourceSupport startResource, String startRel, String... hops) {
		T response = null;
		try {
			Link link = checkLink(startResource, startRel, parameters);
			TraversalBuilder traversalBuilder = configureTraversalBuilder(link, hops);
			response = traversalBuilder.toObject(DELETE, returnType);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			boolean tokenExpired = parseErrorResponse(e);
			if (tokenExpired) {
				SceneClient.getClientContext().refreshAccessToken();
				return delete(returnType, startResource, startRel, hops);
			}
		}
		return response;
	}
	
	/**
	 * Sends a request sequence where the final 'hop' is an HTTP delete request without parameters. 
	 * @param returnType
	 * @param startResource
	 * @param startRel
	 * @param hops
	 * @return
	 */
	public <T, D> T delete(Class<T> returnType, ResourceSupport startResource, String startRel, String... hops) {
		return delete(returnType, null, startResource, startRel, hops);
	}
	

	private Link checkLink(ResourceSupport resource, String rel) { 
		return checkLink(resource, rel, null);
	}
	
	private Link checkLink(ResourceSupport resource, String rel, Map<String, Object> parameters) {
		
		Link link = resource.getLink(rel);
		if (link == null) {
			log.error(resource.getClass().getSimpleName() + " does not have link " + rel);
			throw new SceneClientException();
		}
		
		if (UriTemplate.isTemplate(link.getHref())) {
			link = new Link(resource.getLink(rel).getHref(), rel);
			if (parameters == null) { 
				link = getBaseUrl(link);
			} else { 
				link = link.expand(parameters);
			}
		} 
		return link;
	}

	// JD: This seems a bit hacky. Basically - template parameters are not valid urls and cause problems so we 
	// can't send requests using them. If we have no parameters to send, we need to take the parameters off of the url
	// so we can still use the url for our request. I wonder if the framework provides a better solution than this
	// and I just haven't figured it out. 
	// I should ask the question at the spring-hateoas-ext project.
	private Link getBaseUrl(Link link) {
		Pattern VARIABLE_REGEX = Pattern.compile("\\{([\\?\\&#/]?)([\\w\\,]+)\\}");
		String template = link.getHref();
		Matcher matcher = VARIABLE_REGEX.matcher(template);
		
		// find where the template parameters start and remove them
		while (matcher.find()) {
			int start = matcher.start(0);
			template = template.substring(0, start);
		}

		link = new Link(template);
		return link;
	}
	
	/**
	 * Configures a TraversalBuilder with default headers.
	 * 
	 * @param startUrl
	 * @param hops
	 * @return
	 */
	private TraversalBuilder configureTraversalBuilder(Link startUrl, String... hops) {
		// Set Content-Type header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Set Accept header
		List<MediaType> acceptTypes = new ArrayList<MediaType>();
		acceptTypes.add(MediaTypes.HAL_JSON);
		headers.setAccept(acceptTypes);
		return configureTraversalBuilder(startUrl, headers, hops);
	}

	private TraversalBuilder configureTraversalBuilder(Link startUrl, HttpHeaders headers, String... hops) {

		TraversalBuilder traversalBuilder = null;

		if (startUrl == null) {
			throw new IllegalArgumentException("baseUrl cannot be " + startUrl);
		}

		String token = SceneClient.getClientContext().getAccessToken();

		try {
			Traverson traverson = new Traverson(new URI(startUrl.getHref()), MediaTypes.HAL_JSON);
			traversalBuilder = traverson.follow(hops);
			headers.add("Authorization", "Bearer " + token);
			headers.add("Accept", "application/hal+json");
			traversalBuilder.withHeaders(headers);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return traversalBuilder;
	}

	private boolean parseErrorResponse(HttpStatusCodeException e) {
		int value = e.getStatusCode().value();
		if (value == 403) {
			SceneClientAccessDeniedException accessDeniedException = new Gson().fromJson(e.getResponseBodyAsString(),
					SceneClientAccessDeniedException.class);
			accessDeniedException.setResponseStatus(403);
			accessDeniedException.initCause(e);
			throw accessDeniedException;
		} else if (value == 401) {
			SceneClientUnauthorizedException unauthorizedException = new Gson().fromJson(e.getResponseBodyAsString(),
					SceneClientUnauthorizedException.class);
			unauthorizedException.setResponseStatus(401);
			unauthorizedException.initCause(e);
			boolean tokenExpired = unauthorizedException.getError().equals("invalid_token")
					&& unauthorizedException.getError_description().contains("Access token expired");
			if (tokenExpired) {
				return true;
			} else {
				throw unauthorizedException;
			}
		} else if (value == 400) {

			SceneClientException badRequestException = new Gson().fromJson(e.getResponseBodyAsString(),
					SceneClientValidationException.class);
			if (badRequestException != null) { 
				badRequestException.initCause(e);
				badRequestException.setResponseStatus(400);
			} else { 
				badRequestException = new SceneClientException();
				badRequestException.setResponseStatus(400);
			}
			throw badRequestException;
		} else {
			SceneClientException sceneClientException = new SceneClientException();
			sceneClientException.setResponseStatus(e.getStatusCode().value());
			sceneClientException.initCause(e);
			throw sceneClientException;
		}
	}
}
