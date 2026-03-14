package com.synapticloop.h2zero.base.model;

/*
 * Copyright (c) 2012-2026 synapticloop.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>This is the base class for all h2zero generated models and defines the
 * required functionality for a working model.  It contains methods to insert,
 * update and delete itself.</p>
 */
public abstract class ConstantModelBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConstantModelBase.class);

	protected static final String JSON_KEY_TYPE = "type";
	protected static final String JSON_KEY_NAME = "name";
	protected static final String JSON_KEY_FIELDS = "fields";
	protected static final String JSON_KEY_TOTAL = "total";

	protected static final String JSON_VALUE_TABLE = "table";
	protected static final String JSON_VALUE_VIEW = "view";

	// TODO - need abstract tasks in ModelBase
}
