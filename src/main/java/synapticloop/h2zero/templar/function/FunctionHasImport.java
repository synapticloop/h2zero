package synapticloop.h2zero.templar.function;

import synapticloop.h2zero.model.Table;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.TemplarContext;

public class FunctionHasImport extends Function {
	public FunctionHasImport() {
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
				
			} else {
				throw new FunctionException("The function 'hasImport' takes exactly two arguments, which must be of type ['synapticloop.h2zero.model.Table', 'java.lang.String'], found arguments of ['" + tableObject.getClass().getCanonicalName() + "', '" + fieldTypeObject.getClass().getCanonicalName() + "']." );
			}
		} else {
			throw new FunctionException("The function 'hasImport' takes exactly 2 arguments.");
		}
		return(false);
	}

}
