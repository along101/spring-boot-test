package com.yzl.test.httpclient;

import okhttp3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class OKHttpClientTest {
    private final OkHttpClient client = new OkHttpClient();

    @Before
    public void setUp() throws Exception {
        TestServer.start();
    }

    @After
    public void tearDown() throws Exception {
        TestServer.stop();
    }

    @Test
    public void test1() throws IOException {
        String uri = "http://localhost:8080/test";
        Request request = new Request.Builder()
                .url(uri)
                .header("x-serialization", "protobuf.json")
                .header("connection", "Keep-Alive")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    /**
     * 异步
     * okhttp异步发送请求并没有使用java的nio或者aio模型发送，只是新开一个线程异步执行请求，客户端服服务端直接还是同步的
     * 参见okhttp3.RealCall类的getResponse方法，engine.readResponse();这行请求会阻塞
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        String uri = "http://localhost:8080/test";
        Request request = new Request.Builder()
                .url(uri)
                .header("x-serialization", "protobuf.json")
                .header("connection", "Keep-Alive")
                .get()
                .build();
        CountDownLatch latch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
                latch.countDown();
            }
        });
        latch.await();
    }
}
