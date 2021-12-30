package uk.me.eddies.apps.edc.backend.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class SingleUserModel {

	private final GoalsModel goals;
	private final Map<LocalDate, DayModel> days = new LinkedHashMap<>();
	
	public SingleUserModel(GoalsModel goals) {
		this.goals = goals;
	}

	public synchronized DayModel lookupDay(LocalDate day) {
		return days.computeIfAbsent(day, unused -> new DayModel(goals, day));
	}
	
	public synchronized Collection<DayModel> lookupRange(LocalDate from, LocalDate to) {
		int days = (int) from.until(to, ChronoUnit.DAYS);
		return IntStream.rangeClosed(0, days)
				.mapToObj(i -> from.plusDays(i))
				.map(this::lookupDay)
				.toList();
	}
}
