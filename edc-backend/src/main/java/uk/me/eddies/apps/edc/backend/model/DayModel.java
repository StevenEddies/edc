package uk.me.eddies.apps.edc.backend.model;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import uk.me.eddies.apps.edc.backend.api.model.AchievementDTO;
import uk.me.eddies.apps.edc.backend.api.model.DayDTO;
import uk.me.eddies.apps.edc.backend.api.model.DayStatus;

public class DayModel {
	
	private final GoalsModel goals;
	
	private final LocalDate day;
	private final Map<Goal, Boolean> achievements;
	private final Supplier<LocalDate> now;

	public DayModel(GoalsModel goals, LocalDate day, Supplier<LocalDate> now) {
		this.goals = goals;
		this.day = day;
		this.achievements = new LinkedHashMap<>();
		this.now = now;

		goals.getAllGoals().values()
				.forEach(eachGoal -> achievements.put(eachGoal, false));
	}
	
	public synchronized void update(DayDTO incomingDTO) {
		if (incomingDTO.getDay() != null && !day.isEqual(LocalDate.parse(incomingDTO.getDay()))) {
			throw new IllegalArgumentException("Attempted to update " + day + " but specified day was " + incomingDTO.getDay());
		}
		for (AchievementDTO eachIncomingAchievement : incomingDTO.getAchievements()) {
			Goal goal = goals.lookupGoal(eachIncomingAchievement.getGoal())
					.orElseThrow(() -> new IllegalArgumentException("Unrecognised goal: " + eachIncomingAchievement.getGoal()));
			achievements.put(goal, eachIncomingAchievement.isAchieved());
		}
	}

	public synchronized DayDTO toDTO() {
		Collection<AchievementDTO> achievementDTOs = achievements.entrySet().stream()
				.map(eachAchievement -> new AchievementDTO(eachAchievement.getKey().getName(), eachAchievement.getValue()))
				.collect(toList());
		return new DayDTO(day.format(DateTimeFormatter.ISO_LOCAL_DATE), achievementDTOs, computeStatus());
	}

	private DayStatus computeStatus() {
		if (now.get().isBefore(day)) {
			return DayStatus.NOT_DUE;
		} else if (achievements.values().stream().allMatch(Boolean.TRUE::equals)) {
			return DayStatus.COMPLETE;
		} else if (achievements.values().stream().anyMatch(Boolean.TRUE::equals)) {
			return DayStatus.PARTIALLY_COMPLETE;
		} else {
			return DayStatus.INCOMPLETE;
		}
	}
}
