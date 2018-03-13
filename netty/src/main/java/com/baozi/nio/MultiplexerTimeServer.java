package com.baozi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by baozi on 2018/2/2.
 */
public class MultiplexerTimeServer implements Runnable{

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;


    public MultiplexerTimeServer(int port) {

        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);

            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("The time server is start in port : " + port);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    public void run() {

        while (!stop) {

            try {
                int count = selector.select();
                if (count == 0) continue;

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iKeys = keys.iterator();

                while (iKeys.hasNext()) {
                    SelectionKey key = iKeys.next();
                    iKeys.remove();

                    try {
                        //执行
                        handleInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }

            } catch (Exception e) {

            } finally {

            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void handleInput(SelectionKey key) throws IOException {

        if (key.isValid()) {
            if (key.isAcceptable()) {
                //对于服务端的连接确认事件,只能获取到ServerSocketChannel
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                //获取客户端连接,此方法阻塞
                SocketChannel channel = ssc.accept();
                channel.configureBlocking(false);
                System.out.println("服务端收到连接");

                channel.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                System.out.println("收到读事件了");
                SocketChannel sc = (SocketChannel) key.channel();

                ByteBuffer readBf = ByteBuffer.allocate(1024);
                //这里是异步操作,也有可能出现读半包问题
                int readBytes = sc.read(readBf);
                if (readBytes > 0) {
                    //切换读模式
                    readBf.flip();

                    //创建byte数组,大小为缓冲的大小
                    byte[] bytes = new byte[readBf.remaining()];
                    readBf.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + body);

                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    try {
                        doWrite(sc, currentTime);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    public void doWrite(SocketChannel sc, String time) throws IOException{
        byte[] bytes = time.getBytes();
        ByteBuffer writeBf = ByteBuffer.allocate(bytes.length);
        writeBf.put(bytes);
        writeBf.flip();
        //由于写操作是一个异步操作,所以有可能出现写半包的问题
        sc.write(writeBf);
        System.out.println("Receiver time");
    }
}
