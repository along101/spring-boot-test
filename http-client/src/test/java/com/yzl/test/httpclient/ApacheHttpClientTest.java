package com.yzl.test.httpclient;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApacheHttpClientTest {

    private CloseableHttpClient client = buildHttpClient();

    @Before
    public void setUp() throws Exception {
        TestServer.start();
    }

    @After
    public void tearDown() throws Exception {
        TestServer.stop();
    }

    @Test
    public void testGet() throws Exception {
        String uri = "http://localhost:8080/test";
        HttpRequestBase request = (HttpRequestBase) RequestBuilder.get(uri).build();
        try {
            CloseableHttpResponse httpResponse = client.execute(request);
            String rs = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            httpResponse.close();
            System.out.println(rs);
        } finally {
            request.releaseConnection();
        }

    }


    protected CloseableHttpClient buildHttpClient() {
        HttpClientConnectionManager httpClientConnectionManager = buildConnectionManager();

        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(0, false);

        ConnectionKeepAliveStrategy myStrategy = (response, context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
            return 30 * 1000;
        };

        return HttpClients.custom()
                .disableContentCompression()
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(config)
                .setKeepAliveStrategy(myStrategy)
                .setRetryHandler(retryHandler)
                .disableCookieManagement()
                .build();
    }

    protected HttpClientConnectionManager buildConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);
        return connectionManager;
    }
}
