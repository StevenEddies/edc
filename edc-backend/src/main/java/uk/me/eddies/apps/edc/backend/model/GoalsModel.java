/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.model;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class GoalsModel {

	private final Map<String, Goal> goals;
	
	public GoalsModel(Collection<Goal> goals) {
		this.goals = goals.stream()
				.sorted(comparing(Goal::getName))
				.collect(toUnmodifiableMap(Goal::getName, identity()));
	}
	
	public Optional<Goal> lookupGoal(String name) {
		return Optional.ofNullable(goals.get(name));
	}
	
	public Map<String, Goal> getAllGoals() {
		return goals;
	}
}
