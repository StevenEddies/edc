package uk.me.eddies.apps.edc.backend.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import uk.me.eddies.apps.edc.backend.api.resource.AchievementResource;
import uk.me.eddies.apps.edc.backend.api.resource.UsersResource;
import uk.me.eddies.apps.edc.backend.model.EdcModel;
import uk.me.eddies.apps.edc.backend.model.ModelFactory;

public class EdcBackendApplication extends Application<EdcBackendConfiguration> {
	
	private EdcModel model;
	
	@Override
	public void run(EdcBackendConfiguration configuration, Environment environment) {
		
		model = ModelFactory.build(configuration.getUsers(), configuration.getGoals());
		
		environment.jersey().register(new UsersResource(model));
		environment.jersey().register(new AchievementResource(model));
		
		environment.jersey().register(new CORSFilter());
	}

	public static void main(String[] args) throws Exception {
		new EdcBackendApplication().run(args);
	}
}
