package nz.co.thescene.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.hibernate.validator.constraints.NotEmpty;


@JsonRootName("categoryToProfile")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryToProfileRequest {

    @JsonProperty(required = true)
    @NotEmpty
    private String categoryId;

    public CategoryToProfileRequest() {
    }

    public CategoryToProfileRequest(@JsonProperty("category") String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
