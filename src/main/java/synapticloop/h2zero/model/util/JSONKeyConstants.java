package synapticloop.h2zero.model.util;

/*
 * Copyright (c) 2012-2017 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

public class JSONKeyConstants {
	private JSONKeyConstants() {}

	public static final String NAME = "name";

	public static final String UNIQUE = "unique";

	public static final String FIELDS = "fields";

	public static final String FINDERS = "finders";
	public static final String FIELD_FINDERS = "fieldFinders";

	public static final String QUESTIONS = "questions";
	public static final String COUNTERS = "counters";
	public static final String DELETERS = "deleters";
	public static final String INSERTERS = "inserters";

	public static final String UPDATERS = "updaters";
	public static final String FIELD_UPDATERS = "fieldUpdaters";

	public static final String CONSTANTS = "constants";

	public static final String WHERE_FIELDS = "whereFields";
	public static final String SELECT_FIELDS = "selectFields";
	public static final String VALUE_FIELDS = "valueFields";
	public static final String SET_FIELDS = "setFields";

	public static final String WHERE_CLAUSE = "whereClause";
	public static final String SELECT_CLAUSE = "selectClause";
	public static final String INSERT_CLAUSE = "insertClause";
	public static final String VALUES_CLAUSE = "valuesClause";
	public static final String SET_CLAUSE = "setClause";

	public static final String ORDER_BY = "orderBy";

	public static final String INDEX = "index";

	public static final String FOREIGN_KEY = "foreignKey";

	public static final String TYPE = "type";

	public static final String ALIAS = "alias";

	public static final String MULTIPLE = "multiple";

	public static final String VALUES = "values";

	public static final String DATABASE = "database";

	public static final String SCHEMA = "schema";

	public static final String PACKAGE = "package";

	public static final String TABLES = "tables";

	public static final String VIEWS = "views";

	public static final String FORMS = "forms";

	public static final String AS_CLAUSE = "asClause";

	public static final String FOREIGN = "foreign";

	public static final String ON_UPDATE = "onUpdate";
	public static final String ON_DELETE = "onDelete";

	public static final String COMMENTS = "comments";
	public static final String CONFIRM = "requiresConfirm";

	public static final String COMMENT = "comment";

	public static final String ENGINE = "engine";

	public static final String CHARSET = "charset";

	public static final String SINGLE = "single";

	// cache sizes for various things
	public static final String FINDER_STATEMENT_CACHE_SIZE = "statementCacheSize";
	public static final String FINDALL_STATEMENT_CACHE_SIZE = "findAllStatementCacheSize";
	public static final String DEFAULT_STATEMENT_CACHE_SIZE = "defaultStatementCacheSize";

}
