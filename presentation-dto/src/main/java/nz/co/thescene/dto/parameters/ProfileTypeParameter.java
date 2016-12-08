package nz.co.thescene.dto.parameters;

public enum ProfileTypeParameter {

	// lower case is non-standard but is used as spring hateoas requires
	// lowercase for hyperlink building.
	artists("ARTIST_TYPE", "artists"), venues("VENUE_TYPE", "venues"), events("EVENT_TYPE",
			"events"), suppliers("SUPPLIER_TYPE", "suppliers");

	private String sceneId, text;

	public String getText() {
		return text;
	}

	ProfileTypeParameter(String sceneId, String text) {
		this.text = text;
		this.sceneId = sceneId;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	@Override
	public String toString() {
		return text;
	}
}
