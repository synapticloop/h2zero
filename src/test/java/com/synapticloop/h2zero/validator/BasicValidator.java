package com.synapticloop.h2zero.validator;

import java.util.List;

import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.h2zero.generator.model.Table;
import com.synapticloop.h2zero.generator.model.View;
import com.synapticloop.h2zero.generator.validator.BaseValidator;

public class BasicValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		// messages
		addInfoMessage("This is an informational message.");
		addWarnMessage("This is a warning message.");
		addFatalMessage("This is a fatal message, which will end the h2zero generation.");

		// get and iterate over the tables
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			// do some validation
			table.getName();
		}

		// get and iterate over the views
		List<View> views = database.getViews();
		for (View view : views) {
			// do some validation
			view.getName();
		}
	}
}
