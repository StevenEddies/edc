/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.model.persistence;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.Files;

import uk.me.eddies.apps.edc.backend.api.model.DayDTO;
import uk.me.eddies.apps.edc.backend.model.EdcModel;
import uk.me.eddies.apps.edc.backend.model.SingleUserModel;
import uk.me.eddies.apps.edc.backend.model.User;

public class AchievementDataFile {

	private static final Logger LOG = LoggerFactory.getLogger(AchievementDataFile.class);
	
	private final ObjectMapper json = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	private final boolean backup;
	private final EdcModel model;
	private final File dataFile;

	public AchievementDataFile(boolean backup, EdcModel model, File dataFile) {
		this.backup = backup;
		this.model = model;
		this.dataFile = dataFile;
	}

	public void load() {
		if (!dataFile.isFile()) {
			LOG.warn("Not loading data this time, as data file {} does not exist.", dataFile);
			return;
		}
		LOG.info("Loading persisted data from: {}", dataFile);
		backup();
		loadDataFromFile();
	}

	private void backup() {
		if (!backup) {
			LOG.warn("Not backing up, in accordance with configuration. Only do this in test environments.");
			return;
		}
		try {
			File backupFile = new File(dataFile.getPath() + "-" + Instant.now().toEpochMilli() + ".bak");
			Files.copy(dataFile, backupFile);
			LOG.info("Backed up persisted data to: {}", backupFile);
		} catch (IOException e) {
			LOG.error("Failed to back up persisted data.", e);
		}
	}
	
	private void loadDataFromFile() {
		try {
			AllUsersDTO data = json.readValue(dataFile, AllUsersDTO.class);
			data.getUsers().forEach(this::loadUserData);
			LOG.info("Loading complete.");
		} catch (IOException | RuntimeException e) {
			LOG.error("Failed to load persisted data.", e);
		}
	}

	private void loadUserData(FullUserDTO eachUser) {
		if (model.lookupUser(eachUser.getUserInfo().getName()).isEmpty()) {
			LOG.error("Persisted data contains unknown user {}, whose data couldn't be loaded.", eachUser.getUserInfo().getName());
			return;
		}
		SingleUserModel userModel = model.lookupForUser(eachUser.getUserInfo().getName());
		eachUser.getDays().forEach(eachDay -> loadUserDay(eachUser.getUserInfo().getName(), userModel, eachDay));
	}

	private void loadUserDay(String inputUsername, SingleUserModel userModel, DayDTO inputDay) {
		try {
			userModel.update(LocalDate.parse(inputDay.getDay()), inputDay);
		} catch (DateTimeParseException e) {
			LOG.error("Persisted data contains invalid date {}, whose data couldn't be loaded.", inputDay.getDay());
		} catch (RuntimeException e) {
			LOG.error("Persisted data contains invalid data for {} on {}, which couldn't be loaded.", inputUsername, inputDay.getDay());
		}
	}
	
	public void save() {
		LOG.info("Persisting data to: {}", dataFile);
		try {
			dataFile.getParentFile().mkdirs();
			json.writeValue(dataFile, determineDataToSave());
			LOG.info("Persisting complete.");
		} catch (IOException e) {
			LOG.error("Failed to persist data.", e);
		}
	}

	private AllUsersDTO determineDataToSave() {
		return new AllUsersDTO(
				model.getAllUsers().stream()
				.map(eachUser -> determineOutputUser(eachUser))
				.toList());
	}

	private FullUserDTO determineOutputUser(User eachUser) {
		return new FullUserDTO(eachUser.toDTO(), determineOutputDays(model.lookupForUser(eachUser)));
	}

	private List<DayDTO> determineOutputDays(SingleUserModel userModel) {
		return userModel.getAllKnownDays().stream()
				.map(eachDayModel -> eachDayModel.toDTO())
				.toList();
	}
}
