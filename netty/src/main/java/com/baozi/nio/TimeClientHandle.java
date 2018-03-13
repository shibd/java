package com.baozi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by baozi on 2018/2/2.
 */
public class TimeClientHandle implements Runnable {

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;


    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void run() {

        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!stop) {
            try {
                int count = selector.select();
                if (count == 0) continue;

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iKeys = keys.iterator();
                while (iKeys.hasNext()) {
                    SelectionKey key = iKeys.next();
                    iKeys.remove();
                    handleInput(key);
                }

            } catch (Exception e) {

            } finally {

            }
        }
    }

    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }


    private void handleInput(SelectionKey key) throws IOException {

        if (key.isValid()) {

            SocketChannel sc = (SocketChannel) key.channel();

            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_WRITE);
                } else {
                    System.out.println("连接失败");
                    System.exit(1);
                }
            }

            if (key.isReadable()) {
                ByteBuffer bf = ByteBuffer.allocate(1024);
                int readByetes = sc.read(bf);

                if (readByetes > 0) {
                    bf.flip();
                    byte[] bytes = new byte[bf.remaining()];
                    bf.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is : " + body);
                    this.stop = true;
                } else if (readByetes < 0) {
                    key.cancel();
                    sc.close();
                }
                socketChannel.register(selector, SelectionKey.OP_WRITE);
            }

            if (key.isWritable()) {
                System.out.println("write tims");
                byte[] req = "QUERY TIME ORDER".getBytes();
                ByteBuffer bf = ByteBuffer.allocate(req.length);
                bf.put(req);
                bf.flip();
                sc.write(bf);
                if (!bf.hasRemaining()) {
                    System.out.println("Send order 2 server succeed.");
                }
                socketChannel.register(selector, SelectionKey.OP_READ);
            }

        }

    }
}
