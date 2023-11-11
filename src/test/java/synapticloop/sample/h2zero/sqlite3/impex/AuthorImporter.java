package synapticloop.sample.h2zero.sqlite3.impex;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                 (/impex/impex-importer.templar)

import synapticloop.h2zero.exception.H2ZeroParseException;
import java.math.*;
import java.sql.*;

public class AuthorImporter {
	public static void importLine(String line) throws H2ZeroParseException {
		String[] splits = line.split("\t");
		if(splits.length != 13) {
			// we don't have enough tokens to import - we always import every field
			throw new H2ZeroParseException(String.format("Insufficient fields, found '%d', required '%d'",13 , splits.length));
		}

		// we are good to go
		// for each field - we parse the value
		Long idAuthor = ImpexConverter.convertLong(splits[0], false);
		Long idAuthorStatus = ImpexConverter.convertLong(splits[1], true);
		String txtIdAuthor = ImpexConverter.convertString(splits[2], false);
		String nmAuthor = ImpexConverter.convertString(splits[3], false);
		String nmUsername = ImpexConverter.convertString(splits[4], false);
		String txtBio = ImpexConverter.convertString(splits[5], false);
		String txtUrlCacheImage = ImpexConverter.convertString(splits[6], false);
		Long numFollowing = ImpexConverter.convertLong(splits[7], true);
		Long numFollowers = ImpexConverter.convertLong(splits[8], true);
		Timestamp dtmStartedFollowing = ImpexConverter.convertTimestamp(splits[9], true);
		Boolean flIsUpdating = ImpexConverter.convertBoolean(splits[10], true);
		Boolean flAuthorIsFollowingUser = ImpexConverter.convertBoolean(splits[11], true);
		Boolean flAuthorIsFollowedByUser = ImpexConverter.convertBoolean(splits[12], true);
	}

}