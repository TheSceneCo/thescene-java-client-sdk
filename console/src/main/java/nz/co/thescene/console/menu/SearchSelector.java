package nz.co.thescene.console.menu;

import java.util.function.Function;

import org.springframework.hateoas.Resources;

public class SearchSelector<T> {

	private Function<String, Resources<T>> searchFunction;
	
	private Function<String, String> getUserInput;
	
	public SearchSelector(Function<String, Resources<T>> searchFunction, Function<String, String> getUserInput) {
		this.searchFunction = searchFunction;
		this.getUserInput = getUserInput;
	}

	public T search() {
		Resources<T> searchResults = null;
		do {
			searchResults = searchFunction.apply(getUserInput.apply("Enter search term: "));
		} while (searchResults != null && !searchResults.getContent().isEmpty() && searchResults.getContent().size() != 1);
		return searchResults.getContent().iterator().next();
	}
}
