/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.integration.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BackendExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestBackend {

	String value();
}
