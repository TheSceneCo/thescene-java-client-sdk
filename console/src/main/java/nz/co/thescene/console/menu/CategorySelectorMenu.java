package nz.co.thescene.console.menu;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.CategoryResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategorySelectorMenu extends SelectorMenu<CategoryResource>{

	private CategoryResource currentCategory = null;

	List<CategoryResource> categoryResources = new ArrayList<>();

	// Configure
	private boolean allowSelectFile = false;

	private boolean allowSelectCategory = true;

	private String title;

	public CategorySelectorMenu(String title, ConsoleUI consoleUI, List<CategoryResource> categoryResources) {
		super(title, consoleUI, categoryResources);
		this.categoryResources = categoryResources;
		this.title = title;
	}

	@Override
	public CategoryResource select() {
		boolean stillDrillingDown = true;
		while (stillDrillingDown) {
			String subheading = System.getProperty("line.separator") + "Root Categories"
					+ System.getProperty("line.separator") + System.getProperty("line.separator")
					+ "-TYPE-\t\t-NAME-";
			if (currentCategory != null) {
				categoryResources = loadSelectableCategory(currentCategory);
				System.getProperty("line.separator");
				subheading = System.getProperty("line.separator") + "Contents of '" + currentCategory.getName()
						+ "' category:" + System.getProperty("line.separator") + System.getProperty("line.separator")
						+ "-TYPE-\t\t-NAME-";

			}

			SelectorMenu<CategoryResource> selectorMenu = new SelectorMenu<>(
					title, subheading, consoleUI, categoryResources);
			CategoryResource selectedCategory = selectorMenu.select();

			if (allowSelectCategory) {
				stillDrillingDown = (selectedCategory == null)
						? false : true;
			} else {
				stillDrillingDown = selectedCategory instanceof CategoryResource ? false : true;
			}

			if (stillDrillingDown) {
				currentCategory = (CategoryResource) selectedCategory;
				// refresh the current folder to get its neighbouring
				// relationships
				currentCategory = SceneClient.getCategoryClient().getCategory(currentCategory);
			} else if (selectedCategory instanceof CategoryResource) {
				return selectedCategory;
			}
		}
		return currentCategory;
	}

	private List<CategoryResource> loadSelectableCategory(CategoryResource categoryResource) {
		List<CategoryResource> selectableItems = new ArrayList<>();
		if (categoryResource.hasParent()) {
			CategoryResource parentCategory = SceneClient.getCategoryClient().getParentOfCategory(categoryResource);
			parentCategory.setName("..");
			selectableItems.add(parentCategory);
		}

		if (categoryResource.hasChildren()) {
			selectableItems.addAll(SceneClient.getCategoryClient().getChildrenOfCategory(categoryResource).getContent());
		}

		return selectableItems;
	}

	public void setAllowSelectFolder(boolean allowSelectCategory) {
		this.allowSelectCategory = allowSelectCategory;
	}

	public void setAllowSelectFile(boolean allowSelectFile) {
		this.allowSelectFile = allowSelectFile;
	}
	
}
