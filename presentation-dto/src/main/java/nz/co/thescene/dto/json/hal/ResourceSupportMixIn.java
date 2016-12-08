package nz.co.thescene.dto.json.hal;

import java.util.List;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ResourceSupportMixIn {
	
	@JsonIgnore
	public abstract List<Link> getLinks();
}
