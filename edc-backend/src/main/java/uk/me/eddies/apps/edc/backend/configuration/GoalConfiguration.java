/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.configuration;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class GoalConfiguration {

	@NotBlank
	private String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GoalConfiguration other = (GoalConfiguration) obj;
		return Objects.equals(name, other.name);
	}
}
