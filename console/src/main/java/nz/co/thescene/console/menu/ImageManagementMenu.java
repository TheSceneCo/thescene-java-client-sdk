package nz.co.thescene.console.menu;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.hal.FileResource;
import nz.co.thescene.dto.json.hal.FolderResource;
import nz.co.thescene.dto.json.hal.ImageMetaInfoResource;
import nz.co.thescene.dto.json.hal.MemberResource;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageManagementMenu extends Menu {

	@Override
	protected void printMenu() {
		printHeader("Manage Images");
		System.out.println("1) Select an image");
		System.out.println("2) Upload image");
		System.out.println("3) Remove image");
		System.out.println("4) Get member photo");
		System.out.println("5) Set member photo");
		System.out.println("6) Remove member photo");
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) { // Select an image
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu imageSelector = new FileFolderSelectorMenu("Select folder: ", consoleUI,
					startFolder);
			imageSelector.setAllowSelectFile(true);
			ImageMetaInfoResource imageMetaInfoResource = SceneClient.getImageClient().getImageMetaInfo(imageSelector.select());
			consoleUI.displayImage(imageMetaInfoResource);
		} else if (userInput.equals("2")) { // Upload an image
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu folderSelector = new FileFolderSelectorMenu("Select a place for your image: ",
					consoleUI, startFolder);
			FileResource selectedFolder = folderSelector.select();

			String posterLocation = getUserInput("Enter poster location (jpg or png): ");
			
			List<String> paths = new ArrayList<>();
			paths.add(posterLocation);

			SceneClient.getImageClient().uploadImage((FolderResource) selectedFolder, paths);
		} else if (userInput.equals("3")) { // Remove an image
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu folderSelector = new FileFolderSelectorMenu("Select folder: ", consoleUI,
					startFolder);
			folderSelector.setAllowSelectFile(true);
			folderSelector.setAllowSelectFolder(false);
			FileResource fileResource = folderSelector.select();
			ImageMetaInfoResource imageMetaInfo = SceneClient.getImageClient().getImageMetaInfo(fileResource);
			SceneClient.getImageClient().deleteImage(imageMetaInfo);
		} else if (userInput.equals("4")) { // Get member photo
			member = SceneClient.getMemberClient().getMember(member);
			if (member.hasImage()) {
				ImageMetaInfoResource imageMetaInfoResource = SceneClient.getImageClient().getMemberPhoto(member);
				System.out.println("Image name: " + imageMetaInfoResource);
				consoleUI.displayImage(imageMetaInfoResource);
			} else { 
				System.out.println("This member has no image to view");
			}
		} else if (userInput.equals("5")) { // Set member photo
			List<FileResource> startFolder = new ArrayList<>();
			startFolder.add(SceneClient.getMemberClient().getRootFolder(member));
			FileFolderSelectorMenu imageSelector = new FileFolderSelectorMenu("Select folder: ", consoleUI,
					startFolder);
			imageSelector.setAllowSelectFile(true);
			imageSelector.setAllowSelectFolder(false);
			ImageMetaInfoResource imageMetaInfoResource = SceneClient.getImageClient().getImageMetaInfo(imageSelector.select());
			SceneClient.getImageClient().setMemberPhoto(member, imageMetaInfoResource);
			consoleUI.displayImage(imageMetaInfoResource);
		} else if (userInput.equals("6")) { // Remove member photo
			member = SceneClient.getMemberClient().getMember(member);
			if (member.hasImage()) {
				SceneClient.getImageClient().deleteMemberPhoto(member);
			} else {
				System.out.println("This member has no image to remove.");
			}
		}
	}

	@Override
	public void onError(SceneClientException e) {
		e.printStackTrace();
	}
}
