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
package com.vonage.client.video;

import com.vonage.client.HttpConfig;
import com.vonage.client.HttpWrapper;
import com.vonage.client.TestUtils;
import com.vonage.client.VonageBadRequestException;
import com.vonage.client.auth.JWTAuthMethod;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.*;
import java.util.UUID;

public class SendDtmfToSessionEndpointTest {
	private SendDtmfToSessionEndpoint endpoint;
	private final String applicationId = UUID.randomUUID().toString();
	
	@BeforeEach
	public void setUp() {
		endpoint = new SendDtmfToSessionEndpoint(new HttpWrapper(
			new JWTAuthMethod(applicationId, new byte[0])
		));
	}

	@Test
	public void testMakeRequest() throws Exception {
		String sessionId = "S01", connectionId = "conn23", digits = "*0123456789#";
		SendDtmfRequest request = new SendDtmfRequest(sessionId, connectionId, digits);
		RequestBuilder builder = endpoint.makeRequest(request);
		Assertions.assertEquals("POST", builder.getMethod());
		String expectedUri = "https://video.api.vonage.com/v2/project/" +
				applicationId+"/session/"+sessionId+"/play-dtmf";
		Assertions.assertEquals(expectedUri, builder.build().getURI().toString());
		Assertions.assertEquals(ContentType.APPLICATION_JSON.getMimeType(), builder.getFirstHeader("Content-Type").getValue());
		String expectedRequest = "{\"digits\":\""+digits+"\"}";
		Assertions.assertEquals(expectedRequest, EntityUtils.toString(builder.getEntity()));
	}

	@Test
	public void testCustomUri() throws Exception {
		String baseUri = "http://example.com";
		HttpWrapper wrapper = new HttpWrapper(
				HttpConfig.builder().videoBaseUri(baseUri).build(),
				new JWTAuthMethod("app-id", new byte[0])
		);
		endpoint = new SendDtmfToSessionEndpoint(wrapper);
		SendDtmfRequest request = new SendDtmfRequest("sesh", "Part", "p90");
		String expectedUri = baseUri + "/v2/project/app-id/session/"+request.sessionId+"/play-dtmf";
		RequestBuilder builder = endpoint.makeRequest(request);
		Assertions.assertEquals(expectedUri, builder.build().getURI().toString());
		Assertions.assertEquals(ContentType.APPLICATION_JSON.getMimeType(), builder.getFirstHeader("Content-Type").getValue());
		String expectedRequest = "{\"digits\":\""+request.digits+"\"}";
		Assertions.assertEquals(expectedRequest, EntityUtils.toString(builder.getEntity()));
		Assertions.assertEquals("POST", builder.getMethod());
	}

	@Test
	public void test400Response() throws Exception {
		assertThrows(VonageBadRequestException.class, () ->
				endpoint.parseResponse(TestUtils.makeJsonHttpResponse(400, ""))
		);
	}
}