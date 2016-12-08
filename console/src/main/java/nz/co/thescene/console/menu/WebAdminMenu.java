package nz.co.thescene.console.menu;

import nz.co.thescene.dto.json.hal.TagResource;
import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.hal.MemberResource;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebAdminMenu extends Menu {

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) { // Content section template management
			consoleUI.showContentSectionTemplateMenu(member);
		} else if (userInput.equals("2")) { // Category management
			consoleUI.showCategoryAdminMenu(member);
		} else if (userInput.equals("3")) {
			consoleUI.showGrantedAuthorityAdministrationMenu(member);
		} else if (userInput.equals("4")) {
			String tagName = getUserInput("Enter tag name: ");
			List<TagResource> tags = new ArrayList<>(SceneClient.getTagClient().searchTags("event", tagName).getContent());
			if (tags.isEmpty()) {
				System.out.println("Tags doesn't exist");
			} else {
				SelectorMenu<TagResource> tagResourceSelectorMenu = new SelectorMenu<TagResource>(
						"Choose tag to delete: ", consoleUI, tags);
				SceneClient.getTagClient().delete(tagResourceSelectorMenu.select());
				System.out.println("Tag was delete successfully.");
			}
		} else if (userInput.equals("5")){
			consoleUI.showDataGeneratorMenu(member);
		} else { 
			handleInvalidInput();
		}
	}

	@Override
	public void printMenu() {
		printHeader("Admin Menu");
		System.out.println();
		System.out.println("1) Content section template management");
		System.out.println("2) Category management");
		System.out.println("3) Administer granted authorities"); 
		System.out.println("4) Delete tag");
		System.out.println("5) Data generator");
		System.out.println("b) Back to previous menu");
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
