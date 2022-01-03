package uk.me.eddies.apps.edc.backend.application;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Environment;
import uk.me.eddies.apps.edc.backend.api.resource.AchievementResource;
import uk.me.eddies.apps.edc.backend.api.resource.StreakResource;
import uk.me.eddies.apps.edc.backend.api.resource.UsersResource;
import uk.me.eddies.apps.edc.backend.api.utility.UserAuthenticator;
import uk.me.eddies.apps.edc.backend.model.EdcModel;
import uk.me.eddies.apps.edc.backend.model.ModelFactory;
import uk.me.eddies.apps.edc.backend.model.User;
import uk.me.eddies.apps.edc.backend.model.persistence.PersistenceHandler;

public class EdcBackendApplication extends Application<EdcBackendConfiguration> {
	
	private EdcModel model;
	private PersistenceHandler persistence;
	
	@Override
	public void run(EdcBackendConfiguration configuration, Environment environment) {
		
		model = ModelFactory.build(configuration.getUsers(), configuration.getGoals());
		
		persistence = new PersistenceHandler(model, configuration.getDatastore(), environment.lifecycle().scheduledExecutorService("persistence-%d").build());
		persistence.modelCreated();
		environment.lifecycle().manage(persistence);
		
		environment.jersey().register(new UsersResource(model));
		environment.jersey().register(new AchievementResource(model));
		environment.jersey().register(new StreakResource(model));
		
		environment.jersey().register(new AuthDynamicFeature(
				new BasicCredentialAuthFilter.Builder<User>()
					.setAuthenticator(new UserAuthenticator(model))
					.buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
		
		environment.admin().addTask(new ShutdownTask());
	}

	public static void main(String[] args) throws Exception {
		new EdcBackendApplication().run(args);
	}
}
