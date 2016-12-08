package nz.co.thescene.dto.json;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.Size;

@JsonRootName("categoryUpdate")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryUpdateRequest {

    @JsonProperty(required = true)
    @Size(min = 1, max = 100, message = "Too short or long name")
    private String name;

    public CategoryUpdateRequest() {}

    public CategoryUpdateRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
