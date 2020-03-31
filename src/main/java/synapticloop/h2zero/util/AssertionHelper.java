package synapticloop.h2zero.util;

/*
 * Copyright (c) 2012-2020 synapticloop.
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

import synapticloop.h2zero.exception.H2ZeroParseException;

public class AssertionHelper {

	private AssertionHelper() {}

	public static void assertNotNull(String key, String value) throws H2ZeroParseException {
		if(null == value) {
			throw new H2ZeroParseException("Value for key '" + key + "' cannot be null.");
		}
	}
}
