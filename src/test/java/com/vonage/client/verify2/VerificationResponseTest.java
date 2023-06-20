/*
 *   Copyright 2023 Vonage
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.vonage.client.verify2;

import com.vonage.client.VonageResponseParseException;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.UUID;

public class VerificationResponseTest {

	@Test
	public void testConstructFromValidJson() {
		UUID rqid = UUID.randomUUID();
		VerificationResponse response = VerificationResponse.fromJson("{\"request_id\":\""+rqid+"\"}");
		assertEquals(rqid, response.getRequestId());
		String toString = response.toString();
		assertTrue(toString.contains("VerificationResponse"));
		assertTrue(toString.contains(rqid.toString()));
	}

	@Test
	public void testConstructFromEmptyJson() {
		VerificationResponse response = VerificationResponse.fromJson("{}");
		assertNull(response.getRequestId());
	}

	@Test(expected = VonageResponseParseException.class)
	public void testConstructFromInvalidJson() {
		VerificationResponse.fromJson("{_malformed_}");
	}

	@Test(expected = VonageResponseParseException.class)
	public void testConstructFromEmptyString() {
		VerificationResponse.fromJson("");
	}
}
