/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.api.resource;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import uk.me.eddies.apps.edc.backend.api.model.UserDTO;
import uk.me.eddies.apps.edc.backend.model.EdcModel;
import uk.me.eddies.apps.edc.backend.model.User;

@Path("/users/")
public class UsersResource {

	private final EdcModel model;

	public UsersResource(EdcModel model) {
		this.model = model;
	}
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> getAllUsers() {
		return model.getAllUsers().stream()
				.map(User::toDTO)
				.toList();
	}
}
