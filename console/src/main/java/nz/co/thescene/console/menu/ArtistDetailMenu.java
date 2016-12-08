package nz.co.thescene.console.menu;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.client.errors.SceneClientValidationException;
import nz.co.thescene.dto.json.hal.ArtistResource;
import nz.co.thescene.dto.json.hal.MemberResource;

public class ArtistDetailMenu extends Menu {

	private ArtistResource artist;
	
	public void setArtist(ArtistResource artist) {
		this.artist = artist;
	}

	@Override
	protected void printMenu() {
		printHeader(artist.getName());
		//System.out.println("Category: " + service.getCategory(artist));
		//System.out.println("Playing in: " + service.getParticipantService().getAcceptedInvitations(artist));
		//System.out.println(service.getParticipantService().getAcceptedProposals(artist));
		//System.out.println("Followed by : " + service.getNewsService().getFollowing(artist));
		System.out.println("f) Follow / Unfollow " + artist.getName());
		System.out.println("b) Back to the previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("f")) { 
			if (SceneClient.getClientContext().hasToken()) { 
				//service.getNewsService().follow(member, artist);
				System.out.println("You are now following " + artist.getName());
			} else { 
				login();
				//service.getNewsService().follow(member, artist);
			}
		} 
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
