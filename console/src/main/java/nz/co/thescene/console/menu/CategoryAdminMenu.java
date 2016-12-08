package nz.co.thescene.console.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.CategoryRequest;
import nz.co.thescene.dto.json.CategoryUpdateRequest;
import nz.co.thescene.dto.json.hal.CategoryResource;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class CategoryAdminMenu extends Menu {

	private MemberResource creator;

	public void setCreator(MemberResource creator) {
		this.creator = creator;
	}

	@Override
	protected void printMenu() {
		printHeader("Manage Categories");
		System.out.println("1) Add category");
		System.out.println("2) Delete category");
		System.out.println("3) Move Category");
		System.out.println("4) View all categories");
		System.out.println("5) Update category");
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Select a parent
			// category", consoleUI,
			// service.getContentService().getAllCategoriesIncludingRoot());
			// CategoryDTO category = (CategoryDTO) selectorMenu.select();
			// service.getContentService().createCategory(getUserInput("Category
			// name: "), category);

			CategoryRequest categoryRequest = new CategoryRequest();
			categoryRequest.setName(getUserInput("Please, enter the name of category: "));
			Set<String> selectedProfileTypes = selectProfileTypes();
			categoryRequest.setProfileTypeIds(selectedProfileTypes);
			String isParentNull = getUserInput("Is this root category? [y|n] : ");

			if (!isParentNull.equals("y")) {
				List<CategoryResource> categories = new ArrayList<>(
						SceneClient.getCategoryClient().getRootCategories("events").getContent());
				CategorySelectorMenu categorySelectorMenu = new CategorySelectorMenu("Choose parent category",
						consoleUI, categories);
				categoryRequest.setParentId(categorySelectorMenu.select().getCategoryId());
			}

			SceneClient.getAdministrationClient().createCategory(categoryRequest);

		} else if (userInput.equals("2")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Select a category",
			// consoleUI, service.getContentService().getAllCategories());
			// CategoryDTO category = (CategoryDTO) selectorMenu.select();
			// boolean deleted =
			// service.getContentService().deleteCategory(category);
			/*
			 * if (deleted) { System.out.println("Category deleted."); } else {
			 * System.out.
			 * println("Category could not be deleted. Does it have a parent and children? Try re-arranging your categories so the category you want to delete has no parent or no children and try again."
			 * ); }
			 */

			List<CategoryResource> categories = new ArrayList<>(
					SceneClient.getCategoryClient().getRootCategories("events").getContent());
			CategorySelectorMenu categorySelectorMenu = new CategorySelectorMenu("Choose category to delete", consoleUI,
					categories);

			SceneClient.getAdministrationClient().deleteCategory(categorySelectorMenu.select());

		} else if (userInput.equals("3")) {
			// SelectorMenu selectorMenu = new SelectorMenu("Which category do
			// you want to move?", consoleUI,
			// service.getContentService().getAllCategories());
			// CategoryDTO child = (CategoryDTO) selectorMenu.select();
			// selectorMenu = new SelectorMenu("Select a new parent", consoleUI,
			// service.getContentService().getAllCategories());
			// CategoryDTO newParent = (CategoryDTO) selectorMenu.select();
			// service.getContentService().setCategoryParent(child, newParent);
		} else if (userInput.equals("4")) {
			// System.out.println(service.getContentService().getAllCategories());
		} else if (userInput.equals("5")) {

			List<CategoryResource> categories = new ArrayList<>(
					SceneClient.getCategoryClient().getRootCategories("events").getContent());
			CategorySelectorMenu categorySelectorMenu = new CategorySelectorMenu("Choose category", consoleUI,
					categories);
			CategoryResource categoryResource = categorySelectorMenu.select();
			boolean updated = false;
			CategoryUpdateRequest request = new CategoryUpdateRequest();
			updated = updateStringValue("Please, enter name for category: ", categoryResource.getName(),
					name -> request.setName(name)) || updated;

			if (updated == true) {
				SceneClient.getAdministrationClient().updateCategory(categoryResource, request);
			}

		} else {
			handleInvalidInput();
		}
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub

	}

}
