package synapticloop.h2zero.base.form.field;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import synapticloop.h2zero.util.JsonHelper;

public abstract class BaseFormField {
	private static final Logger LOGGER = Logger.getLogger(BaseFormField.class);

	// the name of the field to that is bound - i.e. <input type="text" name="firstname" />
	// the input attribute of 'name'
	protected String name;
	// the __actual__ value that is assigned to the field
	protected String value;
	// the __confirmed__ value that is assigned to the field
	protected String confirmValue;
	// whether to allow nulls including just whitespace
	protected boolean allowNull = false;
	// whether this field requires confirm
	protected boolean requiresConfirm = false;
	// the maximum length in characters
	protected int maxLength = 2048;
	// the minimum length
	protected int minLength = 0;
	// the default value
	private String defaultValue = null;

	// whether this is an or field
	private String orField = null;

	private String onlyIfFieldName = null;
	private String onlyIfFieldValue = null;

	private String errorMessage = null;

	private Set<String> allowableValues = new LinkedHashSet<String>();

	protected Object parsedValue;

	public BaseFormField(String name, boolean allowNull, boolean requiresConfirm, int minLength, int maxLength) {
		this.name = name;
		this.allowNull = allowNull;
		this.requiresConfirm = requiresConfirm;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public BaseFormField(JSONObject jsonObject) {
		if(null == jsonObject) {
			jsonObject = new JSONObject();
		}

		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.allowNull = JsonHelper.getBooleanValue(jsonObject, "allowNull", false);
		this.requiresConfirm = JsonHelper.getBooleanValue(jsonObject, "requiresConfirm", false);
		this.maxLength = JsonHelper.getIntValue(jsonObject, "maxLength", this.maxLength);
		this.minLength = JsonHelper.getIntValue(jsonObject, "minLength", 0);
		this.errorMessage = JsonHelper.getStringValue(jsonObject, "errorMessage", "");
		this.orField = JsonHelper.getStringValue(jsonObject, "or", null);

		// now for the allowable values
		String allowableValuesString = JsonHelper.getStringValue(jsonObject, "allowableValues", "");
		if(allowableValuesString.length() != 0) {
			String[] split = allowableValuesString.split(",");
			for (int i = 0; i < split.length; i++) {
				String allowableValue = split[i];
				getAllowableValues().add(allowableValue);
			}
		}

		this.defaultValue = JsonHelper.getStringValue(jsonObject, "default", null);
		// now we need to check that the default value falls within the allowable values
		if(null != defaultValue && 
				!allowableValues.contains(defaultValue)) {
			if(LOGGER.isEnabledFor(Level.WARN)) {
				LOGGER.warn("Default value of '" + defaultValue + "' is not within the allowable values list of '" + allowableValuesString + "', ignoring.");
			}
			this.defaultValue = null;
		}

		// now for the only if fields
		String onlyIf = JsonHelper.getStringValue(jsonObject, "onlyif", null);
		if(null != onlyIf && onlyIf.length() != 0 && onlyIf.contains("=")) {
			String[] splitter = onlyIf.split("=");
			if(splitter.length == 2) {
				this.onlyIfFieldName = splitter[0];
				this.onlyIfFieldValue = splitter[1];
			} else {
				if(LOGGER.isEnabledFor(Level.WARN)) {
					LOGGER.warn("The 'onlyif' value of '" + onlyIf + "' could not be parsed, ignoring.");
				}
			}
		}
	}

	public abstract boolean isValid();

	/**
	 * Populate this field from the servlet reuest
	 *
	 * @param httpServletRequest The incoming servlet request
	 */
	public void populate(HttpServletRequest httpServletRequest) {
		this.setValue(httpServletRequest.getParameter(this.name));
		if(requiresConfirm) {
			this.setConfirmValue(httpServletRequest.getParameter(this.name + ".confirm"));
		}
	}

	/**
	 * Whether this field passes whether it is null (including empty strings or
	 * whitespace only) and is allowed to be null.
	 */
	protected boolean passesDefaultChecks() {
		if(null == value || value.length() == 0) {
			// this field is null, there is no need to check whether it is a valid
			// length
			return(allowNull);
		} else {
			// the value is not null, we need to check lengths
			int length = 0;

			if(null != value) {
				length = value.length();
			}

			if(length < getMinLength() || length > getMaxLength()) {
				return(false);
			}

			// if it requires a confirm field, check to see whether the
			// <field_name>.confirm matches this field value
			if(getRequiresConfirm()) {
				if(null == value && confirmValue == null) {
					return(true);
				} else {
					return(value.equals(confirmValue));
				}
			}

			if(!getAllowableValues().isEmpty()) {
				return(getAllowableValues().contains(value));
			}
			return(true);
		}
	}

	/*
	 * Various Getters and setters - mainly boring stuff here
	 */

	public void setName(String name) { this.name = name; }
	public String getName() { return name; }

	public boolean getOnlyIf() {
		return(null != getOnlyIfFieldName() && null != getOnlyIfFieldValue());
	}

	public String getValue() { return value; }

	/**
	 * Set the value of the named field, and also set the trimmed value
	 *
	 * @param value The value to set
	 */
	public void setValue(String value) {
		if(null == value || value.trim().length() == 0) {
			this.value = null;
		} else {
			this.value = value.trim();
		}
	}


	public String getConfirmValue() { return confirmValue; }

	public void setConfirmValue(String confirmValue) {
		if(null == confirmValue || confirmValue.trim().length() == 0) {
			this.confirmValue = null;
		} else {
			this.confirmValue = confirmValue.trim();
		}
	}

	public void setAllowNull(boolean allowNull) { this.allowNull = allowNull; }
	public boolean getAllowNull() { return allowNull; }
	public void setMaxLength(int maxLength) { this.maxLength = maxLength; }
	public int getMaxLength() { return maxLength; }
	public void setMinLength(int minLength) { this.minLength = minLength; }
	public int getMinLength() { return minLength; }
	public void setRequiresConfirm(boolean requiresConfirm) { this.requiresConfirm = requiresConfirm; }
	public boolean getRequiresConfirm() { return requiresConfirm; }
	public Object getParsedValue() { return(parsedValue); }
	public void setOnlyIfFieldName(String onlyIfFieldName) { this.onlyIfFieldName = onlyIfFieldName; }
	public String getOnlyIfFieldName() { return onlyIfFieldName; }
	public void setOnlyIfFieldValue(String onlyIfFieldValue) { this.onlyIfFieldValue = onlyIfFieldValue; }
	public String getOnlyIfFieldValue() { return onlyIfFieldValue; }
	public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
	public String getErrorMessage() { return errorMessage; }
	public Set<String> getAllowableValues() { return allowableValues; }
	public void setOrField(String orField) { this.orField = orField; }
	public String getOrField() { return orField; }
	public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
	public String getDefaultValue() { return defaultValue; }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");
		stringBuilder.append("\t\"type\": \"" + this.getClass().getSimpleName() + "\", \n");
		stringBuilder.append("\t\"name\": \"" + name + "\", \n");
		stringBuilder.append("\t\"allowNull\": " + allowNull + ", \n");
		stringBuilder.append("\t\"requiresConfirm\": " + requiresConfirm + ", \n");
		stringBuilder.append("\t\"maxLength\": " + maxLength + ", \n");
		stringBuilder.append("\t\"minLength\": " + minLength + ", \n");
		stringBuilder.append("\t\"value\": " + value + ", \n");
		stringBuilder.append("\t\"defaultValue\": " + defaultValue + ", \n");
		stringBuilder.append("\t\"errorMessage\": \"" + errorMessage + "\", \n");
		stringBuilder.append("\t\"onlyIfFieldName\": \"" + onlyIfFieldName + "\", \n");
		stringBuilder.append("\t\"onlyIfFieldValue\": \"" + onlyIfFieldValue + "\"\n");
		stringBuilder.append("}");
		return (stringBuilder.toString());
	}

}
