package com.synapticloop.sample.h2zero.sqlite3.upserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-upserter.templar)


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

public class AuthorUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.AUTHOR_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorUpserter.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_author, 
				id_author_status, 
				txt_id_author, 
				nm_author, 
				nm_username, 
				txt_bio, 
				txt_url_cache_image, 
				num_following, 
				num_followers, 
				dtm_started_following, 
				fl_is_updating, 
				fl_author_is_following_user, 
				fl_author_is_followed_by_user
			from 
				author
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_author = ?";

	private static final String SQL_FIND_BY_ID_AUTHOR_STATUS = SQL_SELECT_START + 
		"""
			where id_author_status = ?
		""";
	private static final String SQL_FIND_BY_FL_IS_UPDATING = SQL_SELECT_START + 
		"""
			where fl_is_updating = ?
		""";
	private static final String SQL_FIND_BY_TXT_ID_AUTHOR_ID_AUTHOR_STATUS = SQL_SELECT_START + 
		"""
			where txt_id_author = ? and id_author_status = ?
		""";
	private static final String SQL_FIND_BY_TXT_ID_AUTHOR = SQL_SELECT_START + 
		"""
			where txt_id_author = ?
		""";
	private static final String SQL_FIND_IN_STATUS = SQL_SELECT_START + 
		"""
			where id_author_status in (...)
		""";
	private static final String SQL_FIND_ALL_TO_BE_EVALUATED = SQL_SELECT_START + 
		"""
			where id_author_status = (select id_author_status from author_status where txt_author_status = 'TO_BE_EVALUATED') and dtm_started_following <= ? 
		""";
	private static final String SQL_FIND_FIRST_TO_BE_EVALUATED = SQL_SELECT_START + 
		"""
			where id_author_status =
			(select id_author_status from author_status where txt_author_status = 'TO_BE_EVALUATED') and
			dtm_started_following < ? order by dtm_started_following asc
		""";
	private static final String SQL_FIND_LIMITED_TO_BE_EVALUATED = SQL_SELECT_START + 
		"""
			where id_author_status = (select id_author_status from author_status where txt_author_status = 'TO_BE_EVALUATED') and dtm_started_following < ? order by dtm_started_following
		""";
	private static final String SQL_FIND_IN_NUMBER = SQL_SELECT_START + 
		"""
			where fl_is_updating = ? and
			fl_is_updating in (...) and
			dtm_started_following in (...) and
			fl_is_updating = ? and
			fl_is_updating = ?
		""";

	private AuthorUpserter() {}

}