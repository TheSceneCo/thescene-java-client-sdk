package nz.co.thescene.console;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.Stack;

import nz.co.thescene.console.menu.*;

import nz.co.thescene.console.menu.webadmin.DataGenerationMenu;
import nz.co.thescene.dto.json.hal.MessageThreadResource;
import org.alcibiade.asciiart.coord.TextBoxSize;
import org.alcibiade.asciiart.image.rasterize.ShapeRasterizer;
import org.alcibiade.asciiart.raster.ExtensibleCharacterRaster;
import org.alcibiade.asciiart.raster.Raster;
import org.alcibiade.asciiart.raster.RasterContext;
import org.alcibiade.asciiart.widget.PictureWidget;
import org.alcibiade.asciiart.widget.TextWidget;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.hal.ArtistResource;
import nz.co.thescene.dto.json.hal.EventResource;
import nz.co.thescene.dto.json.hal.ImageMetaInfoResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import nz.co.thescene.dto.json.hal.SupplierResource;
import nz.co.thescene.dto.json.hal.VenueResource;

@Component
public class ConsoleUI implements CommandLineRunner, ApplicationContextAware {

	private Scanner scanner;
	private Stack<Menu> menuStack;
	private MemberResource member;
	private ApplicationContext applicationContext;
	
	public ConsoleUI()  {
		scanner = new Scanner(System.in);
		menuStack = new Stack<Menu>();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		menuStack.push(applicationContext.getBean(MainMenu.class));
		
		menuStack.push(applicationContext.getBean(WelcomeConsoleClientSettingsMenu.class));
		
		printHeader();

		String userInput;
		do {
			Menu currentMenu = menuStack.peek();
			currentMenu.printMenuAndGetUserInput();
			userInput = currentMenu.peekAtUserInput();
			try {
				currentMenu.processUserInput();
			} catch (SceneClientException e) { 
				currentMenu.onError(e);
			}
			globalMenuOptions(userInput);

		} while (!userInput.equals("x"));

		cleanUp();
	}


	private void globalMenuOptions(String userInput) {
		// global show the super admin menu when requested
		if (userInput.equals("wa")) {
			showAdminMenu();
		}

		// global back to the previous menu
		else if (userInput.equals("b")) {
			back();
		}
	}

	private void cleanUp() {
		// do clean up stuff
		scanner.close();
		System.out.println("Thank you, have a nice day!");
	}

	private void printHeader() {
		System.out.println();
		System.out.println("******************************************");
		System.out.println("*                                        *");
		System.out.println("*    Welcome to The Scene Console UI     *");
		System.out.println("*                                        *");
		System.out.println("*       --- Support live Music ---       *");
		System.out.println("*                                        *");
		System.out.println("******************************************");
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void showMySceneMenu() {
		MySceneMenu mySceneMenu = applicationContext.getBean(MySceneMenu.class);
		menuStack.push(mySceneMenu);
	}

	public void back() {
		if (menuStack.size() > 1) {
			menuStack.pop();
		}
	}

	public void login(MemberResource member) {
		String accessToken = SceneClient.getClientContext().getAccessToken();
		System.out.println("Access token: " + accessToken);
		if (member != null) {
			member.getLink("");
			this.member = member;
		} else {
			System.out.println("Invalid email or password");
		}
	}

	public void logout() {
		SceneClient.getClientContext().clearAccessToken(); 
		SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
		this.member = null;
	}
	
	public boolean isLoggedIn() {
		return member != null; 
	}

	public void showAdminMenu() {
		menuStack.push(applicationContext.getBean(WebAdminMenu.class));
	}

	public void showEventManagementMenu(EventResource event) {
		EventManagementMenu eventManagementMenu = applicationContext.getBean(EventManagementMenu.class);
		eventManagementMenu.setEvent(event);
		menuStack.push(eventManagementMenu);
	}

	public MemberResource getLoggedInMember() {
		return member;
	}

	public void showEventParticipantManagementMenu(
			ProfileResource eventParticipant) {
		EventParticipantMenu eventParticipantMenu = applicationContext.getBean(EventParticipantMenu.class);
		//eventParticipantMenu.setEventParticipant(eventParticipant);
		menuStack.push(eventParticipantMenu);
	}

	public void showEventDetailMenu(EventResource event) {
		if (event != null) {
			EventDetailMenu eventDetailMenu = applicationContext.getBean(EventDetailMenu.class);
			eventDetailMenu.setEvent(event);
			menuStack.push(eventDetailMenu);
		}
	}

	public void showEventCategoryMenu(EventResource event) {
		if (event != null) {
			EventCategoryMenu eventCategoryMenu = applicationContext.getBean(EventCategoryMenu.class);
			eventCategoryMenu.setEvent(event);
			menuStack.push(eventCategoryMenu);
		}
	}

	public void showContentSectionMenu(ProfileResource publisher) {
		ContentSectionMenu contentSectionMenu = applicationContext.getBean(ContentSectionMenu.class);
		contentSectionMenu.setPublisher(publisher);
		menuStack.push(contentSectionMenu);
	}

	public void showArtistDetailMenu(ArtistResource artist) {
		if (artist != null) {
			ArtistDetailMenu artistDetailMenu = applicationContext.getBean(ArtistDetailMenu.class);
			artistDetailMenu.setArtist(artist);
			menuStack.push(artistDetailMenu);
		}
	}

	public void showVenueDetailMenu(VenueResource venue) {
		if (venue != null) {
			VenueDetailMenu venueDetailMenu = applicationContext.getBean(VenueDetailMenu.class);
			//venueDetailMenu.setVenue(venue);
			menuStack.push(venueDetailMenu);
		}
	}

	public void showServiceDetailMenu(SupplierResource service) {
		if (service != null) {
			ServiceDetailMenu serviceDetailMenu = applicationContext.getBean(ServiceDetailMenu.class);
			//serviceDetailMenu.setService(service);
			menuStack.push(serviceDetailMenu);
		}
	}

	public void showContentSectionTemplateMenu(MemberResource creator) {
		ContentSectionTemplateMenu contentSectionTemplateMenu = applicationContext.getBean(ContentSectionTemplateMenu.class);
		contentSectionTemplateMenu.setCreator(creator);
		menuStack.push(contentSectionTemplateMenu);
	}

	public void showCategoryAdminMenu(MemberResource creator) {
		CategoryAdminMenu categoryAdminMenu = applicationContext.getBean(CategoryAdminMenu.class);
		categoryAdminMenu.setCreator(creator);
		menuStack.push(categoryAdminMenu);
	}

	public void showDataGeneratorMenu(MemberResource creator) {
		DataGenerationMenu dataGenerationMenu = applicationContext.getBean(DataGenerationMenu.class);
		menuStack.push(dataGenerationMenu);
	}

	public void showTagManagementMenu(EventResource event) {
		TagManagementMenu tagManagementMenu = applicationContext.getBean(TagManagementMenu.class);
		tagManagementMenu.setEvent(event);
		menuStack.push(tagManagementMenu);
	}
	
	public void showGrantedAuthorityAdministrationMenu(MemberResource member) {
		GrantedAuthorityAdministrationMenu grantedAuthorityAdministrationMenu = applicationContext.getBean(GrantedAuthorityAdministrationMenu.class);
		menuStack.push(grantedAuthorityAdministrationMenu);
	}

	public void showMessageMenu(MessageThreadResource resource) {
		MessageMenu messageMenu = applicationContext.getBean(MessageMenu.class);
		messageMenu.setThread(resource);
		menuStack.push(messageMenu);
	}

	public void showConsoleClientSettingsMenu() {
		ConsoleClientSettingsMenu consoleClientSettingsMenu = applicationContext.getBean(ConsoleClientSettingsMenu.class);
		menuStack.push(consoleClientSettingsMenu);
	}
	
	public void showFolderMenu() {
		FolderMenu folderMenu = applicationContext.getBean(FolderMenu.class);
		menuStack.push(folderMenu);
	}

	public void showImageManagementMenu(){
		ImageManagementMenu imageManagementMenu = applicationContext.getBean(ImageManagementMenu.class);
		menuStack.push(imageManagementMenu);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void doneWithWelcome() {
		menuStack.pop();
	}
	
	public void displayImage(ImageMetaInfoResource imageMetaInfoResource) {
		BufferedImage image = null;
		try {
			image = SceneClient.getImageClient().getImage(imageMetaInfoResource);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TextWidget widget = new PictureWidget(new TextBoxSize(80, 30), image, new ShapeRasterizer());
		Raster raster = new ExtensibleCharacterRaster();
		widget.render(new RasterContext(raster));
		System.out.println(raster);
	}

}
