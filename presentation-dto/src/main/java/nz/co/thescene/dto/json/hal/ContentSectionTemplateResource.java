package nz.co.thescene.dto.json.hal;

import java.util.Set;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("contentSectionTemplate")
public class ContentSectionTemplateResource extends ResourceSupport {

	private String inputTemplate;
	private String title;
	private String sceneId;
	// private Set<String> profileTypes;
	
	public ContentSectionTemplateResource() {}
	
	@JsonCreator
	public ContentSectionTemplateResource(@JsonProperty("sceneId") String sceneId, @JsonProperty("title") String title, @JsonProperty("inputTemplate") String inputTemplate) { 
		this.inputTemplate = inputTemplate;
		this.title = title;
		this.sceneId = sceneId;
	}
	
	public void setTitle(String title) { 
		this.title = title;
	}
	
	public void setInputTemplate(String inputTemplate) { 
		this.inputTemplate = inputTemplate;
	}
	
	public void setSceneId(String sceneId) { 
		this.sceneId = sceneId;
	}
	
	public String getTitle() { 
		return title;
	}
	
	public String getSceneId() {
		return sceneId;
	}
	
	@Override
	public String toString() {
		return title;
	}

	public String getInputTemplate() {
		return inputTemplate;
	}

	/*public void setProfileTypes(Set<String> profileTypes) {
		this.profileTypes = profileTypes;
	}
*/
	/*public Set<String> getProfileTypes() {
		return this.profileTypes;
	}*/
	
}
