package nz.co.thescene.console.menu;

import java.util.List;
import org.springframework.stereotype.Component;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class RecipientSelectorMenu {
	
	private ConsoleUI consoleUI;
	
	/*@Inject
	private MemberAccountRepository memberAccountRepository;*/
	
	public RecipientSelectorMenu() { 
	}
	
	public List<MemberResource> select() {
		
		/*List<MemberDTO> selectedAccounts = new ArrayList<MemberDTO>();
		
		List<MemberDTO> allMembers = SceneService.makeList(memberAccountRepository.findAll());
		allMembers.removeAll(selectedAccounts);
		boolean done = false;
		while (!done) { 
			SelectorMenu selectorMenu = new SelectorMenu("Select a recipient: ", consoleUI, allMembers);
			MemberDTO selectedMember = (Member)selectorMenu.select();
			if (selectedMember.getSceneId().equals("Done")) { 
				done = true;
			} else { 
				selectedAccounts.add(selectedMember);
			}
			allMembers = null;
			allMembers = SceneService.makeList(memberAccountRepository.findAll());
			MemberDTO proxyMember = new MemberDTO(UniqueIDGenerator.randomID(), "John", "Doe");
			proxyMember.setFirstName("Done");
			allMembers.add(proxyMember);
		}
		return selectedAccounts;*/
		return null;
	}
	
}

