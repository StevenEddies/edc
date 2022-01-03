package uk.me.eddies.apps.edc.backend.api.resource;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.Auth;
import uk.me.eddies.apps.edc.backend.api.model.DayDTO;
import uk.me.eddies.apps.edc.backend.model.EdcModel;
import uk.me.eddies.apps.edc.backend.model.User;

@Path("/users/{user}/achievement/")
public class AchievementResource {

	private final EdcModel model;

	public AchievementResource(EdcModel model) {
		this.model = model;
	}
	
	@GET
	@Path("/{year}/")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<DayDTO> getForYear(@PathParam("user") String user, @PathParam("year") int year) {
		Year date = Year.of(year);
		return model.lookupForUser(user)
				.lookupRange(date.atMonth(1).atDay(1), date.atMonth(12).atEndOfMonth()).stream()
				.map(eachDay -> eachDay.toDTO())
				.toList();
	}
	
	@GET
	@Path("/{year}/{month}/")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<DayDTO> getForMonth(@PathParam("user") String user, @PathParam("year") int year, @PathParam("month") int month) {
		YearMonth date = YearMonth.of(year, month);
		return model.lookupForUser(user)
				.lookupRange(date.atDay(1), date.atEndOfMonth()).stream()
				.map(eachDay -> eachDay.toDTO())
				.toList();
	}
	
	@GET
	@Path("/{year}/{month}/{day}/")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public DayDTO getForDay(@PathParam("user") String user, @PathParam("year") int year, @PathParam("month") int month, @PathParam("day") int day) {
		return model.lookupForUser(user)
				.lookupDay(LocalDate.of(year, month, day))
				.toDTO();
	}
	
	@PUT
	@Path("/{year}/{month}/{day}/")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putForDay(@PathParam("user") String user, @PathParam("year") int year, @PathParam("month") int month, @PathParam("day") int day, DayDTO data, @Auth Optional<User> requestingUser) {
		if (!Objects.equals(model.lookupUser(user), requestingUser)) {
			throw new ForbiddenException("A user can only modify their own data.");
		}
		model.lookupForUser(user).update(LocalDate.of(year, month, day), data);
		return Response.noContent().build();
	}
}
