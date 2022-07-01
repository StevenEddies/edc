/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.backend.configuration;

import javax.validation.constraints.NotBlank;

public class DatastoreConfiguration {
	
	@NotBlank
	private String folder;
	
	private boolean backup = true;

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public boolean isBackup() {
		return backup;
	}

	public void setBackup(boolean backup) {
		this.backup = backup;
	}
}
