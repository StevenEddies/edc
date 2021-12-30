package uk.me.eddies.apps.edc.backend.api.model;

import java.time.LocalDate;
import java.util.Collection;

public class DayDTO {

	private LocalDate day;
	private Collection<AchievementDTO> achievements;
	private DayStatus status;
	
	public LocalDate getDay() {
		return day;
	}
	public void setDay(LocalDate day) {
		this.day = day;
	}
	public Collection<AchievementDTO> getAchievements() {
		return achievements;
	}
	public void setAchievements(Collection<AchievementDTO> achievements) {
		this.achievements = achievements;
	}
	public DayStatus getStatus() {
		return status;
	}
	public void setStatus(DayStatus status) {
		this.status = status;
	}
}
