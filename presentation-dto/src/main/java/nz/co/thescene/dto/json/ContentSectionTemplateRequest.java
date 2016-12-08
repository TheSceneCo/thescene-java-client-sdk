package nz.co.thescene.dto.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContentSectionTemplateRequest {

	private String title;
	
	private String inputTemplate;
	
	private Set<String> profileTypes;
	
	private Map<String, String> outputTemplates;
	
	public ContentSectionTemplateRequest() { 
		profileTypes = new HashSet<String>();
		outputTemplates = new HashMap<String, String>();
	}
	
	public Set<String> getProfileTypes() {
		return profileTypes;
	}
	
	public void setProfileTypes(Set<String> profileTypes) {
		this.profileTypes = profileTypes;
	}
	
	public String getTitle() {
		return title;
	}

	public String getInputTemplate() {
		return inputTemplate;
	}

	public void setInputTemplate(String inputTemplate) { 
		this.inputTemplate = inputTemplate;
	}
	
	public void setTitle(String title) { 
		this.title = title;
	}
	
	public void addOutputTemplate(String identifier, String outputTemplates) { 
		this.outputTemplates.put(identifier, outputTemplates);
	}

	public Map<String, String> getOutputTemplates() {
		return outputTemplates;
	}
}
