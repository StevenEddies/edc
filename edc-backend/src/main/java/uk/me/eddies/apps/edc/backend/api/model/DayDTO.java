/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.api.model;

import java.util.Collection;
import java.util.LinkedHashSet;

public class DayDTO {

	private String day;
	private Collection<AchievementDTO> achievements;
	private DayStatus status;
	
	public DayDTO() {
	}
	
	public DayDTO(String day, Collection<AchievementDTO> achievements, DayStatus status) {
		this.day = day;
		this.achievements = new LinkedHashSet<>(achievements);
		this.status = status;
	}

	public String getDay() {
		return day;
	}
	public void setDay(String day) {
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
