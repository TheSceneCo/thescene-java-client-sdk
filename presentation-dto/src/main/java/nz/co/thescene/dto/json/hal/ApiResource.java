package nz.co.thescene.dto.json.hal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResource extends ResourceSupport {

	private String greeting = "Welcome to TheScene.Co API. Surf the API by following the links. For questions, comments or feedback, please contact John at john@thescene.co";
	
	public static final class Rels { 
		public static final String MEMBERS = "members";
		
	}
	
	public String getGreeting() { 
		return greeting;
	}
	
	public void setGreeting(String greeting) { 
		this.greeting = greeting;
	}

}
