package nz.co.thescene.console.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import nz.co.thescene.dto.json.hal.CategoryResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.MemberRegistrationRequest;
import nz.co.thescene.dto.json.hal.EventResource;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class MainMenu extends Menu {

	public void printMenu() {
		printHeader("Main Menu");
		System.out.println("1) View all events");
		System.out.println("2) Search events by location");
		System.out.println("3) View an event");
		System.out.println("4) View all artists");
		System.out.println("5) View an artist");
		System.out.println("6) View all venues");
		System.out.println("7) View a venue");
		System.out.println("8) View all services");
		System.out.println("9) View a service");
		System.out.println("10) View all members");
		System.out.println("11) View a member");
		System.out.println("12) View a categories");
		if (isLoggedIn()) {
			System.out.println("13) My Scene");
			System.out.println("wa) Web admin");
			System.out.println("l) Logout");
		} else {
			System.out.println("l) Login");
			System.out.println("r) Register");
		}
		System.out.println("s) Search");
		System.out.println("c) Console client settings");
		System.out.println("x) Exit");
	}

	public void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) {
			Resources<EventResource> allEvents = SceneClient.getEventClient().getAllEvents();
			Collection<EventResource> events = allEvents.getContent();
			if (events.isEmpty()) {
				System.out.println("There are no events, Sorry :c");
			} else {
				printEvents(events);
			}
		} else if (userInput.equals("2")) { // View all upcoming events
			Double latitude = Double.parseDouble(getUserInput("Enter latitude: "));
			Double longitude = Double.parseDouble(getUserInput("Enter longitude: "));
			Double withinDistance = Double.parseDouble(getUserInput("Within Distance: "));
			Resources<EventResource> allEvents = SceneClient.getEventClient().getEventsByLocation(latitude, longitude,
					withinDistance);
			Collection<EventResource> events = allEvents.getContent();
			if (events.isEmpty()) {
				System.out.println("There are no events, Sorry :c");
			} else {
				printEvents(events);
			}
		} else if (userInput.equals("3")) { // View an event
			Collection<EventResource> events = SceneClient.getEventClient().getEventsOfType("both").getContent();
			SelectorMenu<EventResource> selectorMenu = new SelectorMenu<EventResource>("Choose an event: ", consoleUI,
					events);
			EventResource selectedEvent = selectorMenu.select();
			consoleUI.showEventDetailMenu(selectedEvent);
		} else if (userInput.equals("4")) { // View all artists
			// Iterable<ArtistDTO> artists =
			// service.getArtistService().findAll();
			// System.out.println(artists);
		} else if (userInput.equals("5")) { // View an artist
			// SelectorMenu selectorMenu = new SelectorMenu("Choose an artist:
			// ", consoleUI, service.getArtistService().findAll());
			// ArtistDTO artist = (ArtistDTO) selectorMenu.select();
			// consoleUI.showArtistDetailMenu(artist);
		} else if (userInput.equals("6")) { // View all venues
			/*
			 * Iterable<VenueDTO> venues =
			 * service.getVenueService().getAllVenues();
			 * System.out.println(venues);
			 */
		} else if (userInput.equals("7")) { // View a venue
			/*
			 * SelectorMenu selectorMenu = new SelectorMenu("Choose a venue: ",
			 * consoleUI, service.getVenueService().getAllVenues()); VenueDTO
			 * venue = (VenueDTO)selectorMenu.select();
			 * consoleUI.showVenueDetailMenu(venue);
			 */
		} else if (userInput.equals("8")) { // view all services
			/*
			 * Iterable<SupplierDTO> allSuppliers =
			 * service.getSupplierService().getAllSuppliers();
			 * System.out.println(allSuppliers);
			 */
		} else if (userInput.equals("9")) { // view a service
			// SelectorMenu selectorMenu = new SelectorMenu("Choose a service:
			// ", consoleUI, service.getSupplierService().getAllSuppliers());
			// SupplierDTO serviceEntity = (SupplierDTO) selectorMenu.select();
			// consoleUI.showServiceDetailMenu(serviceEntity);
		} else if (userInput.equals("10")) { // View all members
			Collection<MemberResource> members = SceneClient.getMemberClient().getMembers().getContent();
			System.out.println(members);
		} else if (userInput.equals("11")) { // View a member
			Collection<MemberResource> members = SceneClient.getMemberClient().getMembers().getContent();
			SelectorMenu<MemberResource> selectorMenu = new SelectorMenu<MemberResource>("Choose a member: ", consoleUI,
					members);
			MemberResource selectedMember = (MemberResource) selectorMenu.select();
			System.out.println("First name: " + selectedMember.getFirstName());
			System.out.println("Last name: " + selectedMember.getLastName());
			System.out.println("Email: " + selectedMember.getEmail());
			System.out.println("Phone number: " + selectedMember.getPhoneNumber());

		} else if (userInput.equals("12")) {
			List<CategoryResource> categories =
					new ArrayList<>(SceneClient.getCategoryClient().getRootCategories("events").getContent());
			if ( categories != null && !categories.isEmpty() ) {
				CategorySelectorMenu categorySelectorMenu = new CategorySelectorMenu("Choose category ", consoleUI, categories);
				CategoryResource resource = categorySelectorMenu.select();
				List<EventResource> profiles = new ArrayList<>(SceneClient.getCategoryClient().getEventsOfCategory(resource).getContent());
				if ( profiles != null && !profiles.isEmpty() ) {
					System.out.println("Profiles in category " + categorySelectorMenu.select().getName());
					System.out.println();
					List<CategoryResource> path =
							new LinkedList<>(SceneClient.getCategoryClient().getPathOfCategory(resource).getContent());
					System.out.println("Category path: ");
					for (CategoryResource category : path) {
						System.out.print("/" + category);
					}
					System.out.println();
					profiles.forEach(System.out::print);
				} else {
					System.out.println("Category don't contain profiles! ");
				}
			} else {
				System.out.println("Category don't find! ");
			}


		} else if (userInput.equals("13")) { // My Scene (logged in users only)
			if (isLoggedIn()) {
				consoleUI.showMySceneMenu();
			} else { 
				System.out.println("*** You must log in to access your MyScene menu ***");
				login();
				consoleUI.showMySceneMenu();
			}
		} else if (userInput.equals("s")) { // Member search for debug
			String query = getUserInput("Enter your search term: ");
			List<MemberResource> members = new ArrayList<>(SceneClient.getMemberClient().search(query).getContent());
			members.forEach(System.out::println);
		} else if (userInput.equals("r")) { // Register
			String email = getUserInput("Enter your email address: ");
			String password = getUserInput("Enter your password: ");
			String firstName = getUserInput("Enter your first name: ");
			String lastName = getUserInput("Enter your last name: ");
			MemberResource memberResource = SceneClient.getMemberClient()
					.register(new MemberRegistrationRequest(firstName, lastName, email, password));
			SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(email, password);
			consoleUI.login(memberResource);
		} else if (userInput.equals("c")) {
			consoleUI.showConsoleClientSettingsMenu();
		} else if (userInput.equals("l")) { // Login or logout
			if (isLoggedIn()) {
				logout();
			} else {
				login();
			}
		} else if (userInput.equals("wa")) {
			// perhaps this is a crutch.... With "wa" input
		} else {
			handleInvalidInput();
		}
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub

	}

	private void printEvents(Collection<EventResource> events) {
		for (EventResource event : events) {
			printHeader(event.getEventName());
			System.out.println("Short description: " + event.getShortDescription());
			System.out.println("Door sales: " + event.getDoorSales());
			System.out.println("Sold out: " + event.getSoldOut());
			System.out.println("Paid event: " + event.getPaidEvent());
			printSubHeader(event.getEventName() + " links");
			for (Link link : event.getLinks()) {
				System.out.println(link.getRel() + ": " + link.getHref());
			}
		}
	}

}
