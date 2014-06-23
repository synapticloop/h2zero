package synapticloop.h2zero.base.form.field;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.json.JSONObject;

public class EmailFormField extends BaseFormField {

	public EmailFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public EmailFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}


	@Override
	public boolean isValid() {
		if(!passesDefaultChecks()) {
			return(false);
		}

		if(null == value) {
			if(allowNull) {
				return(allowNull);
			}
		} else {
			boolean result = true;
			try {
				new InternetAddress(value);
				if(!hasNameAndDomain(value)){
					result = false;
				}
			} catch (AddressException jmiaex){
				result = false;
			}

			if(result) {
				this.parsedValue = value;
			}
			return(result);

		}
		return(true);
	}


	private static boolean hasNameAndDomain(String aEmailAddress){
		String[] tokens = aEmailAddress.split("@");
		if(tokens.length != 2) {
			return(false);
		} else {
			String name = tokens[0].trim();
			String domain = tokens[1].trim();
			if(name.trim().length() == 0 || domain.trim().length() == 0) {
				return(false);
			}

			if(name.endsWith(".") || name.startsWith(".")) {
				return(false);
			}

			if(domain.endsWith(".") || domain.startsWith(".")) {
				return(false);
			}
			if(!domain.contains(".")) {
				return(false);
			}
		}
		return(true);
	}}

