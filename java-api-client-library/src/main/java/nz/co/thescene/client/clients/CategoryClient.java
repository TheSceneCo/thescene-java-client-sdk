package nz.co.thescene.client.clients;


import nz.co.thescene.dto.json.hal.CategoryResource;
import nz.co.thescene.dto.json.hal.EventResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;

import java.util.HashMap;
import java.util.Map;

public class CategoryClient {

    private HttpService httpService = new HttpService();

    public Resources<CategoryResource> getRootCategories(String profileTypePath){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("profileType", profileTypePath);
        ParameterizedTypeReference<Resources<CategoryResource>> resourceParameterizedTypeReference =
                new ParameterizedTypeReference<Resources<CategoryResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, httpService.discover(), parameters, "categories");
    }

    public Resources<CategoryResource> searchCategory(String name, String profileTypeId){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", name);
        parameters.put("profileType", profileTypeId);
        ParameterizedTypeReference<Resources<CategoryResource>> resourceParameterizedTypeReference =
                new ParameterizedTypeReference<Resources<CategoryResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, httpService.discover(), parameters, "categories");
    }

    public CategoryResource getCategory(CategoryResource categoryResource){
        return httpService.get(CategoryResource.class, categoryResource, CategoryResource.Rels.SELF);
    }

    public Resources<EventResource> getEventsOfCategory(CategoryResource categoryResource){
        ParameterizedTypeReference<Resources<EventResource>> resourceParameterizedTypeReference =
                new ParameterizedTypeReference<Resources<EventResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, categoryResource, CategoryResource.Rels.PROFILES);
    }

    public CategoryResource getParentOfCategory(CategoryResource categoryResource){
        return httpService.get(CategoryResource.class, categoryResource, CategoryResource.Rels.PARENT);
    }

    public Resources<CategoryResource> getChildrenOfCategory(CategoryResource categoryResource){
        ParameterizedTypeReference<Resources<CategoryResource>> resourceParameterizedTypeReference =
                new ParameterizedTypeReference<Resources<CategoryResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, categoryResource, CategoryResource.Rels.CHILDREN);
    }

    public Resources<CategoryResource> getPathOfCategory(CategoryResource categoryResource){
        ParameterizedTypeReference<Resources<CategoryResource>> resourceParameterizedTypeReference =
                new ParameterizedTypeReference<Resources<CategoryResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, categoryResource, CategoryResource.Rels.PATH);
    }

}
