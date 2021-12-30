package uk.me.eddies.apps.edc.backend.application;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import io.dropwizard.Configuration;
import uk.me.eddies.apps.edc.backend.configuration.UserConfiguration;

public class EdcBackendConfiguration extends Configuration {

	@Valid
	@UniqueElements
	@NotEmpty
	private Collection<UserConfiguration> users;

	public Collection<UserConfiguration> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserConfiguration> users) {
		this.users = users;
	}
}
