/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.model.persistence;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.lifecycle.Managed;
import uk.me.eddies.apps.edc.backend.configuration.DatastoreConfiguration;
import uk.me.eddies.apps.edc.backend.model.EdcModel;

public class PersistenceHandler implements Managed {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersistenceHandler.class);
	
	private final AchievementDataFile dataFile;
	private final ScheduledExecutorService executor;

	private ScheduledFuture<?> periodicSaveTask;
	
	public PersistenceHandler(EdcModel model, DatastoreConfiguration config, ScheduledExecutorService executor) {
		if (config != null) {
			File diskFile = new File(new File(config.getFolder()).getAbsoluteFile(), "data.json");
			this.dataFile = new AchievementDataFile(config.isBackup(), model, diskFile);
		} else {
			this.dataFile = null;
		}
		this.executor = executor;
	}

	public void modelCreated() {
		if (dataFile == null) {
			LOG.warn("No datastore is configured. Data will not persist after the JVM terminates.");
			return;
		}
		dataFile.load();
	}
	
	@Override
	public void start() {
		if (dataFile == null) {
			return;
		}
		periodicSaveTask = executor.scheduleWithFixedDelay(dataFile::save, 1, 1, TimeUnit.MINUTES);
	}

	@Override
	public void stop() {
		if (dataFile == null) {
			return;
		}
		periodicSaveTask.cancel(false);
		dataFile.save();
	}
}
