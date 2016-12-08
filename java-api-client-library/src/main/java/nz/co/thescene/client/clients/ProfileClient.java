package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.CategoryToProfileRequest;
import nz.co.thescene.dto.json.hal.CategoryResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;

public class ProfileClient {

	private HttpService service = new HttpService();
	
	public MemberResource getProfileOwner(ProfileResource profile) {
		return service.get(MemberResource.class, profile, ProfileResource.Rels.OWNER);
	}


	public Resources<CategoryResource> getCategoriesOfProfile(ProfileResource profileResource){
		ParameterizedTypeReference<Resources<CategoryResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<CategoryResource>>() {};
		return service.get(resourceParameterizedTypeReference, profileResource, ProfileResource.Rels.CATEGORY);
	}

	public CategoryResource addCategoryOfProfile(ProfileResource profileResource, CategoryToProfileRequest request){
		return service.put(CategoryResource.class, profileResource, ProfileResource.Rels.CATEGORY, request);
	}

	public CategoryResource removeCategoryFromProfile(ProfileResource profileResource, CategoryResource categoryResource){
		ParameterizedTypeReference<Resources<CategoryResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<CategoryResource>>() {};
		return service.delete(CategoryResource.class, categoryResource, CategoryResource.Rels.PROFILES, resourceParameterizedTypeReference);
	}
	
}
