package com.yzl.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 接收客户端发送数据，返回
 */
public class AIOEchoServer {
    private final int port;

    private AsynchronousServerSocketChannel server = null;

    public AIOEchoServer(int port) {
        this.port = port;
    }

    public void listen() throws IOException {
        server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
        //accept客户端来的连接，生成一个channel，异步回调CompletionHandler
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel channel, Object attachment) {
                //这里得到一个channel，在这个channel上读取数据
                ByteBuffer readBuff = ByteBuffer.allocate(10);
                try {
                    //异步读取数据
                    channel.read(readBuff, readBuff, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer readBuff) {
                            //没有数据，就
                            if (result == -1) {
                                try {
                                    channel.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            readBuff.flip();
                            if (readBuff.remaining() > 0) {
                                String value = new String(readBuff.array(), 0, readBuff.limit());
                                System.out.println("receive from client: " + value);
                                try {
                                    value = value + " from server.";
                                    System.out.println("write to client: " + value);
                                    //写回客户端，用同步的方式，也可以用异步方式。如果客户端数据一次没有读取完，往channel里面写会抛异常，需要粘包
                                    channel.write(ByteBuffer.wrap((value).getBytes())).get();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                readBuff.clear();
                                channel.read(readBuff, readBuff, this);
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //继续接收其他的客户端连接，如果不加这一行，只能连接一个客户端
                    server.accept(null, this);
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });
        System.out.println("Server listen on " + port);
    }

    public static void main(String[] args) throws Exception {
        new AIOEchoServer(8001).listen();
        while (true) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
