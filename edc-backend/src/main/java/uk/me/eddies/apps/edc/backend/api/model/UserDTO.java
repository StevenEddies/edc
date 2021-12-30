package uk.me.eddies.apps.edc.backend.api.model;

public class UserDTO {

	private String name;

	public UserDTO() {
	}
	
	public UserDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
