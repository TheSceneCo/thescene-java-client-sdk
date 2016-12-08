package nz.co.thescene.console.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.FileResource;
import nz.co.thescene.dto.json.hal.FolderResource;

public class FileFolderSelectorMenu extends SelectorMenu<FileResource> {

	private FolderResource currentFolder = null;

	// Configure whether files should be shown or not
	private boolean allowSelectFile = false;

	private boolean allowSelectFolder = true;
	
	private String title;

	public FileFolderSelectorMenu(String title, ConsoleUI consoleUI, List<FileResource> folderResources) {
		super(title, consoleUI, folderResources);
		currentFolder = (FolderResource) folderResources.get(0);
		this.title = title;
	}

	@Override
	public FileResource select() {
		boolean stillDrillingDown = true;
		while (stillDrillingDown) {
			List<FileResource> selectableFolders = loadSelectableFolders(currentFolder);
			System.getProperty("line.separator");
			String subheading = System.getProperty("line.separator") + "Contents of '" + currentFolder.getName()
					+ "' folder:" + System.getProperty("line.separator") + System.getProperty("line.separator")
					+ "-TYPE-\t\t-NAME-";
			/*SelectorMenu<FileResource> selectorMenu = new SelectorMenu<>(
					"Select a folder (or ENTER accepts current folder): ", subheading, consoleUI, selectableFolders);*/
			SelectorMenu<FileResource> selectorMenu = new SelectorMenu<>(
					title, subheading, consoleUI, selectableFolders);
			FileResource selectedFolder = selectorMenu.select();

			if (allowSelectFolder) {
				stillDrillingDown = (selectedFolder == null || selectedFolder.getClass().equals(FileResource.class))
						? false : true;
			} else {
				stillDrillingDown = selectedFolder instanceof FileResource ? false : true;
			}

			if (stillDrillingDown) {
				currentFolder = (FolderResource) selectedFolder;
				// refresh the current folder to get its neighbouring
				// relationships
				currentFolder = SceneClient.getFolderClient().getFolder(currentFolder);
			} else if (selectedFolder instanceof FileResource) {
				return selectedFolder;
			}
		}
		return currentFolder;
	}

	private List<FileResource> loadSelectableFolders(FolderResource folderResource) {
		List<FileResource> selectableItems = new ArrayList<>();
		if (folderResource.hasParent()) {
			FolderResource parentFolder = SceneClient.getFolderClient().getParentFolder(folderResource);
			parentFolder.setName("..");
			selectableItems.add(parentFolder);
		}

		if (folderResource.hasChildFolders()) {
			selectableItems.addAll(SceneClient.getFolderClient().getChildFolders(folderResource).getContent());
		}

		if (allowSelectFile) {
			Collection<FileResource> files = SceneClient.getFolderClient().getFiles(folderResource).getContent();
			selectableItems.addAll(files);
		}

		return selectableItems;
	}

	public void setAllowSelectFolder(boolean allowSelectFolder) {
		this.allowSelectFolder = allowSelectFolder;
	}

	public void setAllowSelectFile(boolean allowSelectFile) {
		this.allowSelectFile = allowSelectFile;
	}

}
