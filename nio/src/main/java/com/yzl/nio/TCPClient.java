package com.yzl.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPClient {
    private String host;
    private int port;
    SocketChannel socketChannel;

    private void connect(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        // 打开监听信道并设置为非阻塞模式
        socketChannel = SocketChannel.open(new InetSocketAddress(this.host, this.port));
        socketChannel.configureBlocking(false);

        // 打开并注册选择器到信道
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        selector.select();
                        Set<SelectionKey> set = selector.selectedKeys();
                        Iterator<SelectionKey> ite = set.iterator();

                        while (ite.hasNext()) {
                            SelectionKey selectionKey = ite.next();
                            ite.remove(); //删除已选的key,以防重复处理
                            if (selectionKey.isConnectable()) {//看是否有连接发生
                                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                //如果正在连接，则完成连接
                                if (socketChannel.isConnectionPending()) {
                                    socketChannel.finishConnect();
                                }
                                socketChannel.configureBlocking(false);//设置为非阻塞模式
                                //给服务器端发送数据
                                System.out.println("客户端连接上了服务器端。。。。");
                                //为了接收来自服务器端的数据，将此通道注册到选择器中
                                socketChannel.register(selector, SelectionKey.OP_READ);
                            } else if (selectionKey.isReadable()) {
                                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                //接收来自于服务器端发送过来的数据
                                ByteBuffer buf = ByteBuffer.allocate(1024);
                                socketChannel.read(buf);
                                byte[] receiveData = buf.array();
                                String msg = new String(receiveData).trim();
                                System.out.println("接收来自服务器端的数据为：" + msg);
                                buf.clear();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void sendMsg(String message) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes());
        socketChannel.write(writeBuffer);
    }

    public static void main(String[] args) throws IOException {
        TCPClient client = new TCPClient();
        client.connect("localhost", 1978);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            s.append("1");
        }
        client.sendMsg(s.toString());
    }
}
