package nz.co.thescene.console.menu;


import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.client.errors.SceneClientValidationException;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.SupplierResource;

public class ServiceDetailMenu extends Menu {

	private SupplierResource serviceEntity;
	
	@Override
	protected void printMenu() {
		printHeader(serviceEntity.toString());
		//System.out.println("Followed by : " + service.getNewsService().getFollowing(serviceEntity));
		System.out.println("f) Follow / Unfollow " + serviceEntity);
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
/*		if (userInput.equals("f")) { 
			if (isMemberAuthenticated()) { 
				service.getNewsService().follow(member, serviceEntity);
				System.out.println("You are now following " + serviceEntity);
			} else { 
				member = login();
				service.getNewsService().follow(member, serviceEntity);
			}
		}*/ 
	}

	public void setService(SupplierResource serviceEntity) {
		this.serviceEntity = serviceEntity;
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
