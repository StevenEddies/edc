package uk.me.eddies.apps.edc.backend.model.persistence;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.lifecycle.Managed;
import uk.me.eddies.apps.edc.backend.model.EdcModel;

public class PersistenceHandler implements Managed {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersistenceHandler.class);
	
	private final EdcModel model;
	private final String datastore;
	private final ScheduledExecutorService executor;

	private ScheduledFuture<?> periodicSaveTask;
	
	public PersistenceHandler(EdcModel model, String datastore, ScheduledExecutorService executor) {
		this.model = model;
		this.datastore = datastore;
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
		LOG.info("Loading persisted data from: {}", datastore);
		// TODO
	}
	
	private void save() {
		LOG.info("Persisting data to: {}", datastore);
		// TODO
	}
}
