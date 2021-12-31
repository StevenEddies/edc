package uk.me.eddies.apps.edc.backend.model;

import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.Collection;
import java.util.Map;

import uk.me.eddies.apps.edc.backend.configuration.GoalConfiguration;
import uk.me.eddies.apps.edc.backend.configuration.UserConfiguration;

public class ModelFactory {

	private ModelFactory() { }
	
	public static EdcModel build(Collection<UserConfiguration> users, Collection<GoalConfiguration> goals) {
		
		GoalsModel goalsModel = new GoalsModel(goals.stream().map(config -> new Goal(config.getName())).toList());
		
		Map<User, SingleUserModel> usersModel = users.stream()
				.collect(toUnmodifiableMap(
						config -> new User(config.getUsername(), config.getPasswordHash()),
						config -> new SingleUserModel(goalsModel)));
		
		return new EdcModel(goalsModel, usersModel);
	}
}
