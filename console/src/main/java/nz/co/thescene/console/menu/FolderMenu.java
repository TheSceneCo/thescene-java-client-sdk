package nz.co.thescene.console.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.FolderRequest;
import nz.co.thescene.dto.json.hal.FileResource;
import nz.co.thescene.dto.json.hal.FolderResource;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class FolderMenu extends Menu {

	@Override
	protected void printMenu() {
		printHeader("Manage Folders and Files");
		System.out.println("1) Add folder");
		System.out.println("2) Remove folder");
		System.out.println("3) Rename folder");
		System.out.println("4) Get folder and files");
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if(userInput.equals("1")){
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu folderSelector = new FileFolderSelectorMenu("Select a place for your new folder (Enter accepts): ", consoleUI, startFolder);
			folderSelector.setAllowSelectFile(false);
			FileResource selectedFolder = folderSelector.select();
			String name = getUserInput("Enter a name for your folder: ");
			FolderRequest folderRequest = new FolderRequest(name, selectedFolder.getFileId());
			FolderResource createdFolder = SceneClient.getMemberClient().createFolder(member, folderRequest);
			printInfoMessage("Created folder '" + createdFolder.getName() + "'.");
		} else if(userInput.equals("2")) {
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu folderSelector = new FileFolderSelectorMenu("Select folder to delete: (Enter accepts)", consoleUI, startFolder);
			FileResource selectedFolder = folderSelector.select();
			FileResource deletedFolder = SceneClient.getFolderClient().delete(selectedFolder);
			printInfoMessage("Removed folder '" + deletedFolder.getName() + "'.");
		} else if (userInput.equals("3")) { 
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu folderSelector = new FileFolderSelectorMenu("Select folder to rename (Enter accepts): ", consoleUI, startFolder);
			FolderResource selectedFolder = (FolderResource) folderSelector.select();
			String oldName = selectedFolder.getName();
			selectedFolder.setName(getUserInput("Enter a new name for folder '" + selectedFolder.getName() + "': "));

			SceneClient.getFolderClient().updateFolder(selectedFolder);
			printInfoMessage("Renamed folder " + oldName + " to " + selectedFolder.getName() + ".");
		} else if (userInput.equals("4")) {
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu folderSelector = new FileFolderSelectorMenu("Select folder : ", consoleUI, startFolder);
			FolderResource selectedFolder = (FolderResource) folderSelector.select();
			List<FileResource> files = new ArrayList<>(SceneClient.getFolderClient().getFilesAndFolders(selectedFolder).getContent());
			if(files != null || !files.isEmpty()){
				files.forEach(System.out::println);
			} else {
				System.out.println("There are no files and folders");
			}
		}
	}

	@Override
	public void onError(SceneClientException e) {
		e.printStackTrace();
	}

}
