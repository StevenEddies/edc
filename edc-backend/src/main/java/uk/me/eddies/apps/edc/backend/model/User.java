package uk.me.eddies.apps.edc.backend.model;

import java.security.Principal;

import at.favre.lib.crypto.bcrypt.BCrypt;
import uk.me.eddies.apps.edc.backend.api.model.UserDTO;

public class User implements Principal {

	private final String username;
	private final String passwordHash;
	
	public User(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean validatePassword(char[] inputPassword) {
		return BCrypt.verifyer().verify(inputPassword, passwordHash).verified;
	}
	
	public UserDTO toDTO() {
		return new UserDTO(username);
	}

	@Override
	public String getName() {
		return getUsername();
	}
}
