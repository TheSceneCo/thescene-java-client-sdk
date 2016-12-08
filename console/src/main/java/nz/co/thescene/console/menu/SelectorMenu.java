package nz.co.thescene.console.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.springframework.hateoas.ResourceSupport;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.MemberResource;

public class SelectorMenu<T> {

	private Map<String, T> aliases;
	protected String title;
	private Iterable<T> customMenuOptions;
	private T selectedItem;
	private Scanner scanner;
	private MemberResource member;
	protected String subheading;
	
	protected ConsoleUI consoleUI;
	
	public SelectorMenu(String title, ConsoleUI consoleUI, Iterable<T> customMenuOptions) {
		initialiseWithIterable(title, consoleUI, customMenuOptions);
	}
	
	public SelectorMenu(String title, String subheading, ConsoleUI consoleUI, Iterable<T> customMenuOptions) { 
		this.subheading = subheading;
		initialiseWithIterable(title, consoleUI, customMenuOptions);
	}

	private void initialiseWithIterable(String title, ConsoleUI consoleUI, Iterable<T> customMenuOptions) {
		this.title = title;
		this.customMenuOptions = customMenuOptions;
		this.scanner = consoleUI.getScanner();
		this.consoleUI = consoleUI;
		this.member = consoleUI.getLoggedInMember();
	}
	
	public SelectorMenu(String title, ConsoleUI consoleUI, T[] customMenuOptions) { 
		initialiseWithArray(title, consoleUI, customMenuOptions);
	}
	
	public SelectorMenu(String title, String subheading, ConsoleUI consoleUI, T[] customMenuOptions) { 
		this.subheading = subheading;
		initialiseWithArray(title, consoleUI, customMenuOptions);
	}
	
	private void initialiseWithArray(String title, ConsoleUI consoleUI, T[] customMenuOptions) {
		this.title = title;
		this.customMenuOptions = Arrays.asList(customMenuOptions);
		this.scanner = consoleUI.getScanner();
		this.consoleUI = consoleUI;
		this.member = consoleUI.getLoggedInMember();
	}

	public T select() { 
		printMenu(customMenuOptions);
		boolean inputIsValid = processUserInput(getUserInput());
		if (!inputIsValid) { 
			selectedItem = select();
		}
		return selectedItem;
	}
	
	private boolean processUserInput(String userInput) {
		if (userInput.isEmpty()) { 
			return true;
		}
		Object possibleSelection = aliases.get(userInput);
		if (possibleSelection == null) { 
			handleInvalidInput(userInput, aliases);
			return false;
		} else { 
			selectedItem = aliases.get(userInput);
			return true;
		}
	}
	
	private void printMenu(Iterable<T> events) {
		printHeader();
		aliases = createAliases(events);
		
		List<String> keys = getSortedAliasKeys(aliases);
		
		for (String key : keys) { 
			System.out.println(key + ") " + aliases.get(key));
		} 
	}
	
	private Map<String, T> createAliases(Iterable<T> graphItems) { 
		Map<String, T> idAliases = new HashMap<String, T>();
		int aliasId = 1;
		for (T graphItem : graphItems) { 
			idAliases.put("" + aliasId, graphItem);
			aliasId++;
		}
		return idAliases;
	}
	
	private List<String> getSortedAliasKeys(Map<String, T> aliases) {
		Set<String> keySet = aliases.keySet();
		List<String> keys = new ArrayList<String>();
		for (String key : keySet) { 
			keys.add(key);
		}
		
		Collections.sort(keys);
		return keys;
	}
	
	private String getUserInput() {
		return scanner.nextLine();
	}
	
	private void handleInvalidInput(String userInput, Map<String, T> customMenuOptions) { 
		if (!customMenuOptions.containsKey(userInput)) {
			System.out.println(userInput + "'" + userInput + "' is not valid input");
		}
	}
	
	protected void printHeader() { 
		System.out.println();
		System.out.println("***   " + title + "   ***");
		if (SceneClient.getClientContext().hasToken()) { 
			System.out.println("[User: " + member + "]");
		}
		System.out.println();
		if (subheading != null) { 
			System.out.println(subheading);
		}
	}

}