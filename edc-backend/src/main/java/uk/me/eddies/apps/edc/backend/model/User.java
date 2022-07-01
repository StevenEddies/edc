/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.model;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;

import at.favre.lib.crypto.bcrypt.BCrypt;
import uk.me.eddies.apps.edc.backend.api.model.UserDTO;

public class User implements Principal {

	private final String username;
	private final String passwordHash;
	private final ZoneId timezone;
	
	public User(String username, String passwordHash, ZoneId timezone) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.timezone = timezone;
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
	
	public LocalDate userNow() {
		return LocalDate.now(timezone);
	}

	@Override
	public String getName() {
		return getUsername();
	}
}
