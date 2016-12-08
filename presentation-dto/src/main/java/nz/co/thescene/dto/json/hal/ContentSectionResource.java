package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("contentSection")
public class ContentSectionResource extends ResourceSupport {

	private String title;
	
	private String content;
	
	@JsonCreator
	public ContentSectionResource(@JsonProperty("title") String title, @JsonProperty("content") String content) {
		this.title = title;
		this.content = content;
	}
	
	public String getTitle() { 
		return title;
	}
	
	public String getContent() { 
		return content;
	}
	
	public void setTitle(String title) { 
		this.title = title;
	}
	
	public void setContent(String content) { 
		this.content = content;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
