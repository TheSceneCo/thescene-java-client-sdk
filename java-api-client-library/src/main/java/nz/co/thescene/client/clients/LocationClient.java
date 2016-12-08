package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.CreateLocationRequest;
import nz.co.thescene.dto.json.hal.LocationResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import org.springframework.core.ParameterizedTypeReference;
import nz.co.thescene.dto.json.hal.ProfileResource.Rels;
import org.springframework.hateoas.Resources;

public class LocationClient {

	private HttpService service = new HttpService();
	
	public LocationResource addLocationToProfile(ProfileResource profileResource, CreateLocationRequest request) { 
		return service.post(LocationResource.class, profileResource, Rels.LOCATION, request);
	}
	
	public LocationResource addLocationToProfile(MemberResource memberResource, CreateLocationRequest request) {
		return service.post(LocationResource.class, memberResource, Rels.LOCATION, request);
	}

	public LocationResource getLocation(LocationResource locationResource){
		ParameterizedTypeReference<LocationResource> resourceParameterizedTypeReference =
				new ParameterizedTypeReference<LocationResource>() {};
		return service.get(resourceParameterizedTypeReference, locationResource, Rels.SELF);
	}

	public Resources<LocationResource> getProfileLocations(ProfileResource profileResource){
		ParameterizedTypeReference<Resources<LocationResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<LocationResource>>() {};
		return service.get(resourceParameterizedTypeReference, profileResource, Rels.LOCATION);
	}

	public LocationResource deleteLocation(LocationResource locationResource){
		return service.delete(LocationResource.class, locationResource, LocationResource.Rels.SELF);
	}

	public LocationResource removeLocationFromProfile(ProfileResource profile, LocationResource tag){
		ParameterizedTypeReference<Resources<LocationResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<LocationResource>>() {};
		return service.delete(LocationResource.class, tag, LocationResource.Rels.SELF, resourceParameterizedTypeReference);
	}
	
}
