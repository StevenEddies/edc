/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.api.resource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import uk.me.eddies.apps.edc.backend.api.model.StreakDTO;
import uk.me.eddies.apps.edc.backend.model.EdcModel;

@Path("/users/{user}/streak/")
public class StreakResource {

	private final EdcModel model;

	public StreakResource(EdcModel model) {
		this.model = model;
	}
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public StreakDTO getStreakInformation(@PathParam("user") String user) {
		return model.lookupForUser(user)
				.streak()
				.toDTO();
	}
}
