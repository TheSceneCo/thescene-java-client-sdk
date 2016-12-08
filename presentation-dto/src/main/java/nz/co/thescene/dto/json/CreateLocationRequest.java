package nz.co.thescene.dto.json;

import javax.validation.constraints.NotNull;

public class CreateLocationRequest {

	@NotNull
	private double latitude;

	@NotNull
	private double longitude;

	@NotNull
	private String googlePlaceId;

	private String address;
	
	public CreateLocationRequest(){}
	
	public CreateLocationRequest(double latitude, double longitude, String googlePlaceId, String address) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.googlePlaceId = googlePlaceId;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getGooglePlaceId() {
		return googlePlaceId;
	}

}
