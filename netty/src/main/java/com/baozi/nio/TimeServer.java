package com.baozi.nio;

/**
 * Created by baozi on 2018/2/2.
 */
public class TimeServer {

    public static void main(String[] args) {

        int port = 8080;

        new Thread(new MultiplexerTimeServer(port), "NIO-MultiplexerTimeServer-001").start();
    }
}
