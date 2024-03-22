package synapticloop.sample.h2zero.sqlite3.impex;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                 (/impex/impex-exporter.templar)

import synapticloop.h2zero.exception.H2ZeroParseException;
import java.io.BufferedWriter;
import synapticloop.sample.h2zero.sqlite3.finder.UserPetFinder;
import synapticloop.sample.h2zero.sqlite3.model.UserPet;
import java.util.List;
import java.io.IOException;

public class UserPetExporter {
	public static void exportToFile(BufferedWriter bufferedWriter) throws IOException {
		List<UserPet> all = UserPetFinder.findAllSilent();
		for (UserPet userPet: all) {
			bufferedWriter.write(ImpexConverter.convertToString(userPet.getIdUserPet()));
			bufferedWriter.write("\t");
			bufferedWriter.write(ImpexConverter.convertToString(userPet.getIdUser()));
			bufferedWriter.write("\t");
			bufferedWriter.write(ImpexConverter.convertToString(userPet.getIdPet()));
			bufferedWriter.write("\n");
		}
	}

}