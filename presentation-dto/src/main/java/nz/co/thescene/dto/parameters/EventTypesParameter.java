package nz.co.thescene.dto.parameters;

public enum EventTypesParameter {

	BOTH("both"), PUBLIC("public"), COLLABORATING("collaborating");

	private String text;

	public String getText() {
		return text;
	}

	EventTypesParameter(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
