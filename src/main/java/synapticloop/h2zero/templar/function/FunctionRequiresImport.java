package synapticloop.h2zero.templar.function;

import synapticloop.h2zero.model.BaseSchemaObject;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class FunctionRequiresImport extends Function {
	public static final String FUNCTION_NAME = "requiresImport";

	public FunctionRequiresImport() {
		super(2);
	}

	@Override
	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(verifyArgumentLength(args)) {
			Object tableObject = ObjectUtils.evaluateObjectToDefault(args[0].toString(), templarContext);
			Object fieldTypeObject = ObjectUtils.evaluateObjectToDefault(args[1], templarContext);
			if(tableObject instanceof BaseSchemaObject && fieldTypeObject instanceof String) {
				BaseSchemaObject baseSchemaObject = (BaseSchemaObject)tableObject;
				String fieldType = (String)fieldTypeObject;
				return(baseSchemaObject.requiresImport(fieldType));
			} else {
				throw new FunctionException("The function '" + FUNCTION_NAME + "' takes exactly two arguments, which must be of type ['" + BaseSchemaObject.class.getCanonicalName() + "', '" + String.class.getCanonicalName() + "'], found arguments of ['" + tableObject.getClass().getCanonicalName() + "', '" + fieldTypeObject.getClass().getCanonicalName() + "']." );
			}
		} else {
			throw new FunctionException("The function '" + FUNCTION_NAME + "' takes exactly 2 arguments.");
		}
	}

}
