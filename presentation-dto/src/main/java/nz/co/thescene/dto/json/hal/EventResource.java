package nz.co.thescene.dto.json.hal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import nz.co.thescene.dto.parameters.ProfileTypeParameter;

@JsonRootName("event")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventResource extends ProfileResource {

	public static final class Rels { 
		public static final String EVENTS = "events";
		public static final String IMAGE_META_INFO = "imageMetaInfo";
	}


	@JsonProperty(required = true)
	private String eventName;
	
	private boolean paidEvent;
	private boolean soldOut;
	private boolean doorSales;
	private boolean publicEvent;
	private Long startTime;
	private Long endTime;
	
	private String shortDescription;
		
	public EventResource() {
		super(ProfileTypeParameter.events);
	}
	
	@JsonCreator
	public EventResource(@JsonProperty("profileId") String eventId, @JsonProperty("eventName") String eventName) {
		super(eventId, ProfileTypeParameter.events);
		this.eventName = eventName;
	}


	public String getEventName() {
		return eventName;
	}

	public void setEventName(String name) {
		this.eventName = name;
	}
	
	public String getShortDescription() { 
		return this.shortDescription;
	}
	
	public void setShortDescription(String shortDescription) { 
		this.shortDescription = shortDescription;
	}
	
	public boolean getPaidEvent() { 
		return this.paidEvent;
	}
	
	public void setPaidEvent(boolean freeEvent) { 
		this.paidEvent = freeEvent;
	}
	
	public boolean getSoldOut() { 
		return this.soldOut;
	}
	
	public void setSoldOut(boolean soldOut) { 
		this.soldOut = soldOut;
	}
	
	public boolean getDoorSales() { 
		return this.doorSales;
	}
	
	public void setDoorSales(boolean doorSales) { 
		this.doorSales = doorSales;
	}

	public boolean isPublicEvent() {
		return publicEvent;
	}

	public void setPublicEvent(boolean publicEvent) {
		this.publicEvent = publicEvent;
	}
	
	public void setStartTime(Long startTime) { 
		this.startTime = startTime;
	}
	
	public void setEndTime(Long endTime) { 
		this.endTime = endTime;
	}
	
	public Long getStartTime() { 
		return startTime;
	}
	
	public Long getEndTime() { 
		return endTime;
	}

	@Override
	public String toString() {
		return eventName;
	}
}
