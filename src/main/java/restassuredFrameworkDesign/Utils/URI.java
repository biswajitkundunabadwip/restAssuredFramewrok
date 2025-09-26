package restassuredFrameworkDesign.Utils;

public enum URI {
	POST_ADD_PLACE(ConfigPath.ADD_PLACE_RESOURCE_URI), PUT_UPDATE_PLACE(ConfigPath.UPDATE_PLACE_RESOURCE_URI),
	GET_PLACE(ConfigPath.GET_PLACE_RESOURCE_URI), DELETE_UPDATE_PLACE(ConfigPath.DELETE_PACE_RESOURCE_URI);

	private String URI;

	public String getURI() {
		return URI;
	}

	private URI(String uri) {
		this.URI = uri;
	}
}
