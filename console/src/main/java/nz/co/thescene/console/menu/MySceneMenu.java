package nz.co.thescene.console.menu;

import nz.co.thescene.dto.json.MessageThreadRequest;
import nz.co.thescene.dto.json.hal.GrantedAuthorityResource;
import nz.co.thescene.dto.json.hal.MessageThreadResource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.EventRequest;
import nz.co.thescene.dto.json.hal.EventResource;
import nz.co.thescene.dto.json.hal.MemberResource;

import java.util.ArrayList;
import java.util.List;

@Component
public class MySceneMenu extends Menu {
	
	@Override
	public void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) {
			/*ArtistDTO artist = service.getArtistService().createArtist(getUserInput("Enter the name of your artist: "),
					member);	
			*///CategorySelectorMenu categorySelectorMenu = new CategorySelectorMenu(consoleUI);
			//Category category = categorySelectorMenu.select();
			//service.categorize(artist, category);
			//System.out.println("Created artist: " + artist.getName());
		} else if (userInput.equals("2")) {
			/*VenueDTO venue = service.getVenueService().createVenue(getUserInput("Enter the name of your venue: "),
					member);	
			*///CategorySelectorMenu categorySelectorMenu = new CategorySelectorMenu(consoleUI);
			//Category category = categorySelectorMenu.select();
			//Venue venue = service.getVenueService().createVenue(member, getUserInput("Venue name: "));
			//service.categorize(venue, category);
			//System.out.println("Created venue: " + venue.getName());
		} else if (userInput.equals("3")) {
			//service.getSupplierService().createSupplier(getUserInput("Enter the name of your service: "), member);
		} else if (userInput.equals("4")) {
			EventRequest eventRequest = new EventRequest(getUserInput("Event name: "));
			//eventRequest.setDoorSales(true);
			//eventRequest.setPaidEvent(true);
			//eventRequest.setShortDescription("Santa will arrive and sing and give presents");
			//eventRequest.setSoldOut(false);
			SceneClient.getEventClient().createEvent(member, eventRequest);
		} else if (userInput.equals("5")) {
			/*Set<ArtistDTO> membersArtists = member.getOwnedArtists();
			System.out.println(membersArtists);*/
		} else if (userInput.equals("6")) {
			//System.out.println(member.getOwnedVenues());			
		} else if (userInput.equals("7")) {
			//System.out.println(member.getOwnedSuppliers());
		} else if (userInput.equals("8")) {
			List<EventResource> events = new ArrayList<>(SceneClient.getEventClient().getEvents(member).getContent());
			if (events.isEmpty()) {
				System.out.println("There are no events, Sorry :c");
			} else {
				events.forEach(System.out::println);
			}
		} else if (userInput.equals("9")) {
			/*SelectorMenu selectorMenu = new SelectorMenu("Select an artist",
					consoleUI, member.getOwnedArtists());
			ArtistDTO artist = (Artist) selectorMenu.select();
			consoleUI.showEventParticipantManagementMenu(artist);*/
		} else if (userInput.equals("10")) {
			/*SelectorMenu selectorMenu = new SelectorMenu("Select a venue",
					consoleUI, member.getOwnedVenues());
			VenueDTO venue = (VenueDTO) selectorMenu.select();
			consoleUI.showEventParticipantManagementMenu(venue);*/
		} else if (userInput.equals("11")) {
			/*SelectorMenu selectorMenu = new SelectorMenu("Select a supplier",
					consoleUI, member.getOwnedSuppliers());
			SupplierDTO serviceEntity = (SupplierDTO) selectorMenu.select();
			consoleUI.showEventParticipantManagementMenu(serviceEntity);*/	
		} else if (userInput.equals("12")) {
			Resources<EventResource> events = SceneClient.getEventClient().getEvents(member);
			SelectorMenu<EventResource> selectorMenu = new SelectorMenu<EventResource>("Select an event",
					consoleUI, events);
			EventResource selectedEvent = selectorMenu.select();
			consoleUI.showEventManagementMenu(selectedEvent);
		} else if (userInput.equals("13")) {

			List<MessageThreadResource> threadsList =
					new ArrayList<>(SceneClient.getMessagingClient().getMessageThreadsOfMember(member).getContent());
			SelectorMenu<MessageThreadResource> threads =
					new SelectorMenu<>("Choose a thread: ", consoleUI, threadsList);

			consoleUI.showMessageMenu(threads.select());

		} else if (userInput.equals("14")) {

			MessageThreadRequest request = new MessageThreadRequest();
			request.setSubject(getUserInput("Please, enter name of thread: "));
			MessageThreadResource thread = SceneClient.getMessagingClient().createMessageThread(member, request);

			consoleUI.showMessageMenu(thread);

		} else if (userInput.equals("15")) {
			consoleUI.showFolderMenu();
		} else if (userInput.equals("16")){
			consoleUI.showImageManagementMenu();
		} else if (userInput.equals("s")) {
			boolean updated = false;
			List<GrantedAuthorityResource> grantedAuthorityResources =
					new ArrayList<>(SceneClient.getMemberClient().getGrantedAuthoritiesForMember(member).getContent());
			System.out.println("Update member info. Type new property or enter to accept.");
			System.out.println();
			System.out.println("**** Granted authorities of " + member.getEmail() + " ****");
			grantedAuthorityResources.forEach(System.out::println);
			System.out.println();
			updated = updateStringValue("First name ", member.getFirstName(), firstName -> member.setFirstName(firstName))
					|| updated;
			updated = updateStringValue("Last name ", member.getLastName(), lastName -> member.setLastName(lastName))
					|| updated;
			updated = updateStringValue("Email ", member.getEmail(), email -> member.setEmail(email))
			 		|| updated;
			updated = updateStringValue("Phone ", member.getPhoneNumber(), phoneNumber -> member.setPhoneNumber(phoneNumber))
					|| updated;
			if (updated) {
				SceneClient.getMemberClient().updateMember(member);
			}

		} else if (userInput.equals("l")) {
			logout();
			consoleUI.back();
		} else {
			handleInvalidInput();
		}
	}

	@Override
	public void printMenu() {
		printHeader("My Scene Menu");
		System.out.println("1) Create artist");
		System.out.println("2) Create venue");
		System.out.println("3) Create service");
		System.out.println("4) Create event");
		System.out.println("5) List my artists");
		System.out.println("6) List my venues");
		System.out.println("7) List my services");
		System.out.println("8) List my events");
		System.out.println("9) Manage an artist");
		System.out.println("10) Manage an venue");
		System.out.println("11) Manage a service");
		System.out.println("12) Manage a event");
		System.out.println("13) Messages");
		System.out.println("14) Create new message thread");
		System.out.println("15) Manage folders");
		System.out.println("16) Manage images");
		System.out.println("s) User settings");
		System.out.println("l) Logout");
		System.out.println("b) Back to previous menu");
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
