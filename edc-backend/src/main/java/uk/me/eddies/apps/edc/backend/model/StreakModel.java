package uk.me.eddies.apps.edc.backend.model;

import java.time.LocalDate;
import java.util.function.Supplier;

import uk.me.eddies.apps.edc.backend.api.model.StreakDTO;

public class StreakModel {

	private final SingleUserModel underlyingModel;
	private final Supplier<LocalDate> now;
	
	private LocalDate asOf;
	private int current;
	private int longest;
	private boolean maintained;
	
	public StreakModel(SingleUserModel underlyingModel, Supplier<LocalDate> now) {
		this.underlyingModel = underlyingModel;
		this.now = now;
		recalculate();
	}
	
	public synchronized void recalculate() {
		current = 0;
		longest = 0;
		maintained = false;
		LocalDate today = now.get();
		LocalDate first = underlyingModel.firstKnownDay().orElse(today);
		for (LocalDate eachDay = first; eachDay.isBefore(today); eachDay = eachDay.plusDays(1)) {
			if (underlyingModel.lookupDay(eachDay).isAchieved()) {
				current++;
			} else {
				current = 0;
			}
			longest = Math.max(longest, current);
		}
		if (underlyingModel.lookupDay(today).isAchieved()) {
			current++;
			longest = Math.max(longest, current);
			maintained = true;
		}
		asOf = today;
	}
	
	public synchronized StreakDTO toDTO() {
		if (!asOf.equals(now.get())) {
			recalculate();
		}
		return new StreakDTO(current, longest, maintained);
	}
}
