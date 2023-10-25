package synapticloop.sample.h2zero.sqlite3.model.util;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java-create-model-statistics.templar)

import synapticloop.sample.h2zero.sqlite3.model.AllTypes;
import synapticloop.sample.h2zero.sqlite3.model.Author;
import synapticloop.sample.h2zero.sqlite3.model.User;
import synapticloop.sample.h2zero.sqlite3.model.Pet;
import synapticloop.sample.h2zero.sqlite3.model.UserPet;

import java.lang.StringBuilder;

public class Statistics {
	public static final String getHitCountJson() {
		StringBuilder stringBuilder = new StringBuilder("[");
		stringBuilder.append(AllTypes.getHitCountJson());
		stringBuilder.append(", ");
		stringBuilder.append(Author.getHitCountJson());
		stringBuilder.append(", ");
		stringBuilder.append(User.getHitCountJson());
		stringBuilder.append(", ");
		stringBuilder.append(Pet.getHitCountJson());
		stringBuilder.append(", ");
		stringBuilder.append(UserPet.getHitCountJson());
		stringBuilder.append("]");
		return(stringBuilder.toString());
	}
}