package com.yzl.test.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * 异步客户端测试
 */
public class ApacheHttpAsyncClientTest {

    private CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();

    @Before
    public void setUp() throws Exception {
        TestServer.start();
        httpclient.start();
    }

    @After
    public void tearDown() throws Exception {
        TestServer.stop();
        httpclient.close();
    }

    @Test
    public void testAsync1() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        String uri = "http://localhost:8080/test";
        HttpRequestBase request = (HttpRequestBase) RequestBuilder.get(uri).build();
        httpclient.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse response) {
                latch.countDown();
                System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
            }

            @Override
            public void failed(Exception ex) {
                latch.countDown();
                System.out.println(request.getRequestLine() + "->" + ex);
            }

            @Override
            public void cancelled() {
                latch.countDown();
                System.out.println(request.getRequestLine() + " cancelled");
            }
        });
        System.out.println("httpclient execute return.");
        latch.await();
    }

    @Test
    public void testAsync2() throws Exception {
        String uri = "http://localhost:8080/test";
        HttpRequestBase request = (HttpRequestBase) RequestBuilder.get(uri).build();
        Future<HttpResponse> future = httpclient.execute(request, null);
        HttpResponse response = future.get();
        System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
    }


    @Test
    public void testAsync3() throws Exception {
        String uri = "http://localhost:8080/test";
        HttpRequestBase request = (HttpRequestBase) RequestBuilder.get(uri).build();

        final CountDownLatch latch = new CountDownLatch(1);
        HttpAsyncRequestProducer producer = HttpAsyncMethods.create(request);

        AsyncCharConsumer<HttpResponse> consumer = new AsyncCharConsumer<HttpResponse>() {

            HttpResponse response;

            @Override
            protected void onResponseReceived(final HttpResponse response) {
                //收到response
                this.response = response;
            }

            @Override
            protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
                System.out.println("onCharReceived: " + buf.toString());
            }

            @Override
            protected void releaseResources() {
                //释放资源
                System.out.println("releaseResources");
            }

            @Override
            protected HttpResponse buildResult(final HttpContext context) {
                return this.response;
            }

        };
        //这里传递context，给consumer的buildResult使用
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute("testId", "123");
        httpclient.execute(producer, consumer, context, new FutureCallback<HttpResponse>() {

            public void completed(final HttpResponse response) {
                latch.countDown();
                System.out.println("completed: " + request.getRequestLine() + "->" + response.getStatusLine());
            }

            public void failed(final Exception ex) {
                latch.countDown();
                System.out.println(request.getRequestLine() + "->" + ex);
            }

            public void cancelled() {
                latch.countDown();
                System.out.println(request.getRequestLine() + " cancelled");
            }

        });
        System.out.println("Execute complete.");
        latch.await();
    }
}
