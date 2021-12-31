package uk.me.eddies.apps.edc.backend.model.persistence;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.Files;

import io.dropwizard.lifecycle.Managed;
import uk.me.eddies.apps.edc.backend.api.model.DayDTO;
import uk.me.eddies.apps.edc.backend.model.EdcModel;
import uk.me.eddies.apps.edc.backend.model.SingleUserModel;
import uk.me.eddies.apps.edc.backend.model.User;

public class PersistenceHandler implements Managed {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersistenceHandler.class);
	
	private final EdcModel model;
	private final File datastore;
	private final File dataFile;
	private final ScheduledExecutorService executor;
	private final ObjectMapper json = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

	private ScheduledFuture<?> periodicSaveTask;
	
	public PersistenceHandler(EdcModel model, String datastore, ScheduledExecutorService executor) {
		this.model = model;
		this.datastore = new File(datastore).getAbsoluteFile();
		this.dataFile = new File(datastore, "data.json");
		this.executor = executor;
	}

	public void modelCreated() {
		if (datastore == null) {
			LOG.warn("No datastore is configured. Data will not persist after the JVM terminates.");
			return;
		}
		load();
	}
	
	@Override
	public void start() {
		if (datastore == null) {
			return;
		}
		periodicSaveTask = executor.scheduleWithFixedDelay(this::save, 1, 1, TimeUnit.MINUTES);
	}

	@Override
	public void stop() {
		if (datastore == null) {
			return;
		}
		periodicSaveTask.cancel(false);
		save();
	}
	
	private void load() {
		if (!dataFile.isFile()) {
			LOG.warn("Not loading data this time, as data file does not exist in: {}", datastore);
			return;
		}
		LOG.info("Loading persisted data from: {}", datastore);
		try {
			File backupFile = new File(dataFile.getPath() + "-" + Instant.now().toEpochMilli() + ".bak");
			Files.copy(dataFile, backupFile);
			LOG.info("Backed up persisted data to: {}", backupFile);
			AllUsersDTO data = json.readValue(dataFile, AllUsersDTO.class);
			for (FullUserDTO eachUser : data.getUsers()) {
				if (model.lookupUser(eachUser.getUserInfo().getName()).isEmpty()) {
					LOG.error("Persisted data contains unknown user {}, whose data couldn't be loaded.", eachUser.getUserInfo().getName());
					continue;
				}
				SingleUserModel userModel = model.lookupForUser(eachUser.getUserInfo().getName());
				for (DayDTO eachDay : eachUser.getDays()) {
					try {
						LocalDate date = LocalDate.parse(eachDay.getDay());
						userModel.lookupDay(date).update(eachDay);
					} catch (DateTimeParseException e) {
						LOG.error("Persisted data contains invalid date {}, whose data couldn't be loaded.", eachDay.getDay());
					} catch (IllegalArgumentException e) {
						LOG.error("Persisted data contains invalid data for {} on {}, which couldn't be loaded.", eachUser.getUserInfo().getName(), eachDay.getDay());
					}
				}
			}
			LOG.info("Loading complete.");
		} catch (IOException e) {
			LOG.error("Failed to load persisted data.", e);
		}
	}
	
	private void save() {
		LOG.info("Persisting data to: {}", datastore);
		LocalDate now = LocalDate.now();
		List<FullUserDTO> outputUsers = new ArrayList<>();
		for (User eachUser : model.getAllUsers()) {
			SingleUserModel userModel = model.lookupForUser(eachUser);
			List<DayDTO> outputDays = userModel.getAllKnownDays().stream()
					.map(eachDayModel -> eachDayModel.toDTO(now))
					.toList();
			outputUsers.add(new FullUserDTO(eachUser.toDTO(), outputDays));
		}
		try {
			datastore.mkdirs();
			json.writeValue(dataFile, new AllUsersDTO(outputUsers));
			LOG.info("Persisting complete.");
		} catch (IOException e) {
			LOG.error("Failed persist data.", e);
		}
	}
}
