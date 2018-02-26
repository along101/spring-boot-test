package com.yzl.test.mock;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.hamcrest.CoreMatchers.equalTo;

public class MockServerTest {
    @Rule
    public MockServerRule server = new MockServerRule(this, 5000);

    @Test
    public void testMockServer() throws IOException {
        MockServerClient mockClient = new MockServerClient("localhost", 5000);
        String expected = "{ message: 'incorrect username and password combination' }";
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("GET")
// .withHeader(new Header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN))
// .withQueryStringParameter(new Parameter("my-token", "12345"))
        ).respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:5000/hello/John");
        CloseableHttpResponse response = client.execute(httpGet);
//验证输出是否是正确
        InputStream content = response.getEntity().getContent();
        InputStreamReader inputStreamReader = new InputStreamReader(content);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String responseText = bufferedReader.readLine();
        assertThat(responseText, equalTo(expected));


    }
}
