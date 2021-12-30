package uk.me.eddies.apps.edc.backend.model;

import uk.me.eddies.apps.edc.backend.api.model.UserDTO;

public class User {

	private final String username;
	private final String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean validatePassword(String inputPassword) {
		return password.equals(inputPassword);
	}
	
	public UserDTO toDTO() {
		return new UserDTO(username);
	}
}
