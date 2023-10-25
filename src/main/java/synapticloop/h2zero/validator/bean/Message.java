package synapticloop.h2zero.validator.bean;

/*
 * Copyright (c) 2013-2023 synapticloop.
 * 
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


public class Message {
	private String type;
	private String content;

	public Message(String type, String content) {
		this.type = type;
		this.content = content;
	}

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getContent() { return content; }
	public void setContent(String message) { this.content = message; }
}
