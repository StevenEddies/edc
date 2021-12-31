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
		warmUp();
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
			for (int i = BCrypt.MIN_COST; i <= BCrypt.MAX_COST; i++) {
				if (hash(i, password).compareTo(Duration.ofSeconds(2)) > 0) {
					LOG.debug("Took the maximum time, skipping the higher cost factors.");
					break;
				}
			}
		}
	}
	
	private Duration hash(int cost, String password) {
		Instant preHash = Instant.now();
		String hash = BCrypt.withDefaults().hashToString(cost, password.toCharArray());
		Duration hashTime = Duration.between(preHash, Instant.now());
		LOG.debug("Hash {} produced in {} with cost {}.", hash, DP_1.format(hashTime.toNanos() / 1000000D) + "ms", cost);
		return hashTime;
	}
	
	private boolean another() {
		System.out.println("Another?");
		return in.nextLine().toLowerCase().trim().startsWith("y");
	}
	
	private void warmUp() {
		LOG.debug("Warming up...");
		for (int i = 1; i <= 2000; i++) {
			BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, "password".toCharArray());
			if (i % 500 == 0) {
				LOG.debug("Warm up {}% complete.", (i / 20));
			}
		}
	}
}
