package uk.me.eddies.apps.edc.backend.model;

import static java.util.Collections.unmodifiableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class EdcModel {

	private final GoalsModel goals;
	private final Map<String, User> userNames;
	private final Map<User, SingleUserModel> users;
	
	public EdcModel(GoalsModel goals, Map<User, SingleUserModel> users) {
		this.goals = goals;
		this.userNames = users.keySet().stream()
				.collect(toUnmodifiableMap(User::getUsername, identity()));
		this.users = unmodifiableMap(new LinkedHashMap<>(users));
	}

	public GoalsModel getGoals() {
		return goals;
	}
	
	public Collection<User> getAllUsers() {
		return users.keySet();
	}
	
	public Optional<User> lookupUser(String username) {
		return Optional.ofNullable(userNames.get(username));
	}
	
	public SingleUserModel lookupForUser(String username) {
		return users.get(lookupUser(username)
				.orElseThrow(() -> new IllegalArgumentException("Unknown user " + username)));
	}
}
