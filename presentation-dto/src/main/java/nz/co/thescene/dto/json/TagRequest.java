package nz.co.thescene.dto.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("tag")
public class TagRequest {

    @NotEmpty
    @Size(min = 2, max = 25, message = "Please specify message")
    private String name;

    @Size(max = 140, message = "Please specify message")
    private String description;

    public TagRequest() {
    }

    @JsonCreator
    public TagRequest(@JsonProperty("name")String name,
                      @JsonProperty("description")String description) {
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

}
