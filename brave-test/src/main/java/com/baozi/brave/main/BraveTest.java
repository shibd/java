package com.baozi.brave.main;

import com.baozi.brave.bean.ClientRequestAdapterImpl;
import com.baozi.brave.bean.ClientResponseAdapterImpl;
import com.baozi.brave.bean.ServerRequestAdapterImpl;
import com.baozi.brave.bean.ServerResponseAdapterImpl;
import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.google.common.collect.ImmutableMap;
import com.twitter.zipkin.gen.Endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BraveTest {

    private static HttpSpanCollector collector = null;
    private static Brave brave = null;
    private static Brave brave2 = null;

    private static void braveInit(){
        collector = HttpSpanCollector.create("http://localhost:9411/", new EmptySpanCollectorMetricsHandler());
        brave = new Brave.Builder("appserver").spanCollector(collector).build();
        brave2 = new Brave.Builder("datacenter").spanCollector(collector).build();
    }

    static class Task {
        String name;
        SpanId spanId;
        public Task(String name, SpanId spanId) {
            super();
            this.name = name;
            this.spanId = spanId;
        }
    }

    public static void main(String[] args) throws Exception {
        braveInit();

        final BlockingQueue<Task> queue = new ArrayBlockingQueue<Task>(10);
        Thread thread = new Thread(){
            public void run() {
                while (true) {
                    try {
                        Task task = queue.take();
                        dcHandle(task.name, task.spanId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        {
            ServerRequestInterceptor serverRequestInterceptor = brave.serverRequestInterceptor();
            ServerResponseInterceptor serverResponseInterceptor = brave.serverResponseInterceptor();
            ClientRequestInterceptor clientRequestInterceptor = brave.clientRequestInterceptor();
            ClientResponseInterceptor clientResponseInterceptor = brave.clientResponseInterceptor();


            Random randomGenerator = new Random();
            long startId = randomGenerator.nextLong();
            SpanId spanId = SpanId.builder().spanId(startId).traceId(startId).parentId(startId).build();

            serverRequestInterceptor.handle(new ServerRequestAdapterImpl("group_data", spanId, ImmutableMap.of("key", "value")));

            //上报错误信息
            brave2.localTracer().submitAnnotation("ss");
            Thread.sleep(1000);
            brave2.localTracer().submitAnnotation("error");

            ClientRequestAdapterImpl clientRequestAdapterImpl = new ClientRequestAdapterImpl("get_radio_list");
            clientRequestInterceptor.handle(clientRequestAdapterImpl);
            queue.offer(new Task("get_radio_list", clientRequestAdapterImpl.getSpanId()));
            Thread.sleep(2000);
            brave2.localTracer().finishSpan();
            clientResponseInterceptor.handle(new ClientResponseAdapterImpl());

            clientRequestAdapterImpl = new ClientRequestAdapterImpl("get_user_list");
            clientRequestInterceptor.handle(clientRequestAdapterImpl);
            queue.offer(new Task("get_user_list", clientRequestAdapterImpl.getSpanId()));
            brave2.localTracer().startNewSpan("", "pks错误");
            brave2.localTracer().submitAnnotation("ss");
            brave2.localTracer().submitAnnotation("error");
            Thread.sleep(3000);
            brave2.localTracer().finishSpan();
            clientResponseInterceptor.handle(new ClientResponseAdapterImpl());

            clientRequestAdapterImpl = new ClientRequestAdapterImpl("get_program_list");
            clientRequestInterceptor.handle(clientRequestAdapterImpl);
            queue.offer(new Task("get_program_list", clientRequestAdapterImpl.getSpanId()));
            brave2.localTracer().startNewSpan("", "pks错误");
            brave2.localTracer().submitAnnotation("ss");
            brave2.localTracer().submitAnnotation("error");
            Thread.sleep(1000);
            brave2.localTracer().finishSpan();
            clientResponseInterceptor.handle(new ClientResponseAdapterImpl());

            serverResponseInterceptor.handle(new ServerResponseAdapterImpl());
        }
        Thread.sleep(3000);
    }

    public static void dcHandle(String spanName, SpanId spanId) throws InterruptedException {
        ServerRequestInterceptor serverRequestInterceptor = brave2.serverRequestInterceptor();
        ServerResponseInterceptor serverResponseInterceptor = brave2.serverResponseInterceptor();
        ClientRequestInterceptor clientRequestInterceptor = brave2.clientRequestInterceptor();
        ClientResponseInterceptor clientResponseInterceptor = brave2.clientResponseInterceptor();

        serverRequestInterceptor.handle(new ServerRequestAdapterImpl(spanName, spanId, ImmutableMap.of("key", "value")));

        clientRequestInterceptor.handle(new ClientRequestAdapterImpl("深入服务"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientResponseInterceptor.handle(new ClientResponseAdapterImpl());
        serverResponseInterceptor.handle(new ServerResponseAdapterImpl());
    }
}
