package synapticloop.h2zero.base.validator.bean;

import org.json.JSONObject;

public class ValidationFieldBean {
	private String fieldName = null;
	private String fieldValue = null;
	private boolean isOverLength = false;
	private boolean isUnderLength = false;
	private boolean isIncorrectNullability = false;
	private boolean isIncorrectForeignKey = false;

	private boolean isValid = true;

	public ValidationFieldBean(String fieldName, String fieldValue, boolean isOverLength, boolean isUnderLength, boolean isIncorrectNullability) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.isOverLength = isOverLength;
		this.isUnderLength = isUnderLength;
		this.isIncorrectNullability = isIncorrectNullability;

		if(isOverLength) { this.isValid = false; }
		if(isUnderLength) { this.isValid = false; }
		if(isIncorrectNullability) { this.isValid = false; }
	}

	public String getFieldName() { return(fieldName); }
	public String getFieldValue() { return(fieldValue); }
	public boolean getIsOverLength() { return(isOverLength); }
	public boolean getIsUnderLength() { return(isUnderLength); }
	public boolean getIsIncorrectNullability() { return(isIncorrectNullability); }

	public void setIsIncorrectForeignKey(boolean isIncorrectForeignKey) { 
		this.isIncorrectForeignKey = isIncorrectForeignKey;
		if(isIncorrectForeignKey) {
			this.isValid = false;
		}
	}

	public boolean getIsIncorrectForeignKey() { return(isIncorrectForeignKey);}

	public boolean getIsValid() { return(this.isValid); }

	public JSONObject toJSON() {
		JSONObject validationObject = new JSONObject();

		validationObject.put("fieldName", fieldName);
		validationObject.put("fieldValue", fieldValue);
		validationObject.put("isOverLength", isOverLength);
		validationObject.put("isUnderLength", isUnderLength);
		validationObject.put("isIncorrectNullability", isIncorrectNullability);
		validationObject.put("isIncorrectForeignKey", isIncorrectForeignKey);
		validationObject.put("isValid", !(isOverLength && isUnderLength && isIncorrectNullability && isIncorrectForeignKey));

		return(validationObject);
	}

	public String toJSONString() {
		JSONObject jsonObject= new JSONObject();

		jsonObject.put(fieldName, toJSON());

		return(jsonObject.toString());
	}
}
