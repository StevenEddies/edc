/* Copyright 2022, Steven Eddies. See the LICENCE file in the repository root. */

package uk.me.eddies.apps.edc.configutil.password;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {

	private static final Logger LOG = LoggerFactory.getLogger(PasswordHasher.class);
	private static final NumberFormat DP_1 = new DecimalFormat("0.0");
	
	private Scanner in = new Scanner(System.in);
	
	public void run() {
		while (true) {
			LOG.debug("Beginning password hash iteration.");
			System.out.println("Enter your password (whitespace will be trimmed, max 72 bytes):");
			processPassword();
			LOG.debug("Completing password hash iteration.");
			if (!another()) {
				LOG.debug("User quit password hashing.");
				break;
			}
		}
	}

	private void processPassword() {
		String password = in.nextLine().trim();
		LOG.debug("Entered password was {} characters long.", password.length());
		if (password.isEmpty()) {
			System.out.println("Password was blank.");
			LOG.debug("User entered blank password, skipping hashing.");
		} else {
			hash(10, password);
		}
	}
	
	private Duration hash(int cost, String password) {
		Instant preHash = Instant.now();
		String hash = BCrypt.withDefaults().hashToString(cost, password.toCharArray());
		Duration hashTime = Duration.between(preHash, Instant.now());
		System.out.println("Hash: " + hash);
		LOG.debug("Hash {} produced in {} with cost {}.", hash, DP_1.format(hashTime.toNanos() / 1000000D) + "ms", cost);
		return hashTime;
	}
	
	private boolean another() {
		System.out.println("Another? (y/n)");
		return in.nextLine().toLowerCase().trim().startsWith("y");
	}
}
