package com.synapticloop.sample.h2zero.cockroach.model.util;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//        (/java/constants/java-create-constants.templar)

/**
 * <p>The Constants class serves a dual purpose to show up compilation errors,
 * and as a resource for code to reference particular table and field values.</p>
 */
public class Constants {
	// The binder variables below are used to show up compilation errors where
	// it has been removed from the generation.

	// Model Binder for the user_type table
	public static final String USER_TYPE_BINDER = "USER_TYPE";
	// Model Binder for the user_title table
	public static final String USER_TITLE_BINDER = "USER_TITLE";
	// Model Binder for the user_user table
	public static final String USER_USER_BINDER = "USER_USER";
	// Model Binder for the pet table
	public static final String PET_BINDER = "PET";
	// Model Binder for the user_user_pet table
	public static final String USER_USER_PET_BINDER = "USER_USER_PET";
	// Model Binder for the all_types table
	public static final String ALL_TYPES_BINDER = "ALL_TYPES";

	// Field constants for the user_type table
	public static final String USER_TYPE_ID_USER_TYPE = "idUserType";
	public static final String USER_TYPE_NM_USER_TYPE = "nmUserType";


	// Field constants for the user_title table
	public static final String USER_TITLE_ID_USER_TITLE = "idUserTitle";
	public static final String USER_TITLE_NM_USER_TITLE = "nmUserTitle";
	public static final String USER_TITLE_NUM_ORDER_BY = "numOrderBy";


	// Finder binder constants for the findIdUserTitleNmUserTitleOrdered finder
	public static final String USER_TITLE_findIdUserTitleNmUserTitleOrdered_BINDER = "USER_TITLE_findIdUserTitleNmUserTitleOrdered";
	// Field constants for the user_user table
	public static final String USER_USER_ID_USER_USER = "idUserUser";
	public static final String USER_USER_ID_USER_TYPE = "idUserType";
	public static final String USER_USER_FL_IS_ALIVE = "flIsAlive";
	public static final String USER_USER_NUM_AGE = "numAge";
	public static final String USER_USER_NM_USERNAME = "nmUsername";
	public static final String USER_USER_TXT_ADDRESS_EMAIL = "txtAddressEmail";
	public static final String USER_USER_TXT_PASSWORD = "txtPassword";
	public static final String USER_USER_TS_SIGNUP = "tsSignup";


	// Finder binder constants for the findNmUserDtmSignup finder
	public static final String USER_USER_findNmUserDtmSignup_BINDER = "USER_USER_findNmUserDtmSignup";
	// Finder binder constants for the findGroupNumAge finder
	public static final String USER_USER_findGroupNumAge_BINDER = "USER_USER_findGroupNumAge";
	// Field constants for the pet table
	public static final String PET_ID_PET = "idPet";
	public static final String PET_NM_PET = "nmPet";
	public static final String PET_NUM_AGE = "numAge";
	public static final String PET_FLT_WEIGHT = "fltWeight";
	public static final String PET_DT_BIRTHDAY = "dtBirthday";
	public static final String PET_IMG_PHOTO = "imgPhoto";


	// Field constants for the user_user_pet table
	public static final String USER_USER_PET_ID_USER_USER_PET = "idUserUserPet";
	public static final String USER_USER_PET_ID_USER_USER = "idUserUser";
	public static final String USER_USER_PET_ID_PET = "idPet";


	// Field constants for the all_types table
	public static final String ALL_TYPES_ID_ALL_TYPES = "idAllTypes";
	public static final String ALL_TYPES_NUM_SMALLINT = "numSmallint";
	public static final String ALL_TYPES_NUM_INTEGER = "numInteger";
	public static final String ALL_TYPES_NUM_BIGINT = "numBigint";
	public static final String ALL_TYPES_NUM_DECIMAL = "numDecimal";
	public static final String ALL_TYPES_NUM_NUMERIC = "numNumeric";
	public static final String ALL_TYPES_FLT_REAL = "fltReal";
	public static final String ALL_TYPES_DBL_REAL = "dblReal";
	public static final String ALL_TYPES_NUM_SERIAL = "numSerial";
	public static final String ALL_TYPES_NUM_SMALLSERIAL = "numSmallserial";
	public static final String ALL_TYPES_NUM_BIGSERIAL = "numBigserial";


	// Model Binder for the user_user_type view
	public static final String USER_USER_TYPE_BINDER = "USER_USER_TYPE";

	// Field constants for the user_user_type view
	public static final String USER_USER_TYPE_NM_USERNAME = "nmUsername";
	public static final String USER_USER_TYPE_NM_USER_TYPE = "nmUserType";


}