package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("location")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResource extends ResourceSupport {

	public static final class Rels {
		public static final String LOCATION = "location";
		public static final String SELF = "self";
		public static final String PROFILE = "profile";
	}
	
	private String latitude;
	
	private String longitude;
	
	private String googlePlaceId;
	
	private String sceneId;

	private String address;
	
	public LocationResource() {}
	
	public LocationResource(@JsonProperty("sceneId") String sceneId, @JsonProperty("longitude") String longitude,
							@JsonProperty("latitude") String latitude, @JsonProperty("googlePlaceId") String googlePlaceId,
							@JsonProperty("address") String address) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.googlePlaceId = googlePlaceId;
		this.sceneId = sceneId;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) { 
		this.latitude = latitude;
	}
	
	public String getLongitude() { 
		return longitude;
	}
	
	public void setLongitude(String longitude) { 
		this.longitude = longitude;
	}
	
	public String getGooglePlaceId() { 
		return googlePlaceId;
	}
	
	public void setGooglePlaceId(String googlePlaceId) { 
		this.googlePlaceId = googlePlaceId;
	}
	
	public String getSceneId() { 
		return sceneId;
	}
	
	public String toString() { 
		return "Location at ( " + latitude + ", " + longitude + ") with googlePlaceId: " + googlePlaceId + ", address " + address;
	}

}
