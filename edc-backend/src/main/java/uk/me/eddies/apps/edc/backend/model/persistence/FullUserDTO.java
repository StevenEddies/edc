package uk.me.eddies.apps.edc.backend.model.persistence;

import java.util.List;

import uk.me.eddies.apps.edc.backend.api.model.DayDTO;
import uk.me.eddies.apps.edc.backend.api.model.UserDTO;

public class FullUserDTO {

	private UserDTO userInfo;
	private List<DayDTO> days;
	
	public FullUserDTO() {
	}
	
	public FullUserDTO(UserDTO userInfo, List<DayDTO> days) {
		this.userInfo = userInfo;
		this.days = days;
	}
	
	public UserDTO getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserDTO userInfo) {
		this.userInfo = userInfo;
	}
	
	public List<DayDTO> getDays() {
		return days;
	}
	
	public void setDays(List<DayDTO> days) {
		this.days = days;
	}
}
