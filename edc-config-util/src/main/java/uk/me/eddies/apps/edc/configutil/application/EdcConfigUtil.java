package uk.me.eddies.apps.edc.configutil.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.me.eddies.apps.edc.configutil.password.PasswordHasher;

public class EdcConfigUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(EdcConfigUtil.class);

	public static void main(String[] args) {
		LOG.debug("Beginning config utility.");
		new PasswordHasher().run();
		LOG.debug("Exiting config utility.");
	}
}
