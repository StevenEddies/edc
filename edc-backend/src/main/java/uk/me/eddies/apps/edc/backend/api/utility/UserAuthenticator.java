package uk.me.eddies.apps.edc.backend.api.utility;

import java.util.Optional;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import uk.me.eddies.apps.edc.backend.model.EdcModel;
import uk.me.eddies.apps.edc.backend.model.User;

public class UserAuthenticator implements Authenticator<BasicCredentials, User> {

	private final EdcModel model;
	
	public UserAuthenticator(EdcModel model) {
		this.model = model;
	}

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) {
		return model.lookupUser(credentials.getUsername())
				.flatMap(user -> requirePassword(user, credentials.getPassword()));
	}

	private Optional<User> requirePassword(User input, String password) {
		return input.validatePassword(password) ? Optional.of(input) : Optional.empty();
	}
}
