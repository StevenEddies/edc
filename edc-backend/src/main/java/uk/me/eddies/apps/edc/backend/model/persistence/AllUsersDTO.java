/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.model.persistence;

import java.util.List;

public class AllUsersDTO {

	private List<FullUserDTO> users;
	
	public AllUsersDTO() {
	}
	
	public AllUsersDTO(List<FullUserDTO> users) {
		this.users = users;
	}
	
	public List<FullUserDTO> getUsers() {
		return users;
	}
	
	public void setUsers(List<FullUserDTO> users) {
		this.users = users;
	}
}
