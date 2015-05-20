package synapticloop.h2zero.ant;

/*
 * Copyright (c) 2013-2015 synapticloop.
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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import synapticloop.h2zero.ant.quick.Database;
import synapticloop.h2zero.ant.quick.Table;

public class H2ZeroQuickTask extends Task {
	private String schema = "your_schema_here";
	private String javaPackage = "your.package.here";
	private String tables = null;
	private String foreign = null;

	@Override
	public void execute() throws BuildException {
		if(null == tables || null == foreign) {
			getProject().log("Attributes 'tables', 'foreign' are required, exiting...", Project.MSG_ERR);
			return;
		}

		Database database = new Database(schema, javaPackage);

		String[] splitTables = tables.split(",");
		for (int i = 0; i < splitTables.length; i++) {
			String splitTable = splitTables[i].trim();
			database.addTable(new Table(splitTable));
		}

		if(foreign.trim().length() != 0) {
			String[] splitForeigns = foreign.split(",");
			for (int i = 0; i < splitForeigns.length; i++) {
				String splitForeign = splitForeigns[i].trim();
				String[] fromTo = splitForeign.split("\\.");
				if(fromTo.length != 2) {
					getProject().log("Could not read foreign key of '" + splitForeign + "' as it is not in the format <table_from>.<table_to>", Project.MSG_ERR);
					return;
				}
				if(!database.addForeignKey(fromTo[0], fromTo[1])) {
					getProject().log("Could not lookup foreign key references of '" + splitForeign + "' as either <table_from> or <table_to> does not exist.", Project.MSG_ERR);
					return;
				}
			}
		}

		getProject().log(database.toString());
	}

	public String getSchema() { return schema; }
	public void setSchema(String schema) { this.schema = schema; }
	public String getPackage() { return javaPackage; }
	public void setPackage(String javaPackage) { this.javaPackage = javaPackage; }

	public String getTables() { return tables; }
	public void setTables(String tables) { this.tables = tables; }
	public String getForeign() { return foreign; }
	public void setForeign(String foreign) { this.foreign = foreign; }

}
