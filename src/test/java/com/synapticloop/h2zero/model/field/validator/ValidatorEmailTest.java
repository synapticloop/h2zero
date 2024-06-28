package com.synapticloop.h2zero.model.field.validator;

import static org.junit.Assert.*;

import com.synapticloop.h2zero.generator.model.field.validator.ValidatorEmail;
import org.junit.Before;
import org.junit.Test;

public class ValidatorEmailTest {
	private ValidatorEmail validatorEmailNoNullNoConfirm;
	private ValidatorEmail validatorEmailAllowNullNoConfirm;
	private ValidatorEmail validatorEmailAllowNullAllowConfirm;
	private ValidatorEmail validatorEmailNoNullAllowConfirm;

	@Before
	public void setup() {
		validatorEmailNoNullNoConfirm = new ValidatorEmail(10, 30, false, false);
		validatorEmailAllowNullNoConfirm = new ValidatorEmail(10, 30, true, false);
		validatorEmailAllowNullAllowConfirm = new ValidatorEmail(10, 30, true, true);
		validatorEmailNoNullAllowConfirm = new ValidatorEmail(10, 30, true, true);
	}

	@Test
	public void testValidEmails() {
		assertTrue(validatorEmailNoNullNoConfirm.validate("a@example.com", null));
		assertTrue(validatorEmailNoNullNoConfirm.validate("a@example.com", ""));

		// these should all convert to null
		assertTrue(validatorEmailAllowNullNoConfirm.validate(null, null));
		assertTrue(validatorEmailAllowNullNoConfirm.validate("     ", null));
		assertTrue(validatorEmailAllowNullNoConfirm.validate("", "    "));
		assertTrue(validatorEmailAllowNullNoConfirm.validate("", ""));
		assertTrue(validatorEmailAllowNullNoConfirm.validate("\t\n\t\r", ""));

		assertTrue(validatorEmailAllowNullAllowConfirm.validate(null, null));
		assertTrue(validatorEmailAllowNullAllowConfirm.validate("a@example.com", "a@example.com"));

		assertTrue(validatorEmailNoNullAllowConfirm.validate("a@example.com", "a@example.com"));
	}

	public void testInvalidEmails() {
		// too long
		assertFalse(validatorEmailNoNullNoConfirm.validate("whilst-this-is-a-valid-email-this-is-too-long-for-our-field@example.com", null));

		// is null and shouldn't be
		assertFalse(validatorEmailNoNullNoConfirm.validate(null, null));

		// too short
		assertFalse(validatorEmailNoNullNoConfirm.validate("a@b.co", null));
		assertFalse(validatorEmailNoNullNoConfirm.validate("a@example.com", null));

		// mis-match
		assertFalse(validatorEmailNoNullAllowConfirm.validate("a@example.com", "something-else@something.com"));
	}

}
