package synapticloop.h2zero.exception;

/*
 * Copyright (c) 2012-2023 synapticloop.
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

public class H2ZeroParseException extends Exception {
	private static final long serialVersionUID = -319281700923415030L;

	/**
	 * Create a new exception
	 * 
	 * @param message The message to pass in
	 */
	public H2ZeroParseException(String message) {
		super(message);
	}

	/**
	 * Create a new exception
	 * 
	 * @param message The message to pass in
	 * @param cause The root cause to wrap
	 */
	public H2ZeroParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
