package uk.me.eddies.apps.edc.backend.integration.framework;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;

public class Lookups {

	private Lookups() {
	}
	
	public static Stream<ExtensionContext> iterateParentContexts(ExtensionContext original) {
		return Stream.iterate(original, eachContext -> eachContext.getParent().isPresent(), eachContext -> eachContext.getParent().get());
	}
	
	public static Stream<Class<?>> iterateTestClasses(ExtensionContext original) {
		return iterateParentContexts(original)
				.map(ExtensionContext::getTestClass).flatMap(Optional::stream);
	}
	
	public static void forEachTestInstance(ExtensionContext original, BiConsumer<Object, Stream<Class<?>>> iteration) {
		iterateParentContexts(original)
				.map(ExtensionContext::getTestInstance).flatMap(Optional::stream)
				.distinct()
				.forEach(instance -> iteration.accept(instance, identifyInheritedClasses(instance.getClass())));
	}
	
	private static Stream<Class<?>> identifyInheritedClasses(Class<?> original) {

		Stream<Class<?>> indirectClasses = Stream.concat(
				Optional.ofNullable(original.getSuperclass()).stream(),
				Arrays.stream(original.getInterfaces()))
				
				.flatMap(eachClass -> identifyInheritedClasses(eachClass));
		
		return Stream.concat(Stream.of(original), indirectClasses).distinct();
	}
	
	public static <R, A extends Annotation> Optional<R> findAnnotatedClasses(Class<?> original, Class<A> annotation, BiFunction<Class<?>, A, R> extractor) {
		return identifyPotentiallyAnnotatedClasses(original)
				.map(eachClass -> findAndProcessAnnotation(eachClass, annotation, extractor))
				.flatMap(Optional::stream)
				.findFirst();
	}
	
	private static Stream<Class<?>> identifyPotentiallyAnnotatedClasses(Class<?> original) {
		return identifyPotentiallyAnnotatedClasses(original, new HashSet<>());
	}
	
	private static Stream<Class<?>> identifyPotentiallyAnnotatedClasses(Class<?> original, Set<Class<?>> visited) {

		// Would use AnnotationSupport except we need to know the class which is annotated
		Stream<Class<?>> indirectClasses = Stream.concat(Stream.concat(
				Arrays.stream(original.getDeclaredAnnotations()).map(Annotation::annotationType),
				Optional.ofNullable(original.getSuperclass()).stream()),
				Arrays.stream(original.getInterfaces()))
				
				.filter(visited::add)
				.flatMap(eachClass -> identifyPotentiallyAnnotatedClasses(eachClass, visited));
		
		return Stream.concat(Stream.of(original), indirectClasses).distinct();
	}
	
	private static <R, A extends Annotation> Optional<R> findAndProcessAnnotation(Class<?> potentiallyAnnotated, Class<A> annotationType, BiFunction<Class<?>, A, R> extractor) {
		return Optional.ofNullable(potentiallyAnnotated.getDeclaredAnnotation(annotationType))
				.map(annotation -> extractor.apply(potentiallyAnnotated, annotation));
	}
}
