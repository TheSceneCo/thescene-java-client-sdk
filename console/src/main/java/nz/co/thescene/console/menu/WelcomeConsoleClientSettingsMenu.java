package nz.co.thescene.console.menu;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.client.errors.SceneClientValidationException;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class WelcomeConsoleClientSettingsMenu extends Menu {
	
	@Override
	protected void printMenu() {
		System.out.println();
		System.out.println("***   " + "To get started, choose an API to connect to" + "   ***");
		System.out.println();
		System.out.println("You can come back to these options with the client settings menu.");
		System.out.println("Current API connection is to " + SceneClient.getClientContext().getBaseUrl());
		System.out.println();
		System.out.println("1) Use API at http://localhost:8080/api");
		System.out.println("2) Use API at http://gateway.thescene.co/api");
		System.out.println("3) Use API at user defined url");
	}
	
	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) { 
			SceneClient.getClientContext().setClientCredentials("live-test", "test").setBaseUrl("http://localhost:8080/api");
			SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
			SceneClient.memberInfo();
			consoleUI.doneWithWelcome();
		} else if (userInput.equals("2")) { 
			SceneClient.getClientContext().setClientCredentials("live-test", "test").setBaseUrl("http://gateway.thescene.co/api");
			SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
			SceneClient.memberInfo();
			consoleUI.doneWithWelcome();
		} else if (userInput.equals("3")) { 
			SceneClient.getClientContext().setBaseUrl(getUserInput("User defined base url: "));
			SceneClient.memberInfo();
			consoleUI.doneWithWelcome();
		} else { 
			handleInvalidInput();
		}		
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}

}
