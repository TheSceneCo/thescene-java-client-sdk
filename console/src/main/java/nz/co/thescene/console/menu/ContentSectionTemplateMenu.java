package nz.co.thescene.console.menu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.clients.AdministrationClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.ContentSectionTemplateRequest;
import nz.co.thescene.dto.json.hal.ContentSectionTemplateResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileTypeResource;

@Component
public class ContentSectionTemplateMenu extends Menu {

	private MemberResource creator;

	private AdministrationClient administrationClient = SceneClient.getAdministrationClient();

	public void setCreator(MemberResource creator) {
		this.creator = creator;
	}

	@Override
	protected void printMenu() {
		printHeader("Manage Content Section Templates");
		System.out.println("1) Add content section template");
		System.out.println("2) Remove content section template");
		System.out.println("3) View a content section template");
		System.out.println("4) View all content section templates");
		System.out.println("5) Edit a content section template");
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) { // create content section template
			ContentSectionTemplateRequest request = new ContentSectionTemplateRequest();
			request.setTitle(getUserInput("Content section template name: "));
			request.setInputTemplate(getUserInput("Content section input template: "));
			request = setOutputTemplatesInRequest(request);
			Set<String> selectedProfileTypes = selectProfileTypes();
			request.setProfileTypes(selectedProfileTypes);
			administrationClient.createContentSectionTemplate(request);
		} else if (userInput.equals("2")) { // remove content section template
			SelectorMenu<ContentSectionTemplateResource> contentSectionTemplateSelector = new SelectorMenu<>(
					"Select a template to delete", consoleUI,
					administrationClient.getAllContentSectionTemplates().getContent());
			ContentSectionTemplateResource contentSectionTemplate = contentSectionTemplateSelector.select();
			ContentSectionTemplateResource deletedContentSectionTemplate = administrationClient
					.deleteContentSectionTemplate(contentSectionTemplate);
			System.out.println("Deleted " + deletedContentSectionTemplate.getTitle());
		} else if (userInput.equals("3")) { // View a content section template
			SelectorMenu<ContentSectionTemplateResource> contentSectionTemplateSelector = new SelectorMenu<>(
					"Select a template to view", consoleUI, administrationClient.getAllContentSectionTemplates());
			ContentSectionTemplateResource contentSectionTemplate = contentSectionTemplateSelector.select();
			System.out.println("Title: " + contentSectionTemplate.getTitle());
			System.out.println("Input template: " + contentSectionTemplate.getInputTemplate());
		} else if (userInput.equals("4")) { // get all content section templates
			System.out.println(administrationClient.getAllContentSectionTemplates());
		} else if (userInput.equals("5")) { // update content section template
			SelectorMenu<ContentSectionTemplateResource> contentSectionTemplateSelector = new SelectorMenu<>(
					"Select a template to update", consoleUI, administrationClient.getAllContentSectionTemplates());
			ContentSectionTemplateResource contentSectionTemplateResource = contentSectionTemplateSelector.select();
			boolean updated = false;
			System.out.println("Update Content Section Template properties. Type new property or enter to accept.");
			updated = updateStringValue("Title: ", contentSectionTemplateResource.getTitle(),
					title -> contentSectionTemplateResource.setTitle(title)) || updated;
			updated = updateStringValue("Input template: ", contentSectionTemplateResource.getInputTemplate(),
					defaultContent -> contentSectionTemplateResource.setInputTemplate(defaultContent)) || updated;
			List<ProfileTypeResource> profileTypes = administrationClient.getProfileTypesForContentSectionTemplate(contentSectionTemplateResource);
			
			printCommaSeparatedList(profileTypes);
			String[] yesOrNo = {"Yes", "No"};
			SelectorMenu<String> selectorMenu = new SelectorMenu<String>("Updated profile types (yes or no)?", consoleUI, yesOrNo);
			
			Set<String> selectedProfileTypes = new HashSet<String>();
			if (selectorMenu.select().equals("Yes")) { 
				selectedProfileTypes = selectProfileTypes();
				updated = true;
			}
			
			if (updated) {
				SceneClient.getAdministrationClient().updateContentSectionTemplate(contentSectionTemplateResource, selectedProfileTypes);
			}
		} else {
			handleInvalidInput();
		}
	}

	private ContentSectionTemplateRequest setOutputTemplatesInRequest(ContentSectionTemplateRequest request) {

		int previousSize = 0;
		do {
			previousSize = request.getOutputTemplates().size();
			
			String outputTemplateIdentifier = getUserInput("Output template identifier: ");
			if (outputTemplateIdentifier.isEmpty()) { // the user must have chosen to not add any more
				break;
			}
			String outputTemplate = getUserInput("Output template: ");
			if (outputTemplate != null) {
				request.addOutputTemplate(outputTemplateIdentifier, outputTemplate);
			}
		} while (request.getOutputTemplates().size() != previousSize);
		return request;
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub

	}

}