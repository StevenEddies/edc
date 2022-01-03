package uk.me.eddies.apps.edc.backend.api.model;

public class StreakDTO {

	private int currentStreak;
	private int longestStreak;
	private boolean maintained;
	
	public StreakDTO() {
	}

	public StreakDTO(int currentStreak, int longestStreak, boolean maintained) {
		this.currentStreak = currentStreak;
		this.longestStreak = longestStreak;
		this.maintained = maintained;
	}

	public int getCurrentStreak() {
		return currentStreak;
	}
	
	public void setCurrentStreak(int currentStreak) {
		this.currentStreak = currentStreak;
	}
	
	public int getLongestStreak() {
		return longestStreak;
	}
	
	public void setLongestStreak(int longestStreak) {
		this.longestStreak = longestStreak;
	}

	public boolean isMaintained() {
		return maintained;
	}

	public void setMaintained(boolean maintained) {
		this.maintained = maintained;
	}
	
}
