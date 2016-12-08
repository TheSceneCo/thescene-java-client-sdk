package nz.co.thescene.client.clients;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;

import nz.co.thescene.client.clients.HttpService;
import nz.co.thescene.dto.json.ContentSectionCreationRequest;
import nz.co.thescene.dto.json.hal.ContentSectionResource;
import nz.co.thescene.dto.json.hal.ContentSectionTemplateResource;
import nz.co.thescene.dto.json.hal.ProfileResource;

public class ContentClient {

	private HttpService httpService = new HttpService();

	public ContentSectionResource createContentSection(ProfileResource profile, ContentSectionCreationRequest request) {
		return httpService.post(ContentSectionResource.class, profile, "contentSections", request);
	}

	public Resources<ContentSectionTemplateResource> getContentSectionTemplates(ProfileResource profile) {
		ParameterizedTypeReference<Resources<ContentSectionTemplateResource>> typeReference = new ParameterizedTypeReference<Resources<ContentSectionTemplateResource>>(){};
		return httpService.get(typeReference, profile, "contentSectionTemplates");
	}

	public ContentSectionResource generateContentSection(ProfileResource profileResource, String contentSectionTemplateId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("templateId", contentSectionTemplateId);
		return httpService.get(ContentSectionResource.class, profileResource, parameters, "new");
	}
	
	/*public ContentSectionTemplateResource createContentSectionTemplate(ContentSectionTemplateRequest request) {
		return httpService.post(ContentSectionTemplateResource.class, httpService.discover(), "administrationOptions", request, "contentSectionTemplates");
	}*/
}
