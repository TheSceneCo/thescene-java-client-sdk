package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.TagRequest;
import nz.co.thescene.dto.json.hal.ApiResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import nz.co.thescene.dto.json.hal.TagResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;

import java.util.HashMap;
import java.util.Map;

public class TagClient {

    private HttpService httpService = new HttpService();

    public TagResource createTag(ProfileResource profile, TagRequest request) {
        return httpService.post(TagResource.class, profile, ProfileResource.Rels.TAGS, request);
    }

    public TagResource getTag(TagResource tag){
        ParameterizedTypeReference<TagResource> resourceParameterizedTypeReference = new ParameterizedTypeReference<TagResource>() {};
        return httpService.get(resourceParameterizedTypeReference, tag, ProfileResource.Rels.SELF);
    }

    public Resources<TagResource> getProfileTags(ProfileResource profileResource){
        ParameterizedTypeReference<Resources<TagResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<TagResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, profileResource, ProfileResource.Rels.TAGS);
    }

    public TagResource delete(TagResource tag){
        return httpService.delete(TagResource.class, tag, ProfileResource.Rels.SELF);
    }

    public Resources<TagResource> searchTags(String profileType, String name){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("profileType", profileType);
        parameters.put("name", name);
        ParameterizedTypeReference<Resources<TagResource>> resourceParameterizedTypeReference =
                new ParameterizedTypeReference<Resources<TagResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, httpService.discover(), parameters, ProfileResource.Rels.TAGS);
    }

    public Resources<TagResource> getAllTags(String profileType){
        ParameterizedTypeReference<Resources<TagResource>> resourceParameterizedTypeReference =
                new ParameterizedTypeReference<Resources<TagResource>>() {};
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("profileType", profileType);
        return httpService.get(resourceParameterizedTypeReference, httpService.discover(), parameters, ProfileResource.Rels.TAGS);
    }

    public TagResource removeTagFromProfile(ProfileResource profile, TagResource tag){
        ParameterizedTypeReference<Resources<TagResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<TagResource>>() {};
        return httpService.delete(TagResource.class, tag, TagResource.Rels.PROFILE, resourceParameterizedTypeReference);
    }

}
