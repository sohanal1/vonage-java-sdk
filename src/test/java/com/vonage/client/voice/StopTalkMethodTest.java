package com.vonage.client.voice;

import com.vonage.client.HttpConfig;
import com.vonage.client.HttpWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class StopTalkMethodTest {
    private static final Log LOG = LogFactory.getLog(StopTalkMethodTest.class);

    private StopTalkMethod method;

    @Before
    public void setUp() throws Exception {
        method = new StopTalkMethod(new HttpWrapper());
    }

    @Test
    public void makeRequestTest() throws Exception {
        RequestBuilder request = method.makeRequest("63f61863-4a51-4f6b-86e1-46edebcf9356");

        assertEquals("DELETE", request.getMethod());
        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), request.getFirstHeader("Content-Type").getValue());
        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), request.getFirstHeader("Accept").getValue());

        assertTrue(request.getUri().toString().contains("63f61863-4a51-4f6b-86e1-46edebcf9356"));
        assertTrue(request.getUri().toString().contains("talk"));
    }

    @Test
    public void parseResponseTest() throws IOException {

        HttpResponse stubResponse = new BasicHttpResponse(
                new BasicStatusLine(new ProtocolVersion("1.1", 1, 1), 200, "OK")
        );

        String json = "{\"message\":\"Talk stopped\", \"uuid\":\"63f61863-4a51-4f6b-86e1-46edebcf9356\" }";
        InputStream jsonStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(jsonStream);
        stubResponse.setEntity(entity);

        TalkResponse response = method.parseResponse(stubResponse);
        assertEquals("Talk stopped", response.getMessage());
        assertEquals("63f61863-4a51-4f6b-86e1-46edebcf9356", response.getUuid());
    }

    @Test
    public void customUriTest() throws UnsupportedEncodingException {
        String expectedUri = "https://example.com/v1/calls/63f61863-4a51-4f6b-86e1-46edebcf9356/talk";
        HttpWrapper wrapper = new HttpWrapper(HttpConfig.builder().baseUri("https://example.com").build());

        method = new StopTalkMethod(wrapper);
        RequestBuilder builder = method.makeRequest("63f61863-4a51-4f6b-86e1-46edebcf9356");

        assertEquals(expectedUri, builder.getUri().toString());

    }
}