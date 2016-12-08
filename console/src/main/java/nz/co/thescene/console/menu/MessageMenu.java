package nz.co.thescene.console.menu;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nz.co.thescene.client.SceneClient;
import nz.co.thescene.dto.json.MessageRequest;
import nz.co.thescene.dto.json.hal.MessageResource;
import nz.co.thescene.dto.json.hal.MessageThreadResource;
import org.springframework.stereotype.Component;

import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.dto.json.hal.MemberResource;

@Component
public class MessageMenu extends Menu {

	@Inject 
	private RecipientSelectorMenu recipientSelector;

	private MessageThreadResource thread;
	
	@Override
	protected void printMenu() {
		printHeader("Message Menu");
		System.out.println("1) Get messages");
		System.out.println("2) Send message");
		System.out.println("3) Get subscriber");
		System.out.println("4) Add subscriber");
		System.out.println("5) Delete subscriber");
		System.out.println("6) Send reply");
		System.out.println("7) Get replies");
		System.out.println("b) Back to the previous menu");
	}

	@Override
	protected void processUserInput(MemberResource member, String userInput) {
		if (userInput.equals("1")) {
			List<MessageResource> messages =
					new ArrayList<>(SceneClient.getMessagingClient().getMessagesInThread(thread).getContent());

			messages.forEach(System.out::println);

		} else if (userInput.equals("2")) {
			String content = getUserInput("Input message: ");
			MessageRequest request = new MessageRequest();
			request.setContent(content);

			SceneClient.getMessagingClient().sendMessage(thread, request);
		} else if (userInput.equals("3")) {
			List<MemberResource> subscribers =
					new ArrayList<>(SceneClient.getMessagingClient().getSubscribers(thread).getContent());

			subscribers.forEach(System.out::println);
		} else if (userInput.equals("4")) {
			String name = getUserInput("Input name or email of new subscriber");
			List<MemberResource> subscribers =
					new ArrayList<>(SceneClient.getMemberClient().search(name).getContent());

			SelectorMenu<MemberResource> selectorMenu =
					new SelectorMenu<>("Choose a member: ", consoleUI, subscribers);

			SceneClient.getMessagingClient().addSubscriber(thread, selectorMenu.select());
		} else if (userInput.equals("5")) {
			List<MemberResource> subscribers =
					new ArrayList<>(SceneClient.getMessagingClient().getSubscribers(thread).getContent());

			SelectorMenu<MemberResource> selectorMenu =
					new SelectorMenu<>("Choose a subscriber to delete: ", consoleUI, subscribers);

			SceneClient.getMessagingClient().deleteSubscriber(thread, selectorMenu.select());
		} else if (userInput.equals("6")) {

			List<MessageResource> messages =
					new ArrayList<>(SceneClient.getMessagingClient().getMessagesInThread(thread).getContent());

			SelectorMenu<MessageResource> selectorMenu =
					new SelectorMenu<>("Choose a message to send reply: ", consoleUI, messages);


			String reply = getUserInput("Please, enter message: ");
			SceneClient.getMessagingClient().replyInMessage(new MessageRequest(reply), selectorMenu.select());

		} else if (userInput.equals("7")) {

			List<MessageResource> messages =
					new ArrayList<>(SceneClient.getMessagingClient().getMessagesInThread(thread).getContent());

			SelectorMenu<MessageResource> selectorMenu =
					new SelectorMenu<>("Choose a message to get reply: ", consoleUI, messages);

			List<MessageResource> replies =
					new ArrayList<>(SceneClient.getMessagingClient().getReplies(selectorMenu.select()).getContent());
			replies.forEach(System.out::println);

		}
	}

	@Override
	public void onError(SceneClientException e) {

	}

	public void setThread(MessageThreadResource thread) {
		this.thread = thread;
	}
}
