package uk.me.eddies.apps.edc.backend.integration.framework;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

class BackendExtension implements BeforeEachCallback, AfterEachCallback {
	
	private static final ExtensionContext.Namespace STORE_NAMESPACE = Namespace.create(BackendExtension.class.getName());
	private static final String STORE_KEY_SERVER = "SERVER";

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		TestBackendInstance server = new TestBackendInstance(identifyConfigUrl(context));
		context.getStore(STORE_NAMESPACE).put(STORE_KEY_SERVER, server);
		
		server.start();
		populateInstanceOnTestClasses(context, server);
	}

	@Override
	public void afterEach(ExtensionContext context) {
		TestBackendInstance server = context.getStore(STORE_NAMESPACE).get(STORE_KEY_SERVER, TestBackendInstance.class);
		server.stop();
	}

	private URL identifyConfigUrl(ExtensionContext context) {
		return Lookups.iterateTestClasses(context)
				.map(this::identifyConfigUrl).flatMap(Optional::stream)
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Test using BackendExtension but test class is not annotated @TestBackend."));
	}
	
	private Optional<URL> identifyConfigUrl(Class<?> potentiallyAnnotatedClass) {
		return Lookups.findAnnotatedClasses(potentiallyAnnotatedClass, TestBackend.class, this::findConfigResource);
	}
	
	private URL findConfigResource(Class<?> annotatedClass, TestBackend annotation) {
		return Optional.ofNullable(annotatedClass.getResource(annotation.value()))
				.orElseThrow(() -> new IllegalArgumentException("Could not find resource " + annotation.value() + " relative to " + annotatedClass));
	}
	
	private void populateInstanceOnTestClasses(ExtensionContext context, TestBackendInstance server) {
		Lookups.forEachTestInstance(context, (instance, types) -> populateInstanceOnTestClass(instance, types, server));
	}
	
	private void populateInstanceOnTestClass(Object testInstance, Stream<Class<?>> testClasses, TestBackendInstance server) {
		testClasses
				.flatMap(type -> Arrays.stream(type.getDeclaredFields()))
				.filter(field -> field.getType().equals(TestBackendInstance.class))
				.peek(field -> field.setAccessible(true))
				.forEach(field -> {
					try {
						field.set(testInstance, server);
					} catch (IllegalAccessException e) {
						throw new RuntimeException("Failed to make " + field + " sufficiently accessible to set its value.", e);
					}
				});
	}
}
