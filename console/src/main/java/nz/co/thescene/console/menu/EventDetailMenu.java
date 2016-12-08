package nz.co.thescene.console.menu;

import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.hal.EventResource;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class EventDetailMenu extends Menu {

	private EventResource event;
	
	public void setEvent(EventResource event) {
		this.event = event;
	}

	@Override
	protected void printMenu() {
		printHeader("Event Details for " + event.getEventName());
		printEventDetails();
		System.out.println("f) Follow / Unfollow");
		System.out.println("b) Back to previous menu");
	}

	private void printEventDetails() {
		System.out.println("Short description: " + event.getShortDescription());
		System.out.println("Door sales: " + event.getDoorSales());
		System.out.println("Sold out: " + event.getSoldOut());
		System.out.println("Paid event: " + event.getPaidEvent());
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("f")) { 
			if (SceneClient.getClientContext().hasToken()) { 
				//service.getNewsService().follow(member, event);
				System.out.println("You are now following " + event.toString());
			} else { 
				login();
				//service.getNewsService().follow(member, event);
			}
		} 
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
