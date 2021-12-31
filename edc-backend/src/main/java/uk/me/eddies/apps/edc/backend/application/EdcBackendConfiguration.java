package uk.me.eddies.apps.edc.backend.application;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import io.dropwizard.Configuration;
import uk.me.eddies.apps.edc.backend.configuration.GoalConfiguration;
import uk.me.eddies.apps.edc.backend.configuration.UserConfiguration;

public class EdcBackendConfiguration extends Configuration {

	@Valid
	@UniqueElements
	@NotEmpty
	private Collection<UserConfiguration> users;

	@Valid
	@UniqueElements
	@NotEmpty
	private Collection<GoalConfiguration> goals;
	
	private String datastore;
	
	public Collection<UserConfiguration> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserConfiguration> users) {
		this.users = users;
	}
	
	public Collection<GoalConfiguration> getGoals() {
		return goals;
	}
	
	public void setGoals(Collection<GoalConfiguration> goals) {
		this.goals = goals;
	}
	
	public String getDatastore() {
		return datastore;
	}
	
	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}
}
