package synapticloop.h2zero.templar.function;

import synapticloop.h2zero.model.Table;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.TemplarContext;

public class FunctionRequiresImport extends Function {
	public static final String FUNCTION_NAME = "requiresImport";

	public FunctionRequiresImport() {
		super(2);
	}

	@Override
	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(verifyArgumentLength(args)) {
			Object tableObject = args[0];
			Object fieldTypeObject = args[0];
			if(tableObject instanceof Table && fieldTypeObject instanceof String) {
				Table table = (Table)tableObject;
				String fieldType = (String)fieldTypeObject;
				return(table.requiresImport(fieldType));
			} else {
				throw new FunctionException("The function '" + FUNCTION_NAME + "' takes exactly two arguments, which must be of type ['" + Table.class.getCanonicalName() + "', '" + String.class.getCanonicalName() + "'], found arguments of ['" + tableObject.getClass().getCanonicalName() + "', '" + fieldTypeObject.getClass().getCanonicalName() + "']." );
			}
		} else {
			throw new FunctionException("The function '" + FUNCTION_NAME + "' takes exactly 2 arguments.");
		}
	}

}
