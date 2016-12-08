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
public class ConsoleClientSettingsMenu extends Menu {

	@Override
	protected void printMenu() {
		printHeader("Admin Menu");
		System.out.println("Current API connection is to " + SceneClient.getClientContext().getBaseUrl());
		System.out.println();
		System.out.println("1) Use API at http://localhost8080/api");
		System.out.println("2) Use API at http://thescene.co/api");
		System.out.println("3) Use API at user defined url");
		System.out.println("4) Set logging level");
		System.out.println("b) Back to previous menu");		
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		
		if (userInput.equals("1")) { 
			SceneClient.getClientContext().clearCachedResources();
			SceneClient.getClientContext().setClientCredentials("live-test", "test").setBaseUrl("http://localhost:8080/api");
			SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
			SceneClient.memberInfo();
		} else if (userInput.equals("2")) { 
			SceneClient.getClientContext().clearCachedResources();
			SceneClient.getClientContext().setClientCredentials("live-test", "test").setBaseUrl("http://thescene.co/api");
			SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
			SceneClient.memberInfo();
		} else if (userInput.equals("3")) { 
			SceneClient.getClientContext().clearCachedResources();
			SceneClient.getClientContext().setBaseUrl(getUserInput("User defined base url: "));
			SceneClient.memberInfo();
		} else if (userInput.equals("4")) { 
			Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
			Level[] logLevels = {Level.OFF, Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.ALL};
			SelectorMenu<Level> selectorMenu = new SelectorMenu<>("Select a log level", consoleUI, logLevels);
			root.setLevel(selectorMenu.select());
		} else { 
			handleInvalidInput();
		}		
	}

	@Override
	public void onError(SceneClientException e) {
		// TODO Auto-generated method stub
		
	}
}
