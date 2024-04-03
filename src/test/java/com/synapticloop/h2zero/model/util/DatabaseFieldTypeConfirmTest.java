package com.synapticloop.h2zero.model.util;

import static org.junit.Assert.*;

import com.synapticloop.h2zero.model.util.DatabaseFieldTypeConfirm;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.h2zero.model.Options;

public class DatabaseFieldTypeConfirmTest {
	@Before
	public void setup() {
	}

	@Test
	public void testPostgresMoneyField() {
		assertFalse(DatabaseFieldTypeConfirm.getIsValidFieldTypeForDatabase(Options.DATABASE_COCKROACH, "money"));
		assertTrue(DatabaseFieldTypeConfirm.getIsValidFieldTypeForDatabase(Options.DATABASE_POSTGRESQL, "money"));
	}

}
