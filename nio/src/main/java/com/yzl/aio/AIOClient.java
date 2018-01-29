package com.yzl.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 向服务端发送一条消息，从服务端读取回执
 */
public class AIOClient {
    public static void main(String[] args) throws Exception {
        final AsynchronousSocketChannel clientChannel = AsynchronousSocketChannel.open();
        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 8001);
        clientChannel.connect(serverAddress).get(1, TimeUnit.SECONDS);

        for (int i = 0; i < 1000; i++) {
            ByteBuffer writeBuffer = ByteBuffer.wrap(("Hello abcdefghijklmnopqrstuvwxyz" + i).getBytes());
            //这里使用一个CountDownLatch，保证从服务端读取回执后再发送下一条，同时使用一个通道发送，会抛异常
            CountDownLatch countDownLatch = new CountDownLatch(1);
            clientChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer writeBuffer) {
                    writeBuffer.flip();
                    System.out.println("write success " + new String(writeBuffer.array(), 0, writeBuffer.limit()));
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //从服务端读取返回，最多等10秒
                    clientChannel.read(readBuffer, 2, TimeUnit.SECONDS, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer readBuffer) {
                            readBuffer.flip();
                            System.out.println("receive: " + new String(readBuffer.array(), 0, readBuffer.limit()));
                            readBuffer.clear();
                            //读完了才countDown，如果服务端发送数据大于缓冲区大小，这里是处理不了的，还需要进行包的拆分和粘连
                            countDownLatch.countDown();
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer readBuffer) {
                            //失败了要countDown
                            countDownLatch.countDown();
                        }
                    });

                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    //失败了要countDown
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
        }
        clientChannel.close();
        System.out.println("channel close success!");
    }

}
