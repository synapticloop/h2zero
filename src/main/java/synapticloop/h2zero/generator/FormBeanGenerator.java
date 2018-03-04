package synapticloop.h2zero.generator;

import java.io.File;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;

/*
 * Copyright (c) 2012-2018 synapticloop.
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

//import java.io.File;
//import java.util.Iterator;
//import java.util.List;
//
//import synapticloop.h2zero.model.Database;
//import synapticloop.h2zero.model.Options;
//import synapticloop.h2zero.model.Table;
//import synapticloop.h2zero.model.form.Form;
//import synapticloop.h2zero.util.SimpleLogger;
//import synapticloop.h2zero.util.SimpleLogger.LoggerType;
//import synapticloop.templar.Parser;
//import synapticloop.templar.exception.FunctionException;
//import synapticloop.templar.exception.ParseException;
//import synapticloop.templar.exception.RenderException;
//import synapticloop.templar.utils.TemplarContext;

public class FormBeanGenerator extends Generator {

	public FormBeanGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() throws RenderException, ParseException {
		// TODO Auto-generated method stub
		
	}
//
//	public FormBeanGenerator(Database database, Options options, File outFile, boolean verbose) {
//		super(database, options, outFile, verbose);
//	}
//
//	@Override
//	public void generate() throws RenderException, ParseException {
//		if(!options.hasGenerator(Options.OPTION_FORMBEANS)) {
//			return;
//		}
//
//		Parser javaCreateDefaultFormBeanParser = getParser("/java-create-default-form-bean-create.templar");
//		Parser javaCreateFormBeanParser = getParser("/java-create-form-bean.templar");
//
//		TemplarContext templarContext = null;
//		try {
//			templarContext = getDefaultTemplarContext();
//		} catch (FunctionException fex) {
//			throw new RenderException("Could not instantiate the function.", fex);
//		}
//
//		// now for the tables
//		List<Table> tables = database.getTables();
//		Iterator<Table> tableIterator = tables.iterator();
//
//		while (tableIterator.hasNext()) {
//			Table table = tableIterator.next();
//			templarContext.add("table", table);
//			SimpleLogger.logInfo(LoggerType.GENERATE_FORM_BEANS, "Generating for table '" + table.getName() + "'.");
//
//			// the default form beans
//			String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/form/auto/" + table.getJavaClassName() + "CreateFormBean.java";
//			renderToFile(templarContext, javaCreateDefaultFormBeanParser, pathname);
//		}
//
//		// now for the forms
//		List<Form> forms = database.getForms();
//		Iterator<Form> formsIterator = forms.iterator();
//
//		while (formsIterator.hasNext()) {
//			Form form = formsIterator.next();
//			templarContext.add("form", form);
//
//			if(options.hasGenerator(Options.OPTION_FORMBEANS)) {
//				String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/form/" + form.getName() + "Bean.java";
//				renderToFile(templarContext, javaCreateFormBeanParser, pathname);
//			}
//		}
//	}

}
