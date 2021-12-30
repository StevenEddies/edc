package uk.me.eddies.apps.edc.backend.api.resource;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uk.me.eddies.apps.edc.backend.api.model.DayDTO;
import uk.me.eddies.apps.edc.backend.model.EdcModel;

@Path("/users/{user}/achievement/")
public class AchievementResource {

	private final EdcModel model;

	public AchievementResource(EdcModel model) {
		this.model = model;
	}
	
	@GET
	@Path("/{year}/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DayDTO> getForYear(@PathParam("user") String user, @PathParam("year") int year, @QueryParam("as-of") String asOf) {
		Year date = Year.of(year);
		return model.lookupForUser(user)
				.lookupRange(date.atMonth(1).atDay(1), date.atMonth(12).atEndOfMonth()).stream()
				.map(eachDay -> eachDay.toDTO(parseAsOf(asOf)))
				.toList();
	}
	
	@GET
	@Path("/{year}/{month}/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DayDTO> getForMonth(@PathParam("user") String user, @PathParam("year") int year, @PathParam("month") int month, @QueryParam("as-of") String asOf) {
		YearMonth date = YearMonth.of(year, month);
		return model.lookupForUser(user)
				.lookupRange(date.atDay(1), date.atEndOfMonth()).stream()
				.map(eachDay -> eachDay.toDTO(parseAsOf(asOf)))
				.toList();
	}
	
	@GET
	@Path("/{year}/{month}/{day}/")
	@Produces(MediaType.APPLICATION_JSON)
	public DayDTO getForDay(@PathParam("user") String user, @PathParam("year") int year, @PathParam("month") int month, @PathParam("day") int day, @QueryParam("as-of") String asOf) {
		return model.lookupForUser(user)
				.lookupDay(LocalDate.of(year, month, day))
				.toDTO(parseAsOf(asOf));
	}
	
	@PUT
	@Path("/{year}/{month}/{day}/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putForDay(@PathParam("user") String user, @PathParam("year") int year, @PathParam("month") int month, @PathParam("day") int day, DayDTO data) {
		model.lookupForUser(user)
				.lookupDay(LocalDate.of(year, month, day))
				.update(data);
		return Response.ok().build();
	}
	
	private LocalDate parseAsOf(String input) {
		if (input == null) {
			return LocalDate.now(ZoneOffset.UTC);
		} else {
			return LocalDate.parse(input);
		}
	}
}
