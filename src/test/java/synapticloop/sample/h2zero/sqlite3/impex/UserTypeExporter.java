package synapticloop.sample.h2zero.sqlite3.impex;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                 (/impex/impex-exporter.templar)

import synapticloop.h2zero.exception.H2ZeroParseException;
import java.io.BufferedWriter;
import synapticloop.sample.h2zero.sqlite3.finder.UserTypeFinder;
import synapticloop.sample.h2zero.sqlite3.model.UserType;
import java.util.List;
import java.io.IOException;

public class UserTypeExporter {
	public static void exportToFile(BufferedWriter bufferedWriter) throws IOException {
		List<UserType> all = UserTypeFinder.findAllSilent();
		for (UserType userType: all) {
			bufferedWriter.write(ImpexConverter.convertToString(userType.getIdUserType()));
			bufferedWriter.write("\t");
			bufferedWriter.write(ImpexConverter.convertToString(userType.getNmUserType()));
			bufferedWriter.write("\n");
		}
	}

}