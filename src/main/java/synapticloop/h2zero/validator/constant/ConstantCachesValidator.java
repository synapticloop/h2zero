package synapticloop.h2zero.validator.constant;

/*
 * Copyright (c) 2013-2024 synapticloop.
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

import synapticloop.h2zero.model.ConstantCache;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseValidator;

import java.util.List;

/**
 * Validate that the constant table's cache constants - this will put a warning in
 * the log if the cache constant is the primary - key - we already generate this
 * for the primary key and it is redundant - see ALL_LOOKUP
 * 
 * @author synapticloop
 *
 */

public class ConstantCachesValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			if(table.getIsConstant()) {
				for (ConstantCache constantCache : table.getConstantCaches()) {
					if(constantCache.getCacheField().getPrimary()) {
						addWarnMessage("Constant model '" + table.getName() + "' has a redundant cache constant field of '" + constantCache.getCacheField().getName() + "' which is a primary key - see the ALL_LOOKUP static map");
					}
				}
			}
		}
	}

}
