package synapticloop.h2zero.model;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.DatabaseFieldTypeConfirm;
import synapticloop.h2zero.model.util.FieldLookupHelper;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.KeyHelper;
import synapticloop.h2zero.util.NamingHelper;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The table encapsulates everything that is required for a single database table
 */
public class Table extends BaseSchemaObject {

  private static final List<String> ignoredKeys = new ArrayList<>();

  static {
    ignoredKeys.add(JSONKeyConstants.COMMENT);
    ignoredKeys.add(JSONKeyConstants.CACHEABLE);
    ignoredKeys.add(JSONKeyConstants.CACHE_FIND_ALL);
  }

  public static Set<String> ALLOWABLE_KEYS = new HashSet<>();

  static {
    ALLOWABLE_KEYS.add(JSONKeyConstants.NAME);

    ALLOWABLE_KEYS.add(JSONKeyConstants.COMMENTS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.FIELDS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.CONSTANTS);
    ALLOWABLE_KEYS.add(JSONKeyConstants.CONSTANTS_CACHE);

    ALLOWABLE_KEYS.add(JSONKeyConstants.FINDERS);
    ALLOWABLE_KEYS.add(JSONKeyConstants.FIELD_FINDERS);
    ALLOWABLE_KEYS.add(JSONKeyConstants.FIELD_NULL_FINDERS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.QUESTIONS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.UPDATERS);
    ALLOWABLE_KEYS.add(JSONKeyConstants.FIELD_UPDATERS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.UPSERTERS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.COUNTERS);
    ALLOWABLE_KEYS.add(JSONKeyConstants.FIELD_COUNTERS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.DELETERS);
    ALLOWABLE_KEYS.add(JSONKeyConstants.FIELD_DELETERS);

    ALLOWABLE_KEYS.add(JSONKeyConstants.INSERTERS);
  }

  private static final Map<String, String> replacementKeys = new HashMap<>();

  static {
    replacementKeys.put("comment", "comments");
  }

  private final List<String> foundIgnoredKeys = new ArrayList<>();

  private String engine = "innodb";
  private String charset = "UTF8";
  private final List<String> comments = new ArrayList<>();

  private boolean hasLargeObject = false;
  private boolean hasForeignKey = false;
  private boolean hasNullableFields = false;

  // all fields that are not marked as secure
  private final List<BaseField> nonSecureFields = new ArrayList<>();
  // all fields that are marked as secure
  private final List<BaseField> secureFields = new ArrayList<>();

  private final List<BaseField> foreignKeys = new ArrayList<>();

  private final Map<String, BaseField> setFieldLookup = new HashMap<>();
  private final Map<String, BaseField> whereFieldLookup = new HashMap<>();

  private final List<BaseField> nonNullFields = new ArrayList<>();
  private final List<BaseField> nonPrimaryFields = new ArrayList<>();
  private final List<BaseField> nonPrimaryNullFields = new ArrayList<>();

  private final List<Updater> updaters = new ArrayList<>(); // a list of all of the updaters
  private final List<Inserter> inserters = new ArrayList<>(); // a list of all of the inserters
  private final List<Upserter> upserters = new ArrayList<>(); // a list of all of the upserters
  private final List<Deleter> deleters = new ArrayList<>(); // a list of all of the deleters
  private final List<Constant> constants = new ArrayList<>(); // a list of all of the constants
  private final List<ConstantCache> constantCaches = new ArrayList<>(); // a list of all of the constant caches

  protected List<String> autoGeneratedDeleters = new ArrayList<>(); // a list of all of the automatically generated deleters

  private final Options options;

  private final Map<String, Object> additionalKeys = new HashMap<>();

  /**
   * Create a new Table object from the passed in jsonObject.
   *
   * @param options                   The options for the h2zero generation
   * @param jsonObject                the json object to create the table from.
   * @param defaultStatementCacheSize the default statement cache size
   * @throws H2ZeroParseException if there was an error parsing the jsonObject
   */
  public Table(Options options, JSONObject jsonObject, int defaultStatementCacheSize) throws H2ZeroParseException {
    super(jsonObject, defaultStatementCacheSize);

    this.options = options;

    this.name = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.NAME, null);
    jsonObject.remove(JSONKeyConstants.NAME);

    this.engine = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.ENGINE, engine);
    jsonObject.remove(JSONKeyConstants.ENGINE);

    this.charset = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.CHARSET, charset);
    jsonObject.remove(JSONKeyConstants.CHARSET);

    // maybe it is a comment array
    JSONArray optJSONArray = jsonObject.optJSONArray(JSONKeyConstants.COMMENTS);
    if (null != optJSONArray) {
      for (int i = 0; i < optJSONArray.length(); i++) {
        String comment = optJSONArray.optString(i, null);
        if (null != comment) {
          comments.add(comment);
        }
      }
    }

    jsonObject.remove(JSONKeyConstants.COMMENTS);

    if (null == name) {
      throw new H2ZeroParseException(String.format("The table '%s' attribute cannot be null.", JSONKeyConstants.NAME));
    }

    for (String key : ignoredKeys) {
      if (jsonObject.opt(key) != null) {
        foundIgnoredKeys.add(key);
      }
    }

    // now we are going to go through and determine all of the missing keys
    KeyHelper.findMissingKeys(this, jsonObject, ALLOWABLE_KEYS);
    // now for the fields
    populateFields(jsonObject);
  }

  /**
   * Populate all of the actions that can be performed on this table, including
   * <ul>
   *   <li>field finders</li>
   *   <li>field null finders</li>
   *   <li>finders</li>
   *   <li>updaters</li>
   *   <li>deleters</li>
   *   <li>inserters</li>
   *   <li>constants</li>
   *   <li>counters</li>
   *   <li>questsions</li>
   * </ul>
   *
   * @throws H2ZeroParseException if there was an error parsing the jsonObject
   */
  public void populateActions() throws H2ZeroParseException {
    populateFieldFinders(jsonObject);
    populateFieldNullFinders(jsonObject);
    populateFinders(jsonObject);

    populateFieldUpdaters(jsonObject);
    populateUpdaters(jsonObject);

    populateFieldDeleters(jsonObject);
    populateDeleters(jsonObject);

    populateInserters(jsonObject);

    populateUpserters(jsonObject);

    populateConstants(jsonObject);
    populateConstantsCache(jsonObject);

    populateFieldCounters(jsonObject);
    populateCounters(jsonObject);

    populateQuestions(jsonObject);

    populateReferencedFieldTypes();

    Iterator<String> keys = jsonObject.keys();
    while (keys.hasNext()) {
      String key = keys.next();

      Object keyObject = jsonObject.get(key);
      additionalKeys.put(key, keyObject);
      SimpleLogger.logInfo(LoggerType.PARSE_ADDITIONAL, this.getClass(), String.format("Found an additional JSONObject keyed on '%s', with value: %s", key, keyObject.toString()));
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void populateFields(JSONObject jsonObject) throws H2ZeroParseException {
    JSONArray fieldJson = new JSONArray();
    try {
      fieldJson = jsonObject.getJSONArray(JSONKeyConstants.FIELDS);
    } catch (JSONException ex) {
      throw new H2ZeroParseException(String.format("Cannot create a table without '%s'.", JSONKeyConstants.FIELDS), ex);
    }

    for (int i = 0; i < fieldJson.length(); i++) {
      String type = null;
      String fieldName = null;

      JSONObject fieldObject = null;
      try {
        fieldObject = fieldJson.getJSONObject(i);
        fieldName = fieldObject.getString(JSONKeyConstants.NAME);
        if (null == fieldName) {
          throw new H2ZeroParseException("Cannot have a field with a null name.");
        }

        type = fieldObject.optString("type", null);
        if (null == type || type.trim().length() == 0) {
          throw new H2ZeroParseException(String.format("No 'type' value found for field '%s'.", fieldName));
        }

        // now check to ensure that you can use this field type for the database
        String database = options.getDatabase();

        if (!DatabaseFieldTypeConfirm.getIsValidFieldTypeForDatabase(database, type)) {
          String separator = "";
          StringBuilder stringBuilder = new StringBuilder();
          for (String allowable : DatabaseFieldTypeConfirm.FIELD_VALIDATION_LOOKUP.get(database)) {
            stringBuilder.append(separator);
            stringBuilder.append(allowable);
            separator = ", ";
          }

          throw new H2ZeroParseException(String.format("Type '%s' value found for field '%s' is not valid for database '%s', valid values are: %s", type, fieldName, database, stringBuilder.toString()));
        }

      } catch (JSONException ex) {
        throw new H2ZeroParseException(String.format("Could not parse the '%s' array.", JSONKeyConstants.FIELDS), ex);
      }

      String firstUpper = NamingHelper.getFirstUpper(type);
      try {
        Class forName = Class.forName("synapticloop.h2zero.model.field." + firstUpper + "Field");
        Constructor constructor = forName.getConstructor(JSONObject.class, boolean.class);

        BaseField inBaseField = (BaseField) constructor.newInstance(fieldObject, true);

        constructor = forName.getConstructor(JSONObject.class);
        BaseField baseField = (BaseField) constructor.newInstance(fieldObject);
        baseField.setFieldIndex(i);

        if (!baseField.getNullable()) {
          nonNullFields.add(baseField);
        }

        // test to see whether any of the fields can be nullable (for the inserters)
        if (baseField.getNullable()) {
          hasNullableFields = true;
        }

        if(!baseField.getPrimary() && !baseField.getNullable()) {
          nonPrimaryNullFields.add(baseField);
        }

        if (!baseField.getPrimary()) {
          nonPrimaryFields.add(baseField);
        } else {
          this.primaryKeyField = baseField;
        }

        if (baseField.getIsLargeObject()) {
          hasLargeObject = true;
        }

        fields.add(baseField);
        if (baseField.getIsSecure()) {
          secureFields.add(baseField);
        } else {
          nonSecureFields.add(baseField);
        }

        // this is whether the fields are populated by default
        if (baseField.getPopulate()) {
          populateFields.add(baseField);
        } else {
          nonPopulateFields.add(baseField);
        }

        fieldLookup.put(fieldName, baseField);
        inFieldLookup.put(fieldName, inBaseField);
        if(baseField.getUnique()) {
          uniqueFields.add(baseField);
        }

        BaseField setBaseField = (BaseField) constructor.newInstance(fieldObject);
        setBaseField.suffixJavaName("Set");
        BaseField whereBaseField = (BaseField) constructor.newInstance(fieldObject);
        whereBaseField.suffixJavaName("Where");

        setFieldLookup.put(fieldName, setBaseField);
        whereFieldLookup.put(fieldName, whereBaseField);

        // add it to the cache - for later lookups
        FieldLookupHelper.addToTableFieldCache(this.name, fieldName);

      } catch (ClassNotFoundException |
               InstantiationException |
               IllegalAccessException |
               IllegalArgumentException |
               InvocationTargetException |
               NoSuchMethodException | SecurityException ex) {
        logFatalFieldParse(ex, ex.getMessage(), firstUpper);
      }

      jsonObject.remove(JSONKeyConstants.FIELDS);
    }

    // now figure out if there is a foreign key relationship
    for (BaseField baseField : fields) {
      String foreignKeyTable = baseField.getForeignKeyTable();
      String foreignKeyField = baseField.getForeignKeyField();
      if (null != foreignKeyField && null != foreignKeyTable) {
        hasForeignKey = true;
        // at this point - see whether the foreign key table and field actually exists
        if (!FieldLookupHelper.hasTableField(foreignKeyTable, foreignKeyField)) {
          throw new H2ZeroParseException(String.format("Field '%s' on table '%s' is trying to reference a foreign key of '%s.%s' which has not been defined yet.", baseField.getName(), name, foreignKeyTable, foreignKeyField));
        }

        foreignKeys.add(baseField);
      }
    }

    jsonObject.remove(JSONKeyConstants.FIELDS);
  }

  private void populateUpdaters(JSONObject jsonObject) throws H2ZeroParseException {
    // now go through and addUpdaters  - there are no multiple updaters - as this is not a finder
    for (String updater : autoGeneratedUpdaters) {
      generateAutomaticUpdater(updater);
    }

    JSONArray updaterJson = jsonObject.optJSONArray(JSONKeyConstants.UPDATERS);
    if (null == updaterJson) {
      updaterJson = new JSONArray();
    }

    for (int i = 0; i < updaterJson.length(); i++) {
      try {
        JSONObject updaterObject = updaterJson.getJSONObject(i);
        updaters.add(new Updater(this, updaterObject));
      } catch (JSONException ex) {
        throw new H2ZeroParseException("Could not parse updaters.", ex);
      }
    }

    jsonObject.remove(JSONKeyConstants.UPDATERS);
  }

  /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
   *
   * The following methods are on the Table class, rather than the view class,
   * as you cannot update/delete/insert into a view - but you can on a table
   *
   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

  /**
   * Automatic updaters will update __ALL__ rows in the database and there is no where clause
   *
   * @param updaterFieldName The fieldName
   * @throws H2ZeroParseException if there was an error parsing it
   */
  private void generateAutomaticUpdater(String updaterFieldName) throws H2ZeroParseException {
    JSONObject autoUpdater = new JSONObject();
    JSONArray setFieldsArray = new JSONArray();

    String[] fields = updaterFieldName.split(",");

    try {
      StringBuilder updaterNameBuilder = new StringBuilder();
      updaterNameBuilder.append("update");

      StringBuilder setClauseBuilder = new StringBuilder();
      setClauseBuilder.append("set ");

      for (int i = 0; i < fields.length; i++) {
        String field = fields[i].trim();
        updaterNameBuilder.append(NamingHelper.getFirstUpper(field));

        setClauseBuilder
            .append(updaterFieldName)
            .append(" = ? ");

        if (i != 0) {
          setClauseBuilder.append(", ");
        }

        setFieldsArray.put(field);
      }

      autoUpdater.put(JSONKeyConstants.NAME, updaterNameBuilder.toString());

      autoUpdater.put(JSONKeyConstants.SET_CLAUSE, setClauseBuilder.toString());
      autoUpdater.put(JSONKeyConstants.SET_FIELDS, setFieldsArray);

      Updater updater = new Updater(this, autoUpdater);

      updater.setIsAutoUpdater(true);
      updaters.add(updater);
    } catch (JSONException ex) {
      throw new H2ZeroParseException("Could not generate the field updater for '" + updaterFieldName + "'.", ex);
    }
  }

  private void populateFieldUpdaters(JSONObject jsonObject) throws H2ZeroParseException {
    JSONArray updaterJson = new JSONArray();
    try {
      updaterJson = jsonObject.getJSONArray(JSONKeyConstants.FIELD_UPDATERS);
    } catch (JSONException ex) {
      // do nothing - no field updaters are ok
    }

    for (int i = 0; i < updaterJson.length(); i++) {
      // these should all be strings - not objects as with other field(updaters/deleters/counters) not arrays as are
      // the finders
      String fieldUpdaterName = null;
      try {
        fieldUpdaterName = updaterJson.getString(i);
        autoGeneratedUpdaters.add(fieldUpdaterName);
      } catch (JSONException ex) {
        throw new H2ZeroParseException("Found a '" + JSONKeyConstants.FIELD_UPDATERS + "' element on the '" + this.name + "' table, however the value at index '" + i + "' is not a String.");
      }
    }

    jsonObject.remove(JSONKeyConstants.FIELD_UPDATERS);
  }

  private void populateFieldDeleters(JSONObject jsonObject) throws H2ZeroParseException {
    JSONArray fieldDeleterJson = new JSONArray();
    try {
      fieldDeleterJson = jsonObject.getJSONArray(JSONKeyConstants.FIELD_DELETERS);
    } catch (JSONException ex) {
      // do nothing - no field deleters is ok
    }

    for (int i = 0; i < fieldDeleterJson.length(); i++) {
      try {
        // this should be a list of fields that can be used to delete by for a where clause, not an object
        String deleterFieldName = fieldDeleterJson.getString(i);
        autoGeneratedDeleters.add(deleterFieldName);
      } catch (JSONException ex) {
        throw new H2ZeroParseException("Could not parse the field deleters, expecting an array of strings, message was: " + ex.getMessage(), ex);
      }
    }

    jsonObject.remove(JSONKeyConstants.FIELD_DELETERS);
  }

  private void populateDeleters(JSONObject jsonObject) throws H2ZeroParseException {

    JSONArray deleterJson = new JSONArray();
    try {
      deleterJson = jsonObject.getJSONArray(JSONKeyConstants.DELETERS);
    } catch (JSONException ex) {
      // do nothing - no deleters is ok
    }

    // now go through and add Deleters based on field deleters
    for (String deleter : autoGeneratedDeleters) {
      generateAutomaticDeleter(deleter);
    }

    for (int i = 0; i < deleterJson.length(); i++) {
      try {
        JSONObject deleterObject = deleterJson.getJSONObject(i);
        deleters.add(new Deleter(this, deleterObject));
      } catch (JSONException ex) {
        throw new H2ZeroParseException("Could not parse deleters.", ex);
      }
    }

    jsonObject.remove(JSONKeyConstants.DELETERS);
  }

  private void generateAutomaticDeleter(String deleterString) throws H2ZeroParseException {
    JSONObject autoDeleter = new JSONObject();
    String[] fields = deleterString.split(",");
    JSONArray whereFieldsArray = new JSONArray();

    try {
      StringBuilder fieldNameBuilder = new StringBuilder();
      fieldNameBuilder.append("deleteBy");

      StringBuilder whereClauseBuilder = new StringBuilder();
      whereClauseBuilder.append("where ");

      for (int i = 0; i < fields.length; i++) {
        String field = fields[i].trim();
        fieldNameBuilder.append(NamingHelper.getFirstUpper(field));

        if (i != 0) {
          whereClauseBuilder.append(" and ");
        }

        whereClauseBuilder.append(field);
        whereClauseBuilder.append(" = ?");

        whereFieldsArray.put(field);
      }

      autoDeleter.put(JSONKeyConstants.NAME, fieldNameBuilder.toString());

      autoDeleter.put(JSONKeyConstants.WHERE_CLAUSE, whereClauseBuilder.toString());
      autoDeleter.put(JSONKeyConstants.WHERE_FIELDS, whereFieldsArray);

      Deleter deleter = new Deleter(this, autoDeleter);
      deleter.setIsAutoDeleter(true);
      deleters.add(deleter);
    } catch (JSONException ex) {
      throw new H2ZeroParseException("Could not generate the field deleter for '" + deleterString + "'.", ex);
    }
  }

  private void populateInserters(JSONObject jsonObject) throws H2ZeroParseException {
    JSONArray inserterJson = new JSONArray();
    try {
      inserterJson = jsonObject.getJSONArray(JSONKeyConstants.INSERTERS);
    } catch (JSONException ex) {
      // do nothing - no finders is ok
    }

    for (int i = 0; i < inserterJson.length(); i++) {
      try {
        JSONObject inserterObject = inserterJson.getJSONObject(i);
        inserters.add(new Inserter(this, inserterObject));
      } catch (JSONException ex) {
        throw new H2ZeroParseException("Could not parse inserters.", ex);
      }
    }

    jsonObject.remove(JSONKeyConstants.INSERTERS);
  }

  private void populateUpserters(JSONObject jsonObject) throws H2ZeroParseException {
    JSONArray upserterJson = new JSONArray();
    try {
      upserterJson = jsonObject.getJSONArray(JSONKeyConstants.UPSERTERS);
    } catch (JSONException ex) {
      // do nothing - no finders is ok
    }

    for (int i = 0; i < upserterJson.length(); i++) {
      try {
        JSONObject upserterObject = upserterJson.getJSONObject(i);
        upserters.add(new Upserter(this, upserterObject));
      } catch (JSONException jsonex) {
        throw new H2ZeroParseException("Could not parse upserters.", jsonex);
      }
    }

    jsonObject.remove(JSONKeyConstants.UPSERTERS);
  }

  private void populateConstants(JSONObject jsonObject) throws H2ZeroParseException {
    JSONArray constantJson = new JSONArray();
    try {
      constantJson = jsonObject.getJSONArray(JSONKeyConstants.CONSTANTS);
    } catch (JSONException ex) {
      // do nothing - no constants is ok
    }

    for (int i = 0; i < constantJson.length(); i++) {
      try {
        JSONObject constantsObject = constantJson.getJSONObject(i);
        constants.add(new Constant(constantsObject, this));
      } catch (JSONException jsonex) {
        throw new H2ZeroParseException("Could not parse constants.", jsonex);
      }
    }

    jsonObject.remove(JSONKeyConstants.CONSTANTS);
  }

  private void populateConstantsCache(JSONObject jsonObject) throws H2ZeroParseException {
    JSONArray constantJson = new JSONArray();
    try {
      constantJson = jsonObject.getJSONArray(JSONKeyConstants.CONSTANTS_CACHE);
    } catch (JSONException ex) {
      // do nothing - no constants cache is OK.
    }

    for (int i = 0; i < constantJson.length(); i++) {
      try {
        constantCaches.add(new ConstantCache(constantJson.getString(i), this));
      } catch (JSONException jsonex) {
        throw new H2ZeroParseException("Could not parse constants cache.", jsonex);
      }
    }

    jsonObject.remove(JSONKeyConstants.CONSTANTS_CACHE);
  }


  // boring old getters and setters
  public String getEngine() {
    return (this.engine);
  }

  public String getCharset() {
    return (this.charset);
  }

  public List<String> getComments() {
    return comments;
  }

  public List<BaseField> getFields() {
    return (fields);
  }

  public List<Updater> getUpdaters() {
    return (updaters);
  }

  public List<Inserter> getInserters() {
    return (inserters);
  }

  public List<Upserter> getUpserters() {
    return (upserters);
  }

  public List<Deleter> getDeleters() {
    return (deleters);
  }

  public List<Constant> getConstants() {
    return (constants);
  }

  public List<BaseField> getForeignKeys() {
    return (foreignKeys);
  }

  public List<BaseField> getNonNullFields() {
    return (nonNullFields);
  }

  public List<BaseField> getNonPrimaryFields() {
    return (nonPrimaryFields);
  }

  public List<BaseField> getNonPrimaryNullFields() {
    return (nonPrimaryNullFields);
  }

  public BaseField getSetField(String name) {
    return (setFieldLookup.get(name));
  }

  public BaseField getWhereField(String name) {
    return (whereFieldLookup.get(name));
  }

  public BaseField getPrimaryKey() {
    return (primaryKeyField);
  }

  public String getJavaClassName() {
    return (NamingHelper.getFirstUpper(name));
  }

  public String getJavaFieldName() {
    return (NamingHelper.getSecondUpper(name));
  }

  public boolean getHasNonNullConstructor() {
    return (nonNullFields.size() != fields.size());
  }

  public boolean getHasLargeObject() {
    return hasLargeObject;
  }

  public boolean getHasNullableFields() {
    return (hasNullableFields);
  }

  public boolean getIsConstant() {
    return (!constants.isEmpty());
  }

  public boolean getIsTable() {
    return (true);
  }

  public boolean getIsView() {
    return (false);
  }

  public List<String> getFoundIgnoredKeys() {
    return foundIgnoredKeys;
  }

  public String getReplacementForKey(String key) {
    return (replacementKeys.get(key));
  }

  public boolean getHasForeignKey() {
    return this.hasForeignKey;
  }

  public void setHasForeignKey(boolean hasForeignKey) {
    this.hasForeignKey = hasForeignKey;
  }

  public List<ConstantCache> getConstantCaches() { return (constantCaches); }
}
