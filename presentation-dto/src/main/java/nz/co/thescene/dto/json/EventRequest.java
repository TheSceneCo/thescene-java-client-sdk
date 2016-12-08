package nz.co.thescene.dto.json;

import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("event")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequest {

	@JsonProperty(required = true)
	@Size(min = 1, max = 100)
	private String eventName;
	
	private boolean paidEvent;
	
	private boolean soldOut;
	
	private boolean doorSales;
	
	private boolean publicEvent;
	
	private Long startTime = new Long(0);
	
	private Long endTime = new Long(0);
	
	@Size(max = 200)
	private String shortDescription;
	
	// Try removing this
	public EventRequest() {}
	
	@JsonCreator
	public EventRequest(@JsonProperty("eventName") String eventName) { 
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

	public void setPublicEvent(boolean publicEvent) {
		this.publicEvent = publicEvent;
	}
	
	public boolean getPublicEvent() { 
		return this.publicEvent;
	}

	public boolean isPublicEvent() { 
		return publicEvent;
	}
	
	public void setStartTime(Long startTime) { 
		this.startTime = startTime;
	}
	
	public Long getStartTime() { 
		return startTime;
	}
	
	public void setEndTime(Long endTime) { 
		this.endTime = endTime;
	}
	
	public Long getEndTime() { 
		return endTime;
	}
}
