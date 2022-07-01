/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.application;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.servlets.tasks.Task;

public class ShutdownTask extends Task {
	
	private static final Logger LOG = LoggerFactory.getLogger(ShutdownTask.class);

	public ShutdownTask() {
		super("shutdown");
	}

	@Override
	public void execute(Map<String, List<String>> parameters, PrintWriter output) {
		LOG.info("Shutdown requested.");
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Do nothing
				}
				LOG.info("Shutdown beginning.");
				System.exit(0);
			}
		}.start();
	}
}
