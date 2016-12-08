package nz.co.thescene.console.menu;

import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.client.errors.SceneClientValidationException;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.VenueResource;

public class VenueDetailMenu extends Menu {

	private VenueResource venue;

	public void setVenue(VenueResource venue) {
		this.venue = venue;
	}
	
	@Override
	protected void printMenu() {
		printHeader(venue.toString());
		//System.out.println("Followed by : " + service.getNewsService().getFollowing(venue));
		System.out.println("f) Follow / Unfollow " + venue);
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("f")) { 
		/*	if (isMemberAuthenticated()) { 
				service.getNewsService().follow(member, venue);
				System.out.println("You are now following " + venue);
			} else { 
				member = login();
				service.getNewsService().follow(member, venue);
			}*/
		} 
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
