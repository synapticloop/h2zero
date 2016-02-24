package synapticloop.h2zero.templar.function;

import synapticloop.h2zero.model.BaseSchemaObject;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class FunctionRequiresImport extends Function {
	public static final String FUNCTION_NAME = "requiresImport";

	public FunctionRequiresImport() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object tableObject = null;
		if(args[0] instanceof BaseSchemaObject) {
			tableObject = args[0];
		} else {
			ObjectHelper.evaluateObjectToDefaultBoolean(args[0], templarContext);
			tableObject = ObjectHelper.evaluateObjectToDefaultBoolean(args[0].toString(), templarContext);
		}

		Object fieldTypeObject = ObjectHelper.evaluateObjectToDefault(args[1], templarContext);

		if(tableObject instanceof BaseSchemaObject && fieldTypeObject instanceof String) {
			BaseSchemaObject baseSchemaObject = (BaseSchemaObject)tableObject;
			String fieldType = (String)fieldTypeObject;
			return(baseSchemaObject.requiresImport(fieldType));
		} else {
			throw new FunctionException("The function '" + FUNCTION_NAME + "' takes exactly two arguments, which must be of type ['" + BaseSchemaObject.class.getCanonicalName() + "', '" + String.class.getCanonicalName() + "'], found arguments of ['" + tableObject.getClass().getCanonicalName() + "', '" + fieldTypeObject.getClass().getCanonicalName() + "']." );
		}
	}
}
