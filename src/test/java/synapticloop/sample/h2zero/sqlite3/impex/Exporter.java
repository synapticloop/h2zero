package synapticloop.sample.h2zero.sqlite3.impex;


// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                 (/impex/exporter.templar)

import synapticloop.h2zero.exception.H2ZeroParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Exporter {
	/**
	 * Export a data dump to a file
	 *
	 * @param file The file to export the data from
	 * @throws H2ZeroParseException If there was an error parsing the data import
	 */
	public static void exportToFile(File file) throws H2ZeroParseException {
		try (FileWriter fileWriter = new FileWriter(file);
		     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

			// we now need to dump all of the tables
			bufferedWriter.write("#\ttype\tALL_TYPES\n");
			AllTypesExporter.exportToFile(bufferedWriter);
			bufferedWriter.write("#\ttype\tAUTHOR_STATUS\n");
			AuthorStatusExporter.exportToFile(bufferedWriter);
			bufferedWriter.write("#\ttype\tAUTHOR\n");
			AuthorExporter.exportToFile(bufferedWriter);
			bufferedWriter.write("#\ttype\tUSER_TYPE\n");
			UserTypeExporter.exportToFile(bufferedWriter);
			bufferedWriter.write("#\ttype\tUSER_TITLE\n");
			UserTitleExporter.exportToFile(bufferedWriter);
			bufferedWriter.write("#\ttype\tUSER\n");
			UserExporter.exportToFile(bufferedWriter);
			bufferedWriter.write("#\ttype\tPET\n");
			PetExporter.exportToFile(bufferedWriter);
			bufferedWriter.write("#\ttype\tUSER_PET\n");
			UserPetExporter.exportToFile(bufferedWriter);
		} catch(IOException ex) {
			throw new H2ZeroParseException(String.format("Cannot write to file '%s'.", file.getAbsolutePath()));
		}
	}

}