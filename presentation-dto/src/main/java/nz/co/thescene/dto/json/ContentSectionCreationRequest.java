package nz.co.thescene.dto.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("contentSection")
public class ContentSectionCreationRequest {
	
	private String title;
	private String content;
	private String templateId; // used for update
	
	@JsonCreator
	public ContentSectionCreationRequest(@JsonProperty("title") String title, @JsonProperty("content") String content, @JsonProperty("templateId") String templateId, @JsonProperty("outputTemplates") String[] outputTemplates) { 
		this.title = title;
		this.content = content;
		this.templateId = templateId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getTemplateId() {
		return templateId;
	}

}
