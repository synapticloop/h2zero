package synapticloop.h2zero.exception;

/*
 * Copyright (c) 2012-2013 synapticloop.
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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class H2ZeroParseExceptionTest {
	private H2ZeroParseException h2ZeroParseException;

	@Before
	public void setup() {
		h2ZeroParseException = new H2ZeroParseException("this is a test message");
	}

	@Test
	public void testException() {
		h2ZeroParseException = new H2ZeroParseException("this is a test message");
	}

}
