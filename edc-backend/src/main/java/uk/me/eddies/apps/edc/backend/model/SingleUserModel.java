package uk.me.eddies.apps.edc.backend.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class SingleUserModel {

	private final GoalsModel goals;
	private final Map<LocalDate, DayModel> days = new LinkedHashMap<>();
	private final Supplier<LocalDate> now;
	
	public SingleUserModel(GoalsModel goals, Supplier<LocalDate> now) {
		this.goals = goals;
		this.now = now;
	}

	public synchronized DayModel lookupDay(LocalDate day) {
		return days.computeIfAbsent(day, unused -> new DayModel(goals, day, now));
	}
	
	public synchronized Collection<DayModel> lookupRange(LocalDate from, LocalDate to) {
		int days = (int) from.until(to, ChronoUnit.DAYS);
		return IntStream.rangeClosed(0, days)
				.mapToObj(i -> from.plusDays(i))
				.map(this::lookupDay)
				.toList();
	}
	
	public synchronized Collection<DayModel> getAllKnownDays() {
		return new LinkedHashSet<>(days.values());
	}
}
