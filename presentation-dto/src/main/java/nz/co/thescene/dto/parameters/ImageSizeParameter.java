package nz.co.thescene.dto.parameters;

public enum ImageSizeParameter {

	LARGE("large"), THUMB("thumb"), POSTER("poster");

	private String text;

	public String getText() {
		return text;
	}

	ImageSizeParameter(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	
}
