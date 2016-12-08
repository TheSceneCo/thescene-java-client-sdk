package nz.co.thescene.dto.json;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryRequest {

    @NotNull
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long")
    private String name;

    private String parentId;

    private Set<String> profileTypeIds;

    public CategoryRequest() {
    	profileTypeIds = new HashSet<String>();
    }
    
    public Set<String> getProfileTypeIds() {
        return profileTypeIds;
    }

	public void setProfileTypeIds(Set<String> profileTypeIds) {
		this.profileTypeIds = profileTypeIds;
	}

    public String getName() {
        return name;
    }
    
    public void setName(String name) { 
    	this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}


