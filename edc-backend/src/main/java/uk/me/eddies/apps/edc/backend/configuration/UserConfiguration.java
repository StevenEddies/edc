package uk.me.eddies.apps.edc.backend.configuration;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserConfiguration {
	
	@NotBlank
	@Pattern(regexp="^[a-z0-9]+$")
	private String username;
	
	@NotBlank
	private String passwordHash;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserConfiguration other = (UserConfiguration) obj;
		return Objects.equals(username, other.username);
	}
}
