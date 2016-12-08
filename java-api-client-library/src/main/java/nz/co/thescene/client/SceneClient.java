package nz.co.thescene.client;

import nz.co.thescene.client.clients.*;
import nz.co.thescene.dto.json.hal.MemberResource;

public class SceneClient {

	private static MemberClient memberClient;
	private static EventClient eventClient;
	private static ImageClient imageClient;
	private static ContentClient contentClient;
	private static AdministrationClient administrationClient;
	private static FolderClient folderClient;
	private static LocationClient locationClient;
	private static TagClient tagClient;
	private static ProfileClient profileClient;
	private static CategoryClient categoryClient;
	private static MessageClient messageClient;

	public static APIClientContext configureClientContext() { 
		return APIClientContext.getInstance();
	}
	
	public static void logoutAndCleanup() { 
		APIClientContext.getInstance().clearAll();
	}
	
	public static MemberClient getMemberClient() {
		memberClient = new MemberClient();
		return memberClient;
	}

	public static EventClient getEventClient() {
		eventClient = new EventClient();
		return eventClient;
	}
	
	public static ImageClient getImageClient() {
		imageClient = new ImageClient();
		return imageClient;
	}

	public static TagClient getTagClient(){
		tagClient = new TagClient();
		return tagClient;
	}

	public static CategoryClient getCategoryClient(){
		categoryClient = new CategoryClient();
		return categoryClient;
	}

	public static APIClientContext getClientContext() {
		return APIClientContext.getInstance();
	}

	public static MemberResource memberInfo() {
		if (SceneClient.getClientContext().hasToken() == false) { 
			return null;
		} else {
			HttpService service = new HttpService();
			return service.memberInfo();
		}
	}

	public static ContentClient getContentClient() {
		contentClient = new ContentClient();
		return contentClient;
	}

	public static AdministrationClient getAdministrationClient() {
		administrationClient = new AdministrationClient();
		return administrationClient;
	}

	public static FolderClient getFolderClient(){
		folderClient = new FolderClient();
		return folderClient;
	}

	public static LocationClient getLocationClient() {
		locationClient = new LocationClient();
		return locationClient;
	}

	public static ProfileClient getProfileClient() {
		profileClient = new ProfileClient();
		return profileClient;
	}

	public static MessageClient getMessagingClient() {
		messageClient = new MessageClient();
		return messageClient;
	}
}