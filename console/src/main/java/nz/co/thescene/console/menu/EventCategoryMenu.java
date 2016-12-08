package nz.co.thescene.console.menu;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.CategoryToProfileRequest;
import nz.co.thescene.dto.json.hal.CategoryResource;
import nz.co.thescene.dto.json.hal.EventResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class EventCategoryMenu extends Menu{

    private EventResource event;

    @Override
    public void printMenu() {
        printHeader("Mange category of event: " + event.getEventName() + ".");
        System.out.println();
        System.out.println("1) Get categories ");
        System.out.println("2) Set category ");
        System.out.println("3) Delete category");
        System.out.println("b) Back to previous menu");
    }

    @Override
    protected void processUserInput(MemberResource member, String userInput) {
        if (userInput.equals("1")) {
            List<CategoryResource> resources = new ArrayList<>(SceneClient.getProfileClient().getCategoriesOfProfile(event).getContent());
            if (resources == null || resources.isEmpty()) {

                System.out.println("No categories.");

            } else {
                for (CategoryResource resource : resources) {
                    List<CategoryResource> path = new LinkedList<>(SceneClient.getCategoryClient().getPathOfCategory(resource).getContent());
                    System.out.println("Category: " + resource);
                    System.out.println("Category path: ");
                    for (CategoryResource category : path) {
                        System.out.print("/" + category);
                    }
                }

            }
        } else if (userInput.equals("2")){
            List<CategoryResource> categories = new ArrayList<>(SceneClient.getCategoryClient().getRootCategories("events").getContent());
            CategorySelectorMenu categorySelectorMenu = new CategorySelectorMenu("Choose category ", consoleUI, categories);
            CategoryToProfileRequest request = new CategoryToProfileRequest();
            request.setCategoryId(categorySelectorMenu.select().getCategoryId());
            SceneClient.getProfileClient().addCategoryOfProfile(event, request);

        } else if (userInput.equals("3")){
            List<CategoryResource> resources = new LinkedList<>(SceneClient.getProfileClient().getCategoriesOfProfile(event).getContent());
            if (resources == null || resources.isEmpty()) {
                System.out.println("No categories.");
            } else {
                SelectorMenu<CategoryResource> categoryResourceSelectorMenu = new SelectorMenu<>("Choose category to remove: ", consoleUI, resources);
                CategoryResource resourceToDelete = categoryResourceSelectorMenu.select();
                SceneClient.getProfileClient().removeCategoryFromProfile(event, resourceToDelete);
            }


        }
    }

    @Override
    public void onError(SceneClientException e) {

    }

    public void setEvent(EventResource event) {
        this.event = event;
    }
}
