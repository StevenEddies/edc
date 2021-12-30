package uk.me.eddies.apps.edc.backend.integration.framework;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.UrlConfigurationSourceProvider;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.DropwizardTestSupport;
import uk.me.eddies.apps.edc.backend.application.EdcBackendApplication;
import uk.me.eddies.apps.edc.backend.application.EdcBackendConfiguration;

public class TestBackendInstance {

	private final DropwizardTestSupport<EdcBackendConfiguration> support;
	private Client client;
	
	TestBackendInstance(URL config) {
		support = new DropwizardTestSupport<>(
				EdcBackendApplication.class,
				config.toString(),
				new UrlConfigurationSourceProvider(),
				ConfigOverride.config("server.applicationConnectors[0].port", "0"),
				ConfigOverride.config("server.adminConnectors[0].port", "0"));
	}
	
	void start() throws Exception {
		support.before();
		client = new JerseyClientBuilder(support.getEnvironment())
				.build("Test client");
	}
	
	void stop() {
		client = null;
		support.after();
	}
	
	public WebTarget client() {
		return client.target(String.format("http://localhost:%d/", support.getLocalPort()));
	}
}
