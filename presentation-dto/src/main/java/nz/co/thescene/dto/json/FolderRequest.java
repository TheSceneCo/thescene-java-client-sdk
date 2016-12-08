package nz.co.thescene.dto.json;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("folder")
public class FolderRequest {

    @NotEmpty
    @Size(max = 100)
    private String name;

    private String parentFolderId;

    public FolderRequest() {
    }

    public FolderRequest(@JsonProperty("name")String name, @JsonProperty("parentFolderId") String parentFolderId) {
        this.name = name;
        this.parentFolderId = parentFolderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    @Override
    public String toString() {
        return "Folder [ name: " + name + " ]";
    }
}
