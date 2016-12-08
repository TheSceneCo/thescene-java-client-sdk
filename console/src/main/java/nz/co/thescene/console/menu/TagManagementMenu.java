package nz.co.thescene.console.menu;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.TagRequest;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import nz.co.thescene.dto.json.hal.TagResource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagManagementMenu extends Menu {

	private ProfileResource profile;
	
	@Override
	protected void printMenu() {
		printHeader("Manage Tags");
		System.out.println("1) Search tags");
		System.out.println("2) Get tags of profile");
		System.out.println("3) Create tag for profile");
		System.out.println("4) View all available tags");
		System.out.println("5) Delete tag from profile");
		System.out.println("6) Get tag");
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")){

			String[] profileTypes = { "events", "artists" };
			SelectorMenu<String> selector = new SelectorMenu<String>(
					"Choose profile type ", consoleUI,
					profileTypes);

			String tagName = getUserInput("Enter tag name: ");

			List<TagResource> tags = new ArrayList<>(SceneClient.getTagClient().searchTags(selector.select(), tagName).getContent());
			if(tags.isEmpty()){
				System.out.println("Tags doesn't exist");
			} else {
				tags.forEach(System.out::println);
			}
		} else if (userInput.equals("2")){
			List<TagResource> tags = new ArrayList<>(SceneClient.getTagClient().getProfileTags(profile).getContent());
			if(tags.isEmpty()){
				System.out.println("Tags doesn't exist");
			} else {
				tags.forEach(System.out::println);
			}
		} else if (userInput.equals("3")){
			TagRequest tagRequest = new TagRequest();
			String name = getUserInput("Enter tag name: ");
			tagRequest.setName(name);

			String description = getUserInput("Enter tag description: ");
			tagRequest.setDescription(description);

			TagResource tag = SceneClient.getTagClient().createTag(profile, tagRequest);

			System.out.println("Tag was created. " + tag);
		} else if (userInput.equals("4")){

			List<TagResource> tags =
					new ArrayList<>(SceneClient.getTagClient().getAllTags("events").getContent());
			if(tags.isEmpty()){
				System.out.println("Tags doesn't exist");
			} else {
				tags.forEach(System.out::println);
			}
		} else if (userInput.equals("5")){

			Resources<TagResource> tags = SceneClient.getTagClient().getProfileTags(profile);
			SelectorMenu<TagResource> selectorMenu = new SelectorMenu<TagResource>("Select a tag to delete",
					consoleUI, tags);
			TagResource selectedTag = selectorMenu.select();
			SceneClient.getTagClient().removeTagFromProfile(profile, selectedTag);
			System.out.println( selectedTag + " was deleted.");
		} else if (userInput.equals("6")){
			List<TagResource> tags = new ArrayList<>(SceneClient.getTagClient().getProfileTags(profile).getContent());
			SelectorMenu<TagResource> tagResourceSelectorMenu = new SelectorMenu<>("Choose tag: ", consoleUI, tags);
			System.out.println(SceneClient.getTagClient().getTag(tagResourceSelectorMenu.select()));
		}
	}

	public void setEvent(ProfileResource tagee) {
		this.profile = tagee;
	}

	@Override
	public void onError(SceneClientException e) {
		e.printStackTrace();
	}

}
