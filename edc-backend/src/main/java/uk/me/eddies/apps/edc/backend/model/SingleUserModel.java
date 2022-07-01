/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import uk.me.eddies.apps.edc.backend.api.model.DayDTO;

public class SingleUserModel {

	private final GoalsModel goals;
	private final NavigableMap<LocalDate, DayModel> days = new TreeMap<>();
	private final Supplier<LocalDate> now;
	private final StreakModel streak;
	
	public SingleUserModel(GoalsModel goals, Supplier<LocalDate> now) {
		this.goals = goals;
		this.now = now;
		this.streak = new StreakModel(this, now);
	}

	public synchronized DayModel lookupDay(LocalDate day) {
		return days.computeIfAbsent(day, unused -> new DayModel(goals, day, now));
	}
	
	public synchronized void update(LocalDate day, DayDTO data) {
		lookupDay(day).update(data);
		streak.recalculate();
	}
	
	public synchronized Collection<DayModel> lookupRange(LocalDate from, LocalDate to) {
		int days = (int) from.until(to, ChronoUnit.DAYS);
		return IntStream.rangeClosed(0, days)
				.mapToObj(i -> from.plusDays(i))
				.map(this::lookupDay)
				.toList();
	}
	
	public synchronized Optional<LocalDate> firstKnownDay() {
		return days.isEmpty() ? Optional.empty() : Optional.of(days.firstKey());
	}
	
	public synchronized Collection<DayModel> getAllKnownDays() {
		return new LinkedHashSet<>(days.values());
	}
	
	public StreakModel streak() {
		return streak;
	}
}
