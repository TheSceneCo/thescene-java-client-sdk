package nz.co.thescene.console.menu;

import java.util.Collection;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.hal.ContentSectionTemplateResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;

@Component
public class ContentSectionMenu extends Menu {

	private ProfileResource publishedIn;
	
	@Override
	protected void printMenu() {
		printHeader("Manage " + publishedIn.toString());
		System.out.println("1) Add content section");
		System.out.println("2) Remove content section");
		System.out.println("3) View all content sections");
		System.out.println("4) Edit a content section");
		System.out.println("b) Back to previous menu");
	}
	
	public void setPublisher(ProfileResource publishedIn) { 
		this.publishedIn = publishedIn;
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) {
			Resources<ContentSectionTemplateResource> contentSectionTemplates = SceneClient.getContentClient().getContentSectionTemplates(publishedIn);
			SelectorMenu<ContentSectionTemplateResource> selectorMenu = new SelectorMenu<>("Select a content section template", consoleUI, contentSectionTemplates);
			ContentSectionTemplateResource selectedContentSectionTemplateResource = selectorMenu.select();
			
			SceneClient.getContentClient().generateContentSection(publishedIn, selectedContentSectionTemplateResource.getSceneId());
			
			// System.out.println("Created new content section: " + newContentSection + " for " + publishedIn);
		} else if (userInput.equals("2")) {
			//SelectorMenu selectorMenu = new SelectorMenu(
			//		"Select a content section to delete", consoleUI,
			//		publishedIn.getContentSections());
			//ContentSectionDTO contentSection = (ContentSectionDTO) selectorMenu
			//		.select();
			//service.getContentService().deleteContentSection(contentSection);
		} else if (userInput.equals("3")) {
			Collection<ContentSectionTemplateResource> content = SceneClient.getAdministrationClient().getAllContentSectionTemplates().getContent();
			for (ContentSectionTemplateResource resource : content) { 
				System.out.println(resource.getTitle());
				System.out.println(resource.getInputTemplate());
			}
			
			//Collection<ContentSectionDTO> contentSections = publishedIn.getContentSections();
			//for (ContentSectionDTO contentSection : contentSections) {
			//	System.out.println("*** " + contentSection.getTitle() + " ***");
			//	System.out.println(contentSection.getContent());
			//	System.out.println();
			//}
		} else if (userInput.equals("4")) {
			//SelectorMenu selectorMenu = new SelectorMenu(
			//		"Select a content section to edit", consoleUI,
			//		publishedIn.getContentSections());
			//ContentSectionDTO contentSection = (ContentSectionDTO) selectorMenu
			//		.select();
			//System.out.println("*** " + contentSection.getTitle() + " ***");
			//System.out.println(contentSection.getContent());
			//System.out.println();
			//contentSection.setContent(getUserInput("New Content: "));
			//service.getContentService().updateContentSection(contentSection);
			//System.out.println("Edited results: ");
			//System.out.println("*** " + contentSection.getTitle() + " ***");
			//System.out.println(contentSection.getContent());
		} else { 
			handleInvalidInput();
		}
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
