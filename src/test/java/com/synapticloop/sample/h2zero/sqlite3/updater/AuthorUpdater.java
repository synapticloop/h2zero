package com.synapticloop.sample.h2zero.sqlite3.updater;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//              (java-create-updater.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Timestamp;

import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthorUpdater {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.AUTHOR_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorUpdater.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_UPDATE_START = "update author ";

	// static fields generated from the user input
	private static final String SQL_UPDATE_NUM_FOLLOWERS = SQL_UPDATE_START + " set num_followers = ? ";
	private static final String SQL_UPDATE_ID_AUTHOR = SQL_UPDATE_START + " set id_author = ? ";
	private static final String SQL_UPDATE_ID_AUTHOR_NUM_FOLLOWERS = SQL_UPDATE_START + " set id_author,num_followers = ? id_author,num_followers = ? , ";
	private static final String SQL_RESET_AUTHORS_TO_BE_FOLLOWED = SQL_UPDATE_START + "  set id_author_status = (select id_author_status from author_status where txt_author_status = 'FOLLOWED') " + "  where id_author_status = (select id_author_status from author_status where txt_author_status = 'TO_BE_EVALUATED') and dtm_started_following < ? order by dtm_started_following ";
	private static final String SQL_SET_FL_IS_UPDATING = SQL_UPDATE_START + "  set fl_is_updating = ?";
	private static final String SQL_SET_FL_IS_UPDATING_WHERE_FL_AUTHOR_IS_FOLLOWED_BY_USER = SQL_UPDATE_START + " set fl_is_updating = ?" + " where fl_author_is_followed_by_user = 1";
	private static final String SQL_SET_FL_AUTHOR_IS_FOLLOWED_BY_USER_WHERE_FL_IS_UPDATING = SQL_UPDATE_START + " set fl_author_is_followed_by_user = ?, fl_is_updating = 1" + "  where fl_is_updating = 1";
	private static final String SQL_SET_FL_IS_UPDATING_WHERE_FL_AUTHOR_IS_FOLLOWING_USER = SQL_UPDATE_START + " set fl_is_updating = ?" + " where fl_author_is_following_user = 1";
	private static final String SQL_SET_FL_AUTHOR_IS_FOLLOWING_USER_WHERE_FL_IS_UPDATING = SQL_UPDATE_START + " set fl_author_is_following_user = ?, fl_is_updating = 1" + "  where fl_is_updating = 1";
	private static final String SQL_UPDATE_FL_IS_FOLLOWING = SQL_UPDATE_START + " set fl_is = ?";
	private static final String SQL_UPDATE_ALL_TO_BE_EVALUATED_TO_FOLLOWED = SQL_UPDATE_START + " set id_status_author = (select id_author_status from author_status where txt_author_status = 'FOLLOWED')" + "  where id_author_status = (select id_author_status from author_status where txt_author_status = 'TO_BE_EVALUATED') and dtm_started_following < ? ";

	private AuthorUpdater() {}

	/**
	 * This is the updater for 'updateNumFollowers' and will throw a SQLException on error
	 * See the 'updateNumFollowersSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param numFollowersSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int updateNumFollowers(Connection connection, Long numFollowersSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_NUM_FOLLOWERS)) {
			ConnectionManager.setBigint(preparedStatement, 1, numFollowersSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'updateNumFollowers' and will throw a SQLException on error
	 * See the 'updateNumFollowersSilent' method for a non-throwing method.
	 * 
	 * @param numFollowersSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateNumFollowers(Long numFollowersSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateNumFollowers(connection, numFollowersSet));
		}
	}

	/**
	 * This is the updater for 'updateNumFollowers' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'updateNumFollowers' method for a throwing method.
	 * 
	 * @param numFollowersSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateNumFollowersSilent(Long numFollowersSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateNumFollowers(connection, numFollowersSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException updateNumFollowersSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'updateIdAuthor' and will throw a SQLException on error
	 * See the 'updateIdAuthorSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param idAuthorSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int updateIdAuthor(Connection connection, Long idAuthorSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ID_AUTHOR)) {
			ConnectionManager.setBigint(preparedStatement, 1, idAuthorSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'updateIdAuthor' and will throw a SQLException on error
	 * See the 'updateIdAuthorSilent' method for a non-throwing method.
	 * 
	 * @param idAuthorSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateIdAuthor(Long idAuthorSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateIdAuthor(connection, idAuthorSet));
		}
	}

	/**
	 * This is the updater for 'updateIdAuthor' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'updateIdAuthor' method for a throwing method.
	 * 
	 * @param idAuthorSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateIdAuthorSilent(Long idAuthorSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateIdAuthor(connection, idAuthorSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException updateIdAuthorSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'updateIdAuthorNumFollowers' and will throw a SQLException on error
	 * See the 'updateIdAuthorNumFollowersSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param idAuthorSet the field to set as a Long
	 * @param numFollowersSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int updateIdAuthorNumFollowers(Connection connection, Long idAuthorSet, Long numFollowersSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ID_AUTHOR_NUM_FOLLOWERS)) {
			ConnectionManager.setBigint(preparedStatement, 1, idAuthorSet);
			ConnectionManager.setBigint(preparedStatement, 2, numFollowersSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'updateIdAuthorNumFollowers' and will throw a SQLException on error
	 * See the 'updateIdAuthorNumFollowersSilent' method for a non-throwing method.
	 * 
	 * @param idAuthorSet the field to set as a Long
	 * @param numFollowersSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateIdAuthorNumFollowers(Long idAuthorSet, Long numFollowersSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateIdAuthorNumFollowers(connection, idAuthorSet, numFollowersSet));
		}
	}

	/**
	 * This is the updater for 'updateIdAuthorNumFollowers' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'updateIdAuthorNumFollowers' method for a throwing method.
	 * 
	 * @param idAuthorSet the field to set as a Long
	 * @param numFollowersSet the field to set as a Long
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateIdAuthorNumFollowersSilent(Long idAuthorSet, Long numFollowersSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateIdAuthorNumFollowers(connection, idAuthorSet, numFollowersSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException updateIdAuthorNumFollowersSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'resetAuthorsToBeFollowed' and will throw a SQLException on error
	 * See the 'resetAuthorsToBeFollowedSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param dtmStartedFollowing - The where clause to set as a Timestamp
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int resetAuthorsToBeFollowed(Connection connection, Timestamp dtmStartedFollowing) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_RESET_AUTHORS_TO_BE_FOLLOWED)) {
			ConnectionManager.setDatetime(preparedStatement, 1, dtmStartedFollowing);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'resetAuthorsToBeFollowed' and will throw a SQLException on error
	 * See the 'resetAuthorsToBeFollowedSilent' method for a non-throwing method.
	 * 
	 * @param dtmStartedFollowing - The where clause to set as a Timestamp
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int resetAuthorsToBeFollowed(Timestamp dtmStartedFollowing) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(resetAuthorsToBeFollowed(connection, dtmStartedFollowing));
		}
	}

	/**
	 * This is the updater for 'resetAuthorsToBeFollowed' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'resetAuthorsToBeFollowed' method for a throwing method.
	 * 
	 * @param dtmStartedFollowing - The where clause to set as a Timestamp
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int resetAuthorsToBeFollowedSilent(Timestamp dtmStartedFollowing) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(resetAuthorsToBeFollowed(connection, dtmStartedFollowing));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException resetAuthorsToBeFollowedSilent(" + dtmStartedFollowing + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdating' and will throw a SQLException on error
	 * See the 'setFlIsUpdatingSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int setFlIsUpdating(Connection connection, Boolean flIsUpdatingSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_FL_IS_UPDATING)) {
			ConnectionManager.setBoolean(preparedStatement, 1, flIsUpdatingSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdating' and will throw a SQLException on error
	 * See the 'setFlIsUpdatingSilent' method for a non-throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlIsUpdating(Boolean flIsUpdatingSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlIsUpdating(connection, flIsUpdatingSet));
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdating' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'setFlIsUpdating' method for a throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlIsUpdatingSilent(Boolean flIsUpdatingSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlIsUpdating(connection, flIsUpdatingSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException setFlIsUpdatingSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdatingWhereFlAuthorIsFollowedByUser' and will throw a SQLException on error
	 * See the 'setFlIsUpdatingWhereFlAuthorIsFollowedByUserSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int setFlIsUpdatingWhereFlAuthorIsFollowedByUser(Connection connection, Boolean flIsUpdatingSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_FL_IS_UPDATING_WHERE_FL_AUTHOR_IS_FOLLOWED_BY_USER)) {
			ConnectionManager.setBoolean(preparedStatement, 1, flIsUpdatingSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdatingWhereFlAuthorIsFollowedByUser' and will throw a SQLException on error
	 * See the 'setFlIsUpdatingWhereFlAuthorIsFollowedByUserSilent' method for a non-throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlIsUpdatingWhereFlAuthorIsFollowedByUser(Boolean flIsUpdatingSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlIsUpdatingWhereFlAuthorIsFollowedByUser(connection, flIsUpdatingSet));
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdatingWhereFlAuthorIsFollowedByUser' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'setFlIsUpdatingWhereFlAuthorIsFollowedByUser' method for a throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlIsUpdatingWhereFlAuthorIsFollowedByUserSilent(Boolean flIsUpdatingSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlIsUpdatingWhereFlAuthorIsFollowedByUser(connection, flIsUpdatingSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException setFlIsUpdatingWhereFlAuthorIsFollowedByUserSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'setFlAuthorIsFollowedByUserWhereFlIsUpdating' and will throw a SQLException on error
	 * See the 'setFlAuthorIsFollowedByUserWhereFlIsUpdatingSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param flAuthorIsFollowedByUserSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int setFlAuthorIsFollowedByUserWhereFlIsUpdating(Connection connection, Boolean flAuthorIsFollowedByUserSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_FL_AUTHOR_IS_FOLLOWED_BY_USER_WHERE_FL_IS_UPDATING)) {
			ConnectionManager.setBoolean(preparedStatement, 1, flAuthorIsFollowedByUserSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'setFlAuthorIsFollowedByUserWhereFlIsUpdating' and will throw a SQLException on error
	 * See the 'setFlAuthorIsFollowedByUserWhereFlIsUpdatingSilent' method for a non-throwing method.
	 * 
	 * @param flAuthorIsFollowedByUserSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlAuthorIsFollowedByUserWhereFlIsUpdating(Boolean flAuthorIsFollowedByUserSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlAuthorIsFollowedByUserWhereFlIsUpdating(connection, flAuthorIsFollowedByUserSet));
		}
	}

	/**
	 * This is the updater for 'setFlAuthorIsFollowedByUserWhereFlIsUpdating' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'setFlAuthorIsFollowedByUserWhereFlIsUpdating' method for a throwing method.
	 * 
	 * @param flAuthorIsFollowedByUserSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlAuthorIsFollowedByUserWhereFlIsUpdatingSilent(Boolean flAuthorIsFollowedByUserSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlAuthorIsFollowedByUserWhereFlIsUpdating(connection, flAuthorIsFollowedByUserSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException setFlAuthorIsFollowedByUserWhereFlIsUpdatingSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdatingWhereFlAuthorIsFollowingUser' and will throw a SQLException on error
	 * See the 'setFlIsUpdatingWhereFlAuthorIsFollowingUserSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int setFlIsUpdatingWhereFlAuthorIsFollowingUser(Connection connection, Boolean flIsUpdatingSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_FL_IS_UPDATING_WHERE_FL_AUTHOR_IS_FOLLOWING_USER)) {
			ConnectionManager.setBoolean(preparedStatement, 1, flIsUpdatingSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdatingWhereFlAuthorIsFollowingUser' and will throw a SQLException on error
	 * See the 'setFlIsUpdatingWhereFlAuthorIsFollowingUserSilent' method for a non-throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlIsUpdatingWhereFlAuthorIsFollowingUser(Boolean flIsUpdatingSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlIsUpdatingWhereFlAuthorIsFollowingUser(connection, flIsUpdatingSet));
		}
	}

	/**
	 * This is the updater for 'setFlIsUpdatingWhereFlAuthorIsFollowingUser' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'setFlIsUpdatingWhereFlAuthorIsFollowingUser' method for a throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlIsUpdatingWhereFlAuthorIsFollowingUserSilent(Boolean flIsUpdatingSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlIsUpdatingWhereFlAuthorIsFollowingUser(connection, flIsUpdatingSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException setFlIsUpdatingWhereFlAuthorIsFollowingUserSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'setFlAuthorIsFollowingUserWhereFlIsUpdating' and will throw a SQLException on error
	 * See the 'setFlAuthorIsFollowingUserWhereFlIsUpdatingSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param flAuthorIsFollowingUserSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int setFlAuthorIsFollowingUserWhereFlIsUpdating(Connection connection, Boolean flAuthorIsFollowingUserSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_FL_AUTHOR_IS_FOLLOWING_USER_WHERE_FL_IS_UPDATING)) {
			ConnectionManager.setBoolean(preparedStatement, 1, flAuthorIsFollowingUserSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'setFlAuthorIsFollowingUserWhereFlIsUpdating' and will throw a SQLException on error
	 * See the 'setFlAuthorIsFollowingUserWhereFlIsUpdatingSilent' method for a non-throwing method.
	 * 
	 * @param flAuthorIsFollowingUserSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlAuthorIsFollowingUserWhereFlIsUpdating(Boolean flAuthorIsFollowingUserSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlAuthorIsFollowingUserWhereFlIsUpdating(connection, flAuthorIsFollowingUserSet));
		}
	}

	/**
	 * This is the updater for 'setFlAuthorIsFollowingUserWhereFlIsUpdating' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'setFlAuthorIsFollowingUserWhereFlIsUpdating' method for a throwing method.
	 * 
	 * @param flAuthorIsFollowingUserSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int setFlAuthorIsFollowingUserWhereFlIsUpdatingSilent(Boolean flAuthorIsFollowingUserSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(setFlAuthorIsFollowingUserWhereFlIsUpdating(connection, flAuthorIsFollowingUserSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException setFlAuthorIsFollowingUserWhereFlIsUpdatingSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'updateFlIsFollowing' and will throw a SQLException on error
	 * See the 'updateFlIsFollowingSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int updateFlIsFollowing(Connection connection, Boolean flIsUpdatingSet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_FL_IS_FOLLOWING)) {
			ConnectionManager.setBoolean(preparedStatement, 1, flIsUpdatingSet);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'updateFlIsFollowing' and will throw a SQLException on error
	 * See the 'updateFlIsFollowingSilent' method for a non-throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateFlIsFollowing(Boolean flIsUpdatingSet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateFlIsFollowing(connection, flIsUpdatingSet));
		}
	}

	/**
	 * This is the updater for 'updateFlIsFollowing' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'updateFlIsFollowing' method for a throwing method.
	 * 
	 * @param flIsUpdatingSet the field to set as a Boolean
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateFlIsFollowingSilent(Boolean flIsUpdatingSet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateFlIsFollowing(connection, flIsUpdatingSet));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException updateFlIsFollowingSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * This is the updater for 'updateAllToBeEvaluatedToFollowed' and will throw a SQLException on error
	 * See the 'updateAllToBeEvaluatedToFollowedSilent' method for a non-throwing method.
	 * 
	 * @param connection the connection to the database
	 * @param dtmStartedFollowing - The where clause to set as a Timestamp
	 * 
	 * @return The number of rows that were updated
	 * @throws SQLException if there was an error in the statement or database connection
	 */ 
	public static int updateAllToBeEvaluatedToFollowed(Connection connection, Timestamp dtmStartedFollowing) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ALL_TO_BE_EVALUATED_TO_FOLLOWED)) {
			ConnectionManager.setDatetime(preparedStatement, 1, dtmStartedFollowing);

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * This is the updater for 'updateAllToBeEvaluatedToFollowed' and will throw a SQLException on error
	 * See the 'updateAllToBeEvaluatedToFollowedSilent' method for a non-throwing method.
	 * 
	 * @param dtmStartedFollowing - The where clause to set as a Timestamp
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateAllToBeEvaluatedToFollowed(Timestamp dtmStartedFollowing) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateAllToBeEvaluatedToFollowed(connection, dtmStartedFollowing));
		}
	}

	/**
	 * This is the updater for 'updateAllToBeEvaluatedToFollowed' and will silently swallow any 
	 * SQLException on error and return a -1 as the number of rows updated.
	 * See the 'updateAllToBeEvaluatedToFollowed' method for a throwing method.
	 * 
	 * @param dtmStartedFollowing - The where clause to set as a Timestamp
	 * 
	 * @return The number of rows that were updated or -1 on error
	 */ 
	public static int updateAllToBeEvaluatedToFollowedSilent(Timestamp dtmStartedFollowing) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(updateAllToBeEvaluatedToFollowed(connection, dtmStartedFollowing));
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException updateAllToBeEvaluatedToFollowedSilent(" + dtmStartedFollowing + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

}