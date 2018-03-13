package com.baozi.bio.bio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by baozi on 2018/2/2.
 */
public class TimeServer {


    public static void main(String[] args) throws IOException {

        int port = 8080;

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Server start in port: " + port);
            Socket socket = null;
            while (true) {
                // 传统BIO当中accept是阻塞的，所以不用担心CPU空转,不用担心会创建到线程
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.close();
                server = null;
            }
        }
    }
}

class TimeServerHandler implements Runnable{

    Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
        System.out.println("新来的连接");
    }

    public void run() {

        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            String currentTime = null;
            String body = null;

            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }

                System.out.println("The time server receive order :" + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                out.println(currentTime);
            }

        } catch (Exception e) {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
