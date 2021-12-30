package uk.me.eddies.apps.edc.backend.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class EdcBackendApplication extends Application<EdcBackendConfiguration> {
	
	@Override
	public void run(EdcBackendConfiguration configuration, Environment environment) {
		
		environment.jersey().register(new CORSFilter());
	}

	public static void main(String[] args) throws Exception {
		new EdcBackendApplication().run(args);
	}
}
