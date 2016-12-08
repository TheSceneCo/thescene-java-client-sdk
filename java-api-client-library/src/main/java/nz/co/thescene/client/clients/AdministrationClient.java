package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.*;
import nz.co.thescene.dto.json.hal.*;

import java.util.List;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;

public class AdministrationClient {

	private HttpService httpService = new HttpService();

	private AdministrationResource getAdministrationResource() {
		ApiResource discover = httpService.discover();
		return httpService.get(AdministrationResource.class, discover, "administrationOptions");
	}

	// ***********************************
	// GRANTED AUTHORITIES
	// ***********************************

	public GrantedAuthorityResource createGrantedAuthority(GrantedAuthorityRequest request) {
		return httpService.post(GrantedAuthorityResource.class, getAdministrationResource(), "grantedAuthorities",
				request);
	}

	public GrantedAuthorityResource deleteGrantedAuthority(GrantedAuthorityResource grantedAuthorityResource) {
		return httpService.delete(GrantedAuthorityResource.class, grantedAuthorityResource, "self");
	}

	public Resources<GrantedAuthorityResource> getAllGrantedAuthorities() {
		ParameterizedTypeReference<Resources<GrantedAuthorityResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<GrantedAuthorityResource>>() {
		};
		return httpService.get(resourceParameterizedTypeReference, getAdministrationResource(), "grantedAuthorities");
	}

	public GrantResource addGrantedAuthorityToMember(MemberResource memberResource,
			GrantedAuthorityResource grantedAuthorityResource) {
		memberResource = httpService.get(MemberResource.class, memberResource, "self");
		GrantRequest grantRequest = new GrantRequest(memberResource.getMemberId(),
				grantedAuthorityResource.getGrantedAuthorityId());
		
		return httpService.post(GrantResource.class, memberResource, "grants", grantRequest);
	}

	public GrantResource removeGrantFromMember(GrantResource grantResource) {
		return httpService.delete(GrantResource.class, grantResource, "self");
	}

	// ***********************************
	// CONTENT SECTION TEMPLATES
	// ***********************************

	public ContentSectionTemplateResource createContentSectionTemplate(ContentSectionTemplateRequest request) {
		return httpService.post(ContentSectionTemplateResource.class, getAdministrationResource(),
				"contentSectionTemplates", request);
	}

	public Resources<ContentSectionTemplateResource> getAllContentSectionTemplates() {
		ParameterizedTypeReference<Resources<ContentSectionTemplateResource>> responseType = new ParameterizedTypeReference<Resources<ContentSectionTemplateResource>>() {
		};
		return httpService.get(responseType, httpService.discover(), "administrationOptions",
				"contentSectionTemplates");
	}

	public ContentSectionTemplateResource deleteContentSectionTemplate(
			ContentSectionTemplateResource contentSectionTemplateResource) {
		return httpService.delete(ContentSectionTemplateResource.class, contentSectionTemplateResource, "self");
	}

	public ContentSectionTemplateResource updateContentSectionTemplate(
			ContentSectionTemplateResource contentSectionTemplateResource, Set<String> profileTypes) {
		ContentSectionTemplateRequest request = new ContentSectionTemplateRequest();
		request.setTitle(contentSectionTemplateResource.getTitle());
		request.setInputTemplate(contentSectionTemplateResource.getInputTemplate());
		if (profileTypes != null) {
			request.setProfileTypes(profileTypes);
		}
		return httpService.put(ContentSectionTemplateResource.class, contentSectionTemplateResource, "self", request);
	}

	public List<ProfileTypeResource> getProfileTypesForContentSectionTemplate(
			ContentSectionTemplateResource contentSectionTemplateResource) {
		ParameterizedTypeReference<List<ProfileTypeResource>> type = new ParameterizedTypeReference<List<ProfileTypeResource>>() {
		};
		return httpService.get(type, contentSectionTemplateResource, "profileTypes");
	}

	// ***********************************
	// CATEGORIES
	// ***********************************
	public CategoryResource createCategory(CategoryRequest categoryRequest) {
		return httpService.post(CategoryResource.class, getAdministrationResource(), "categories", categoryRequest);
	}

	public CategoryResource updateCategory(CategoryResource categoryResource, CategoryUpdateRequest request) {
		return httpService.put(CategoryResource.class, categoryResource, "self", request);
	}

	public CategoryResource deleteCategory(CategoryResource categoryResource) {
		return httpService.delete(CategoryResource.class, categoryResource, "self");
	}

}
