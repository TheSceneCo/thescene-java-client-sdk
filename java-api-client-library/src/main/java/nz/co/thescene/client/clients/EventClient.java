package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.hal.*;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import nz.co.thescene.dto.json.CollaboratorRequest;
import nz.co.thescene.dto.json.EventRequest;
import nz.co.thescene.dto.json.hal.ProfileResource.Rels;

import java.util.HashMap;
import java.util.Map;

public class EventClient {

	private HttpService httpService = new HttpService();

	public EventResource createEvent(MemberResource member, EventRequest eventRequest) {
		return httpService.post(EventResource.class, member, EventResource.Rels.EVENTS, eventRequest);
	}

	public EventResource getEvent(EventResource eventResource) {
		return httpService.get(EventResource.class, eventResource, Rels.SELF);
	}

	public Resources<EventResource> getEvents(MemberResource memberResource) {
		ParameterizedTypeReference<Resources<EventResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<EventResource>>() {
		};
		return httpService.get(resourceParameterizedTypeReference, memberResource, EventResource.Rels.EVENTS);
	}

	public Resources<CollaboratorResource> getCollaborators(EventResource eventResource) {
		ParameterizedTypeReference<Resources<CollaboratorResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<CollaboratorResource>>() {};
		return httpService.get(resourceParameterizedTypeReference,eventResource, Rels.COLLABORATORS);
	}

	public MemberResource getEventCollaborator(MemberResource memberDTO, EventResource eventResource) {
		return httpService.get(MemberResource.class, eventResource, Rels.COLLABORATORS);
	}

	public EventResource getEvent(CollaboratorResource collaboratorResource) {
		return httpService.get(EventResource.class, collaboratorResource, Rels.PROFILE);
	}

	public CollaboratorResource addCollaboratorToEvent(CollaboratorRequest collaboratorRequest, ProfileResource profileResource) {
		return httpService.post(CollaboratorResource.class, profileResource, Rels.COLLABORATORS, collaboratorRequest);
	}
	
	public EventResource updateEvent(EventResource eventResource) {
		ModelMapper modelMapper = new ModelMapper();
		EventRequest eventRequest = modelMapper.map(eventResource, EventRequest.class);
		return httpService.put(EventResource.class, eventResource, Rels.SELF, eventRequest);
	}

	public Resources<EventResource> getAllEvents() {
		ParameterizedTypeReference<Resources<EventResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<EventResource>>() {
		};
		ApiResource api = httpService.discover();
		return httpService.get(resourceParameterizedTypeReference, api, EventResource.Rels.EVENTS);
	}

	public CollaboratorResource removeCollaborator(CollaboratorResource collaboratorResource, EventResource event) {
		return httpService.delete(CollaboratorResource.class, collaboratorResource, CollaboratorResource.Rels.SELF);
	}

	public CollaboratorResource updateCollaborator(CollaboratorResource collaboratorResource) {
		ModelMapper modelMapper = new ModelMapper();
		CollaboratorRequest collaboratorRequest = modelMapper.map(collaboratorResource, CollaboratorRequest.class);
		return httpService.put(CollaboratorResource.class, collaboratorResource, CollaboratorResource.Rels.SELF, collaboratorRequest);
	}

	public EventResource deleteEvent(EventResource eventResource) {
		return httpService.delete(EventResource.class, eventResource, ProfileResource.Rels.SELF);
	}

	public Resources<EventResource> getEventsOfType(String eventType) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collection", eventType);
		ParameterizedTypeReference<Resources<EventResource>> typeReference = new ParameterizedTypeReference<Resources<EventResource>>(){};
		return httpService.get(typeReference, httpService.discover(), parameters, EventResource.Rels.EVENTS);
	}

	public Resources<EventResource> getEventsByLocation(Double latitude, Double longitude, Double withinDistance){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("withinDistance", withinDistance);
		parameters.put("latitude", latitude);
		parameters.put("longitude", longitude);
		ParameterizedTypeReference<Resources<EventResource>> typeReference = new ParameterizedTypeReference<Resources<EventResource>>(){};
		return httpService.get(typeReference, httpService.discover(), parameters, EventResource.Rels.EVENTS);
	}

}
