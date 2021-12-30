package uk.me.eddies.apps.edc.backend.api.model;

public class AchievementDTO {

	private String goal;
	private boolean achieved;
	
	public AchievementDTO() {
	}
	
	public AchievementDTO(String goal, boolean achieved) {
		this.goal = goal;
		this.achieved = achieved;
	}

	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public boolean isAchieved() {
		return achieved;
	}
	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
}
