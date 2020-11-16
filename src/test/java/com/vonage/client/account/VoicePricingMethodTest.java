/*
 *   Copyright 2020 Vonage
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
package com.vonage.client.account;

import com.vonage.client.HttpConfig;
import com.vonage.client.HttpWrapper;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VoicePricingMethodTest {
    private VoicePricingMethod method;

    @Before
    public void setUp() throws Exception {
        method = new VoicePricingMethod(new HttpWrapper());
    }

    @Test
    public void testDefaultUri() throws Exception {
        PricingRequest request = new PricingRequest("country");

        RequestBuilder builder = method.makeRequest(request);
        assertEquals("GET", builder.getMethod());
        assertEquals("https://rest.nexmo.com/account/get-pricing/outbound/voice?country=country",
                builder.build().getURI().toString()
        );
    }

    @Test
    public void testCustomUri() throws Exception {
        HttpWrapper wrapper = new HttpWrapper(HttpConfig.builder().baseUri("https://example.com").build());
        VoicePricingMethod method = new VoicePricingMethod(wrapper);
        PricingRequest request = new PricingRequest("country");

        RequestBuilder builder = method.makeRequest(request);
        assertEquals("GET", builder.getMethod());
        assertEquals(
                "https://example.com/account/get-pricing/outbound/voice?country=country",
                builder.build().getURI().toString()
        );
    }
}
