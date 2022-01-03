package uk.me.eddies.apps.edc.backend.model;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.me.eddies.apps.edc.backend.configuration.GoalConfiguration;
import uk.me.eddies.apps.edc.backend.configuration.UserConfiguration;

public class ModelFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(ModelFactory.class);

	private ModelFactory() { }
	
	public static EdcModel build(Collection<UserConfiguration> users, Collection<GoalConfiguration> goals) {
		
		GoalsModel goalsModel = new GoalsModel(goals.stream().map(config -> new Goal(config.getName())).toList());
		
		Map<User, SingleUserModel> usersModel = users.stream()
				.map(ModelFactory::readUserConfig)
				.collect(toUnmodifiableMap(
						identity(),
						user -> new SingleUserModel(goalsModel, user::userNow)));
		
		return new EdcModel(goalsModel, usersModel);
	}

	private static User readUserConfig(UserConfiguration config) {
		return new User(config.getUsername(), config.getPasswordHash(), identifyTimezone(config));
	}

	private static ZoneId identifyTimezone(UserConfiguration config) {
		if (config.getTimezone() == null) {
			LOG.info("User {} has no defined timezone. Using the default of {}.", config.getUsername(), ZoneId.systemDefault());
			return ZoneId.systemDefault();
		} else {
			try {
				return ZoneId.of(config.getTimezone());
			} catch (DateTimeException ex) {
				LOG.error("User {} has unsupported or invalid timezone {}. Using the default of {}.", config.getUsername(), config.getTimezone(), ZoneId.systemDefault());
				return ZoneId.systemDefault();
			}
		}
	}
}
