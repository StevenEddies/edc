package uk.me.eddies.apps.edc.backend.api.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;

public class DayDTO {

	private LocalDate day;
	private Collection<AchievementDTO> achievements;
	private DayStatus status;
	
	public DayDTO() {
	}
	
	public DayDTO(LocalDate day, Collection<AchievementDTO> achievements, DayStatus status) {
		this.day = day;
		this.achievements = new LinkedHashSet<>(achievements);
		this.status = status;
	}

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
