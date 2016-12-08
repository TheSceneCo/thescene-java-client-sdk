package nz.co.thescene.console.menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.console.ConsoleUI;
import nz.co.thescene.dto.json.hal.MemberResource;

public abstract class Menu {

	@Inject
	protected ConsoleUI consoleUI;

	protected String userInput;

	protected abstract void printMenu();

	protected abstract void processUserInput(MemberResource member, String userInput);

	public abstract void onError(SceneClientException e);

	protected void login() {
		System.out.println("Connect to " + SceneClient.getClientContext().getBaseUrl());
		System.out.println();
		SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(
				getUserInput("Enter your email address: "), getUserInput("Enter your password: "));
		consoleUI.login(SceneClient.memberInfo());
	}

	protected void logout() {
		consoleUI.logout();
	}

	protected boolean isLoggedIn() {
		return consoleUI.isLoggedIn();
	}

	public String printMenuAndGetUserInput() {
		printMenu();
		userInput = getUserInput();
		return userInput;
	}

	protected void printHeader(String title) {
		System.out.println();
		System.out.println("***   " + title + "   ***");
		printLoggedInMember();
		System.out.println();
	}

	protected void printSubHeader(String title) {
		System.out.println();
		System.out.println(">>>   " + title);
		System.out.println();
	}

	protected void printInfoMessage(String message) {
		printSubHeader(message);
	}

	protected void printLoggedInMember() {
		System.out.println("[User: " + consoleUI.getLoggedInMember() + "]");
	}

	protected String getUserInput() {
		return consoleUI.getScanner().nextLine();
	}

	protected String getUserInput(String prompt) {
		System.out.print(prompt);
		return getUserInput();
	}

	public String peekAtUserInput() {
		return userInput;
	}

	public void processUserInput() {
		processUserInput(consoleUI.getLoggedInMember(), userInput);
	}

	/**
	 * This method is used for validating user input and prints an appropriate
	 * message when invalid options are passed.
	 * 
	 * @param customMenuOptions
	 *            Any custom menu options that have been used by the caller.
	 *            Accepts null.
	 */
	protected void handleInvalidInput() {
		if (!userInput.equals("x") // global exit
				&& !userInput.equals("sa") // global super administrator
				&& !userInput.equals("b")) { // global back
			System.out.println(userInput + " is not valid input");
		}
	}

	protected boolean updateBooleanValue(String promptText, Boolean existingValue, Consumer<Boolean> updateMethod) {
		String value = getUserInput(promptText + " [" + existingValue + "]: ");
		if (!value.isEmpty()) {
			try {
				updateMethod.accept(Boolean.parseBoolean(value));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error: You must enter true, false or nothing.");
				updateBooleanValue(promptText, existingValue, updateMethod);
			}
			return true;
		} else {
			return false;
		}
	}

	protected boolean updateTimeValue(String promptText, Long existingValue, Consumer<Long> updateMethod) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
		String formattedDate = null;
		if (existingValue != null) {
			formattedDate = dateFormatter.format(existingValue);
		}
		String value = getUserInput(promptText + " [" + formattedDate + "]: ");
		if (!value.isEmpty()) {
			Date date = null;
			try {

				date = dateFormatter.parse(value);
				updateMethod.accept(date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Error: You must enter a date in the format dd-mm-yy).");
				updateTimeValue(promptText, existingValue, updateMethod);
			}

			return true;
		} else {
			return false;
		}

	}

	protected boolean updateStringValue(String promptText, String existingValue, Consumer<String> updateMethod) {
		String value = getUserInput(promptText + "[" + existingValue + "]: ");
		if (!value.isEmpty()) {
			updateMethod.accept(value);
			return true;
		} else {
			return false;
		}
	}

	protected Set<String> selectProfileTypes() {
		Set<String> selectableProfileTypes = new HashSet<>(
				Arrays.asList("EVENT_TYPE", "ARTIST_TYPE", "VENUE_TYPE", "SUPPLIER_TYPE"));
		Set<String> selectedProfileTypes = new HashSet<String>();

		int previousSize = 0;
		do {
			previousSize = selectedProfileTypes.size();
			SelectorMenu<String> profileTypeSelector = new SelectorMenu<String>(
					"Select a profile type or Enter to continue", consoleUI, selectableProfileTypes);
			String profileType = profileTypeSelector.select();
			if (profileType != null) {
				selectableProfileTypes.remove(profileType);
				selectedProfileTypes.add(profileType);
			}
			System.out.println("Selected profile types: " + selectedProfileTypes);
		} while (selectedProfileTypes.size() != previousSize && !selectableProfileTypes.isEmpty());

		return selectedProfileTypes;
	}

	protected <T> void printCommaSeparatedList(List<T> items) {
		System.out.println("Current profile types are: ");

		if (items.size() >= 1) {
			System.out.print(items.get(0));
		}

		for (int i = 1; i < items.size(); i++) {
			System.out.println(", " + items.get(i));
		}

		System.out.print(":");
	}

}
