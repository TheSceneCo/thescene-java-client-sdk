package nz.co.thescene.console.menu;


import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.client.errors.SceneClientValidationException;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.CategoryResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;

public class EventParticipantMenu extends Menu {

	private ProfileResource eventParticpant;

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) { 
			//SelectorMenu selectorMenu = new SelectorMenu("Select an event", consoleUI, service.getEventService().loadAllEvents());
			//EventDTO event = (EventDTO)selectorMenu.select();
			//service.getParticipantService().sendProposal(eventParticpant, event);
			//System.out.println("You have sent a proposal for " + eventParticpant + " to participate in " + event);
		} else if (userInput.equals("2")) { 
			System.out.println("You have sent invitations to: ");
			//System.out.println(service.getParticipantService().getSentInvitations(eventParticpant));
		} else if (userInput.equals("3")) { 
			//SelectorMenu selectorMenu = new SelectorMenu("Select an event", consoleUI, service.getParticipantService().getSentInvitations(eventParticpant));
			//EventDTO event = (EventDTO)selectorMenu.select();
			//service.getParticipantService().cancelInvitation(eventParticpant, event);
		} else if (userInput.equals("4")) { 
			System.out.println(eventParticpant + " has been invited to:");
			//System.out.println(service.getParticipantService().getReceivedInvitations(eventParticpant));
		} else if (userInput.equals("5")) { 
			//SelectorMenu selectorMenu = new SelectorMenu("Select an invitation to accept", consoleUI, service.getParticipantService().getReceivedInvitations(eventParticpant));
			//Participant sender = (Participant) selectorMenu.select();
			//service.getParticipantService().acceptProposal(sender, eventParticpant);
			//System.out.println("You have accepted an invitation to  " + sender); 
		} else if (userInput.equals("6")) { 
			/*List<? extends BaseSceneNode> receivedInvitations = service.getParticipantService().getReceivedInvitations(eventParticpant);
			List<? extends BaseSceneNode> acceptedInvitations = service.getParticipantService().getAcceptedInvitations(eventParticpant);
			List<BaseSceneNode> declinableInvitations = new ArrayList<BaseSceneNode>();
			declinableInvitations.addAll(receivedInvitations);
			declinableInvitations.addAll(acceptedInvitations);
			SelectorMenu selectorMenu = new SelectorMenu("Select an invitation to decline", consoleUI, declinableInvitations);
			Participant sender = (Participant) selectorMenu.select();
			service.getParticipantService().declineReceivedInvitation(sender, eventParticpant);
			System.out.println("You have declined an invitation from " + sender);*/
		} else if (userInput.equals("7")) { 
			/*System.out.println("You have accepted invitations to: ");
			System.out.println(service.getParticipantService().getAcceptedInvitations(eventParticpant));
			System.out.println("The following have accepted proposals from you: ");
			System.out.println(service.getParticipantService().getAcceptedProposals(eventParticpant));*/
		} else if (userInput.equals("8")) { 
			consoleUI.showContentSectionMenu(eventParticpant);
		} else if (userInput.equals("9")) { 
			System.out.println("Select new category");
		} else { 
			handleInvalidInput();
		}
	}

	@Override
	public void printMenu() {
		printHeader("Manage " + eventParticpant);
		System.out.println("1) Send proposal to event");
		System.out.println("2) See sent proposals");
		System.out.println("3) Cancel sent proposals");
		System.out.println("4) See received invitations");
		System.out.println("5) Accept received invitations");
		System.out.println("6) Decline received invitations");
		System.out.println("7) View accepted proposals and invitations");
		System.out.println("8) Manage content");
		System.out.println("9) Categorize");
		System.out.println("b) Back to previous menu");
	}

	public void setEventParticipant(ProfileResource contentSectionHolder) {
		this.eventParticpant = contentSectionHolder;
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}
	
}
