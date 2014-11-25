package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;

public interface DatabaseValidator {
	public boolean isValid(Database database);
	
	public ArrayList<String> getMessages();
}
