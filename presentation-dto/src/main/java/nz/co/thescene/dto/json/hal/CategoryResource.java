package nz.co.thescene.dto.json.hal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.ResourceSupport;

@JsonRootName("category")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResource extends ResourceSupport {

    public static final class Rels {
        public static final String PARENT = "parent";
        public static final String CHILDREN = "categories";
        public static final String PROFILES = "profiles";
        public static final String PATH = "path";
        public static final String SELF = "self";
        public static final String CREATOR = "creator";
    }

    private String categoryId;

    @JsonProperty(required = true)
    private String name;

    public CategoryResource() {}

    public CategoryResource(@JsonProperty("sceneId") String categoryId, @JsonProperty("name") String name) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean hasParent() {
        return hasLink(Rels.PARENT.toString().toLowerCase());
    }

    public boolean hasChildren() {
        return hasLink(Rels.CHILDREN.toString().toLowerCase());
    }

    public boolean hasProfiles() {
        return hasLink(Rels.PROFILES.toString().toLowerCase());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
