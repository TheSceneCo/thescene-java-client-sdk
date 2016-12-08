package nz.co.thescene.dto.json.hal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("tag")
public class TagResource extends ResourceSupport {

	public static final class Rels {
    	public static final String ALL_PROFILES = "allProfiles";
		public static final String PROFILES_BY_TYPE = "profilesByType";
		public static final String SELF = "self";
        public static final String PROFILE = "profile";
    }

	private String name;

    private String description;

    public TagResource() {
    }

    @JsonCreator
    public TagResource(@JsonProperty("name") String name,@JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TagResource{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    
    @Override
   	public int hashCode() {
   		final int prime = 31;
   		int result = super.hashCode();
   		result = prime * result + ((name == null) ? 0 : name.hashCode());
   		return result;
   	}

   	@Override
   	public boolean equals(Object obj) {
   		if (this == obj)
   			return true;
   		if (getClass() != obj.getClass())
   			return false;
   		TagResource other = (TagResource) obj;
   		if (name == null) {
   			if (other.name != null)
   				return false;
   		} else if (!name.equals(other.name))
   			return false;
   		return true;
   	}
}
