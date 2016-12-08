package nz.co.thescene.console.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nz.co.thescene.dto.json.hal.*;

import org.springframework.stereotype.Component;
import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.CollaboratorRequest;
import nz.co.thescene.dto.json.CreateLocationRequest;

@Component
public class EventManagementMenu extends Menu {

	private EventResource event;

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) {
			boolean updated = false;
			System.out.println("Update event properties. Type new property or enter to accept.");
			updated = updateStringValue("Event name ", event.getEventName(), name -> event.setEventName(name))
					|| updated;
			updated = updateBooleanValue("Paid event ", event.getPaidEvent(), isPaid -> event.setPaidEvent(isPaid))
					|| updated;
			updated = updateBooleanValue("Public event ", event.isPublicEvent(),
					isPublic -> event.setPublicEvent(isPublic)) || updated;
			updated = updateStringValue("Short description ", event.getShortDescription(),
					shortDescription -> event.setShortDescription(shortDescription)) || updated;
			updated = updateBooleanValue("Sold out ", event.getSoldOut(), isSoldOut -> event.setSoldOut(isSoldOut))
					|| updated;
			updated = updateBooleanValue("Has door sales ", event.getDoorSales(),
					doorSales -> event.setDoorSales(doorSales)) || updated;
			updated = updateTimeValue("Start time [dd-mm-yy] ", event.getStartTime(),
					startTime -> event.setStartTime(startTime)) || updated;
			updated = updateTimeValue("End time [dd-mm-yy] ", event.getEndTime(), 
					endTime -> event.setEndTime(endTime))
					|| updated;
			if (updated) {
				SceneClient.getEventClient().updateEvent(event);
			}
		} else if (userInput.equals("16")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Select an artist",
			// consoleUI, service.getArtistService().findAll());
			// ArtistDTO artist = (ArtistDTO)selectorMenu.select();
			// service.getParticipantService().inviteParticipantToEvent(event,
			// artist);
			// System.out.println("You have invited " + artist + " to " +
			// event);
		} else if (userInput.equals("2")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Select a venue",
			// consoleUI, service.getVenueService().getAllVenues());
			// VenueDTO venue = (VenueDTO)selectorMenu.select();
			// service.getParticipantService().inviteParticipantToEvent(event,
			// venue);
			// System.out.println("You have invited " + venue + " to " + event);
		} else if (userInput.equals("3")) {
			System.out.println("Not implemented");
		} else if (userInput.equals("4")) {
			System.out.println("You have invited:");
			// System.out.println(service.getParticipantService().getSentInvitations(event));
		} else if (userInput.equals("5")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Select an
			// invitation to cancel", consoleUI,
			// service.getParticipantService().getSentInvitations(event));
			// Participant invited = (Participant) selectorMenu.select();
			// service.getParticipantService().cancelInvitation(event, invited);
			// System.out.println("You have cancelled " + invited + "'s
			// invitation to " + event);
		} else if (userInput.equals("6")) {
			System.out.println("You have received invitations from:");
			// System.out.println(service.getParticipantService().getReceivedInvitations(event));
		} else if (userInput.equals("7")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Select an offer to
			// accept", consoleUI,
			// service.getParticipantService().getReceivedInvitations(event));
			// Participant invitee = (Participant)selectorMenu.select();
			// service.getParticipantService().acceptProposal(invitee, event);
			// System.out.println("You have accepted an offer from " + invitee +
			// " to particpate in " + event);
		} else if (userInput.equals("8")) {
			System.out.println("You have accepted invitations to: ");
			// System.out.println(service.getParticipantService().getAcceptedInvitations(event));
			System.out.println("The following have accepted proposals from you: ");
			// System.out.println(service.getParticipantService().getAcceptedProposals(event));
		} else if (userInput.equals("9")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Select an offer to
			// decline", consoleUI,
			// service.getParticipantService().getReceivedInvitations(event));
			// Participant invitor = (Participant)selectorMenu.select();
			// service.getParticipantService().declineReceivedInvitation(event,
			// invitor);
			// System.out.println("You have declined an offer from " + invitor +
			// " to participate in " + event);
		} else if (userInput.equals("10")) {
			consoleUI.showContentSectionMenu(event);
		} else if (userInput.equals("11")) {
			consoleUI.showEventCategoryMenu(event);
		} else if (userInput.equals("12")) {
			consoleUI.showTagManagementMenu(event);
		} else if (userInput.equals("13")) { // set profile photo
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu imageSelector = new FileFolderSelectorMenu("Select folder: ", consoleUI,
					startFolder);
			imageSelector.setAllowSelectFile(true);
			imageSelector.setAllowSelectFolder(false);
			ImageMetaInfoResource imageMetaInfoResource = SceneClient.getImageClient()
					.getImageMetaInfo(imageSelector.select());
			SceneClient.getImageClient().setProfilePhoto(event, imageMetaInfoResource);
			consoleUI.displayImage(imageMetaInfoResource);
		} else if (userInput.equals("14")) {

			ImageMetaInfoResource image = SceneClient.getImageClient().getProfilePhoto(event);
			System.out.println("Image name: " + image.getName());

		} else if (userInput.equals("15")) {

			SceneClient.getImageClient().deleteProfilePhoto(event);

		} else if (userInput.equals("17")) {
			List<MemberResource> memberResources = new ArrayList<>(
					SceneClient.getMemberClient().search(getUserInput("Please enter query: ")).getContent());
			SelectorMenu<MemberResource> memberResourceSelectorMenu = new SelectorMenu<MemberResource>(
					"Select member: ", consoleUI, memberResources);
			MemberResource foundMemberResource = memberResourceSelectorMenu.select();
			CollaboratorRequest collaboratorRequest = new CollaboratorRequest();
			collaboratorRequest.setMemberId(foundMemberResource.getMemberId());
			String[] possiblePermissions = { "member", "admin", "owner" };
			SelectorMenu<String> permissionSelector = new SelectorMenu<String>(
					"Choose permission level for " + foundMemberResource.getFirstName(), consoleUI,
					possiblePermissions);
			collaboratorRequest.setPermissionList(permissionSelector.select());
			updateBooleanValue("Add as public collaborator ", collaboratorRequest.getPublicCollaborator(),
					isPublicCollaborator -> collaboratorRequest.setPublicCollaborator(isPublicCollaborator));
			SceneClient.getEventClient().addCollaboratorToEvent(collaboratorRequest, event);
		} else if (userInput.equals("18")) {

			Collection<CollaboratorResource> collaborators = SceneClient.getEventClient().getCollaborators(event)
					.getContent();
			SelectorMenu<CollaboratorResource> collaboratorSelector = new SelectorMenu<CollaboratorResource>(
					"Choose a collaborator to update: ", consoleUI, collaborators);
			CollaboratorResource collaboratorResource = collaboratorSelector.select();
			String[] possiblePermissions = { "member", "admin", "owner" };
			SelectorMenu<String> permissionSelector = new SelectorMenu<String>(
					"Choose permission level for " + collaboratorResource.getEmail(), consoleUI, possiblePermissions);
			collaboratorResource.setPermissionList(permissionSelector.select());
			List<Boolean> isPublic = new LinkedList<>();
			isPublic.add(true);
			isPublic.add(false);
			SelectorMenu<Boolean> publicSelector = new SelectorMenu<>("Is it public collaborator? ", consoleUI,
					isPublic);
			collaboratorResource.setPublicCollaborator(publicSelector.select());

			try {
				SceneClient.getEventClient().updateCollaborator(collaboratorResource);
			} catch (SceneClientException exc) {
				System.out.println("Access is denied");
			}

		} else if (userInput.equals("19")) {
			Collection<CollaboratorResource> collaborators = SceneClient.getEventClient().getCollaborators(event)
					.getContent();
			SelectorMenu<CollaboratorResource> collaboratorSelector = new SelectorMenu<>(
					"Choose a collaborator to remove: ", consoleUI, collaborators);
			CollaboratorResource collaboratorResource = collaboratorSelector.select();
			CollaboratorResource removedCollaborator = SceneClient.getEventClient()
					.removeCollaborator(collaboratorResource, event);
			System.out.println(removedCollaborator.getFirstName() + " " + removedCollaborator.getLastName()
					+ " is no longer a part of " + event.getEventName());
		} else if (userInput.equals("20")) {
			super.printHeader("Collaborators for " + event);
			Collection<CollaboratorResource> collaborators = SceneClient.getEventClient().getCollaborators(event)
					.getContent();
			collaborators.forEach(System.out::println);
		} else if (userInput.equals("21")) {
			EventResource deletedEvent = SceneClient.getEventClient().deleteEvent(event);
			System.out.println("Deleted event '" + deletedEvent.getEventName() + "'");
			consoleUI.back();
		} else if (userInput.equals("22")) {
			CreateLocationRequest locationRequest = new CreateLocationRequest(
					Double.parseDouble(getUserInput("latitude: ")), Double.parseDouble(getUserInput("longitude: ")),
					getUserInput("googlePlaceId: "), getUserInput("address string: "));
			LocationResource locationResource = SceneClient.getLocationClient().addLocationToProfile(event,
					locationRequest);
			System.out.println(locationResource);
		} else if (userInput.equals("23")) {

			List<LocationResource> resources = new ArrayList<>(
					SceneClient.getLocationClient().getProfileLocations(event).getContent());
			resources.forEach(System.out::println);

		} else if (userInput.equals("24")) {

			List<LocationResource> resources = new ArrayList<>(
					SceneClient.getLocationClient().getProfileLocations(event).getContent());

			SelectorMenu<LocationResource> selector = new SelectorMenu<>(
					"Choose location to delete form profile #" + event.getProfileId(), consoleUI, resources);

			LocationResource locationResource = SceneClient.getLocationClient().removeLocationFromProfile(event,
					selector.select());
			System.out.println(locationResource + " was deleted from profile: " + event.getEventName());

		} else {
			handleInvalidInput();
		}
	}

	@Override
	public void printMenu() {
		printHeader("Manage " + event);
		System.out.println("1) Update event properties");
		System.out.println("2) Invite venue to event");
		System.out.println("3) Invite service to event");
		System.out.println("4) See sent invitations");
		System.out.println("5) Cancel sent invitations");
		System.out.println("6) See received invitations");
		System.out.println("7) Accept received invitations");
		System.out.println("8) See accepted and received invitations");
		System.out.println("9) Decline received invitations");
		System.out.println("10) Manage content");
		System.out.println("11) Manage categories");
		System.out.println("12) Manage tags");
		System.out.println("13) Set profile photo");
		System.out.println("14) Get profile photo");
		System.out.println("15) Delete profile photo");
		System.out.println("16) Invite artist to event");
		System.out.println("17) Invite collaborator");
		System.out.println("18) Update collaborator");
		System.out.println("19) Remove collaborator");
		System.out.println("20) List collaborators");
		System.out.println("21) Delete event");
		System.out.println("22) Add location to event");
		System.out.println("23) Get location of event");
		System.out.println("24) Delete location of event");
		System.out.println("b) Back to previous menu");
	}

	public void setEvent(EventResource event) {
		this.event = event;
	}

	@Override
	public void onError(SceneClientException e) {
		e.printStackTrace();
	}

}
