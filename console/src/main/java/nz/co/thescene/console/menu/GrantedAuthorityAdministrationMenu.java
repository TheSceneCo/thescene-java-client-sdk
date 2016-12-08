package nz.co.thescene.console.menu;

import java.util.Collection;

import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.GrantedAuthorityRequest;
import nz.co.thescene.dto.json.hal.GrantResource;
import nz.co.thescene.dto.json.hal.GrantedAuthorityResource;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class GrantedAuthorityAdministrationMenu extends Menu {

	@Override
	protected void printMenu() {
		printHeader("Administer granted authorities");
		System.out.println("1) Add granted authority to member");
		System.out.println("2) Remove granted authority from member");
		System.out.println("3) View granted authorities for member");
		System.out.println("4) Create granted authority");
		System.out.println("5) Delete granted authority");
		System.out.println("6) View all granted authorities");
		System.out.println("b) Back to previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {

		if (userInput.equals("1")) { // add granted authority to member
			SelectorMenu<GrantedAuthorityResource> grantedAuthoritySelector = new SelectorMenu<>(
					"Select a granted authority: ", consoleUI,
					SceneClient.getAdministrationClient().getAllGrantedAuthorities().getContent());
			
			SelectorMenu<MemberResource> memberSelector = new SelectorMenu<>("Select a member: ", consoleUI,
					SceneClient.getMemberClient().getMembers());
			
			SceneClient.getAdministrationClient().addGrantedAuthorityToMember(memberSelector.select(),
					grantedAuthoritySelector.select());
		} else if (userInput.equals("2")) { // remove granted authority from
											// member
			SelectorMenu<MemberResource> memberSelector = new SelectorMenu<>("Select a member: ", consoleUI,
					SceneClient.getMemberClient().getMembers().getContent());
			MemberResource selectedMember = memberSelector.select();
			SelectorMenu<GrantResource> grantedAuthoritySelector = new SelectorMenu<>(
					"Select a granted authority to remove: ", consoleUI,
					SceneClient.getMemberClient().getGrantsForMember(selectedMember).getContent());
			GrantResource grantResource = grantedAuthoritySelector.select();
			GrantResource grant = SceneClient.getAdministrationClient().removeGrantFromMember(grantResource);
			System.out.println("Removed " + grant + " from " + selectedMember);
		} else if (userInput.equals("3")) { // view granted authorities for member
			SelectorMenu<MemberResource> memberSelector = new SelectorMenu<>("Select a member: ", consoleUI, SceneClient.getMemberClient().getMembers());
			Collection<GrantedAuthorityResource> grantedAuthorities = SceneClient.getMemberClient()
					.getGrantedAuthoritiesForMember(memberSelector.select()).getContent();
			System.out.println(grantedAuthorities);
		} else if (userInput.equals("4")) { // Create GrantedAuthority
			GrantedAuthorityRequest request = new GrantedAuthorityRequest(getUserInput("Granted authority name: "));
			GrantedAuthorityResource grantedAuthority = SceneClient.getAdministrationClient()
					.createGrantedAuthority(request);
			System.out.println("Created granted authority " + grantedAuthority);
		} else if (userInput.equals("5")) { // Delete GrantedAuthority
			SelectorMenu<GrantedAuthorityResource> grantedAuthoritySelector = new SelectorMenu<>(
					"Select a granted authority: ", consoleUI,
					SceneClient.getAdministrationClient().getAllGrantedAuthorities().getContent());
			GrantedAuthorityResource toDelete = grantedAuthoritySelector.select();
			SceneClient.getAdministrationClient().deleteGrantedAuthority(toDelete);
			System.out.println("Deleted granted authority " + toDelete);
		} else if (userInput.equals("6")) {  
			Collection<GrantedAuthorityResource> allGrantedAuthorities = SceneClient.getAdministrationClient().getAllGrantedAuthorities().getContent();
			System.out.println(allGrantedAuthorities);
		} else {
			handleInvalidInput();
		}

	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub

	}

}
