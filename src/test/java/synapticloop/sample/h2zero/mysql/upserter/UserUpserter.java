package synapticloop.sample.h2zero.mysql.upserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-upserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;
import synapticloop.sample.h2zero.mysql.bean.FindNmUserDtmSignupBean;
import synapticloop.sample.h2zero.mysql.bean.FindGroupNumAgeBean;

import synapticloop.sample.h2zero.mysql.model.User;

public class UserUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserUpserter.class);
	private static final String SQL_SELECT_START = "select id_user, id_user_type, fl_is_alive, nm_username, txt_address_email, txt_password from user";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user = ?";

	private static final String SQL_FIND_BY_NUM_AGE = SQL_SELECT_START + " where num_age = ?";
	private static final String SQL_FIND_BY_FL_IS_ALIVE_NUM_AGE = SQL_SELECT_START + " where fl_is_alive = ? and num_age = ?";
	private static final String SQL_FIND_BY_NM_USERNAME = SQL_SELECT_START + " where nm_username = ?";
	private static final String SQL_FIND_BY_TXT_ADDRESS_EMAIL = SQL_SELECT_START + " where txt_address_email = ?";
	private static final String SQL_FIND_BY_TXT_ADDRESS_EMAIL_TXT_PASSWORD = SQL_SELECT_START + " where txt_address_email = ? and txt_password = ?";
	private static final String SQL_FIND_NM_USER_DTM_SIGNUP = "select nm_user, dtm_signup from user";
	private static final String SQL_FIND_GROUP_NUM_AGE = "select count(*) as num_count, num_age from user group by num_count";
	private static final String SQL_FIND_BY_NUM_AGE_IN = SQL_SELECT_START + " where num_age in (...)";
	private static final String SQL_FIND_BY_NUM_AGE_BETWEEN = SQL_SELECT_START + " where num_age > ? and num_age < ?";


	private UserUpserter() {}

}