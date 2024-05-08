package com.synapticloop.sample.h2zero.mysql.model.util;

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
	// Model Binder for the user table
	public static final String USER_BINDER = "USER";
	// Model Binder for the pet_type table
	public static final String PET_TYPE_BINDER = "PET_TYPE";
	// Model Binder for the pet table
	public static final String PET_BINDER = "PET";
	// Model Binder for the user_pet table
	public static final String USER_PET_BINDER = "USER_PET";
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
	// Field constants for the user table
	public static final String USER_ID_USER = "idUser";
	public static final String USER_ID_USER_TYPE = "idUserType";
	public static final String USER_FL_IS_ALIVE = "flIsAlive";
	public static final String USER_NUM_AGE = "numAge";
	public static final String USER_NM_USERNAME = "nmUsername";
	public static final String USER_TXT_ADDRESS_EMAIL = "txtAddressEmail";
	public static final String USER_TXT_PASSWORD = "txtPassword";
	public static final String USER_DTM_SIGNUP = "dtmSignup";


	// Finder binder constants for the findNmUserDtmSignup finder
	public static final String USER_findNmUserDtmSignup_BINDER = "USER_findNmUserDtmSignup";
	// Finder binder constants for the findGroupNumAge finder
	public static final String USER_findGroupNumAge_BINDER = "USER_findGroupNumAge";
	// Field constants for the pet_type table
	public static final String PET_TYPE_ID_PET_TYPE = "idPetType";
	public static final String PET_TYPE_NM_PET_TYPE = "nmPetType";
	public static final String PET_TYPE_TXT_DESC_PET_TYPE = "txtDescPetType";


	// Field constants for the pet table
	public static final String PET_ID_PET = "idPet";
	public static final String PET_NM_PET = "nmPet";
	public static final String PET_NUM_AGE = "numAge";
	public static final String PET_FLT_WEIGHT = "fltWeight";
	public static final String PET_DT_BIRTHDAY = "dtBirthday";
	public static final String PET_IMG_PHOTO = "imgPhoto";


	// Field constants for the user_pet table
	public static final String USER_PET_ID_USER_PET = "idUserPet";
	public static final String USER_PET_ID_USER = "idUser";
	public static final String USER_PET_ID_PET = "idPet";


	// Field constants for the all_types table
	public static final String ALL_TYPES_ID_ALL_TYPES = "idAllTypes";
	public static final String ALL_TYPES_TEST_BIGINT = "testBigint";
	public static final String ALL_TYPES_TEST_BLOB = "testBlob";
	public static final String ALL_TYPES_TEST_BOOL = "testBool";
	public static final String ALL_TYPES_TEST_CHAR = "testChar";
	public static final String ALL_TYPES_TEST_BOOLEAN = "testBoolean";
	public static final String ALL_TYPES_TEST_BINARY = "testBinary";
	public static final String ALL_TYPES_TEST_VARBINARY = "testVarbinary";
	public static final String ALL_TYPES_TEST_DATE = "testDate";
	public static final String ALL_TYPES_TEST_DATETIME = "testDatetime";
	public static final String ALL_TYPES_TEST_DEC = "testDec";
	public static final String ALL_TYPES_TEST_DECIMAL = "testDecimal";
	public static final String ALL_TYPES_TEST_DOUBLE = "testDouble";
	public static final String ALL_TYPES_TEST_FLOAT = "testFloat";
	public static final String ALL_TYPES_TEST_INT = "testInt";
	public static final String ALL_TYPES_TEST_INTEGER = "testInteger";
	public static final String ALL_TYPES_TEST_LONGTEXT = "testLongtext";
	public static final String ALL_TYPES_TEST_MEDIUMBLOB = "testMediumblob";
	public static final String ALL_TYPES_TEST_MEDIUMINT = "testMediumint";
	public static final String ALL_TYPES_TEST_MEDIUMTEXT = "testMediumtext";
	public static final String ALL_TYPES_TEST_NUMERIC = "testNumeric";
	public static final String ALL_TYPES_TEST_SMALLINT = "testSmallint";
	public static final String ALL_TYPES_TEST_TIME = "testTime";
	public static final String ALL_TYPES_TEST_TEXT = "testText";
	public static final String ALL_TYPES_TEST_TIMESTAMP = "testTimestamp";
	public static final String ALL_TYPES_TEST_TINYINT = "testTinyint";
	public static final String ALL_TYPES_TEST_TINYTEXT = "testTinytext";
	public static final String ALL_TYPES_TEST_VARCHAR = "testVarchar";
	public static final String ALL_TYPES_TEST_YEAR = "testYear";


	// Model Binder for the user_user_type view
	public static final String USER_USER_TYPE_BINDER = "USER_USER_TYPE";

	// Field constants for the user_user_type view
	public static final String USER_USER_TYPE_ID_USER = "idUser";
	public static final String USER_USER_TYPE_NM_USER = "nmUser";
	public static final String USER_USER_TYPE_NM_USER_TYPE = "nmUserType";


}