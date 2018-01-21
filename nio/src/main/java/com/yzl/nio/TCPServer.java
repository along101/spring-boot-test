package com.yzl.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//TODO 服务端用多线程处理socket请求，每次只能写入一部分数据到缓冲区，流如果没有结束，如何保证是同一个线程处理相同的channel？
public class TCPServer {

    private ExecutorService pool;

    public TCPServer() {
        int cpu = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(cpu);
    }

    public void listen(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务端启动了。");

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            System.out.println("循环");
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println(selectionKeys.size());
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 删除已选的key 以防重复处理
                iterator.remove();
                if (selectionKey.isAcceptable()) {//如果有客户端连接进来
                    // 先拿到这个SelectionKey里面的ServerSocketChannel。
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                    // 获得和客户端连接的通道
                    SocketChannel socketChannel = serverSocketChannel1.accept();
                    socketChannel.configureBlocking(false);//将此通道设置为非阻塞

                    //为了接收客户端发送过来的数据，需要将此通道绑定到选择器上，并为该通道注册读事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {//客户端发送数据过来了
                    //先拿到这个SelectionKey里面的SocketChannel。
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    pool.submit(new Worker(socketChannel));
                }
            }
        }
    }

    public static class Worker implements Runnable {

        private SocketChannel socketChannel;

        public Worker(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            //接收来自于客户端发送过来的数据
            ByteBuffer buf = ByteBuffer.allocate(100);
            try {
                if (socketChannel.read(buf) <= 0) {
                    return;
                }
                byte[] receivedData = buf.array();
                String msg = new String(receivedData).trim();
                System.out.println("receivedData：" + msg);
                buf.clear();
                socketChannel.write(ByteBuffer.wrap("收到信息!!!".getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        TCPServer tcpServer = new TCPServer();
        tcpServer.listen(1978);
    }
}