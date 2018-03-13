package com.baozi.nio;

/**
 * Created by baozi on 2018/2/2.
 */
public class TimeClient {

    public static void main(String[] args) {

        int port = 8080;

        new Thread(new TimeClientHandle(null, port)).start();
    }
}
