package com.baozi.brave.test2;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.HttpSpanCollector;
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

            serverRequestInterceptor.handle(new ServerRequestAdapterImpl("group_data", spanId));


            ClientRequestAdapterImpl clientRequestAdapterImpl = new ClientRequestAdapterImpl("get_radio_list");
            clientRequestInterceptor.handle(clientRequestAdapterImpl);
            queue.offer(new Task("get_radio_list", clientRequestAdapterImpl.getSpanId()));
            brave2.localTracer().startNewSpan("", "pks错误");
//            brave2.localTracer().submitAnnotation("ss");
            Thread.sleep(1000);
            brave2.localTracer().submitAnnotation("error");
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

        serverRequestInterceptor.handle(new ServerRequestAdapterImpl(spanName, spanId));

//        clientRequestInterceptor.handle(new ClientRequestAdapterImpl("深入服务"));
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        clientResponseInterceptor.handle(new ClientResponseAdapterImpl());


//        serverResponseInterceptor.handle(new ServerResponseAdapterImpl());
    }


    static class ServerRequestAdapterImpl implements ServerRequestAdapter {

        Random randomGenerator = new Random();
        SpanId spanId;
        String spanName;

        ServerRequestAdapterImpl(String spanName){
            this.spanName = spanName;
            long startId = randomGenerator.nextLong();
            SpanId spanId = SpanId.builder().spanId(startId).traceId(startId).parentId(startId).build();
            this.spanId = spanId;
        }

        ServerRequestAdapterImpl(String spanName, SpanId spanId){
            this.spanName = spanName;
            this.spanId = spanId;
        }

        @Override
        public TraceData getTraceData() {
            if (this.spanId != null) {
                return TraceData.builder().spanId(this.spanId).build();
            }
            long startId = randomGenerator.nextLong();
            SpanId spanId = SpanId.builder().spanId(startId).traceId(startId).parentId(startId).build();
            return TraceData.builder().spanId(spanId).build();
        }

        @Override
        public String getSpanName() {
            return spanName;
        }

        @Override
        public Collection<KeyValueAnnotation> requestAnnotations() {
            Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
            KeyValueAnnotation kv = KeyValueAnnotation.create("sr", "error");
            collection.add(kv);
            return collection;
        }

    }

    static class ServerResponseAdapterImpl implements ServerResponseAdapter {

        @Override
        public Collection<KeyValueAnnotation> responseAnnotations() {
            Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
            KeyValueAnnotation kv = KeyValueAnnotation.create("sr", "error");
            collection.add(kv);
            return collection;
        }

    }

    static class ClientRequestAdapterImpl implements ClientRequestAdapter {

        String spanName;
        SpanId spanId;

        ClientRequestAdapterImpl(String spanName){
            this.spanName = spanName;
        }

        public SpanId getSpanId() {
            return spanId;
        }

        @Override
        public String getSpanName() {
            return this.spanName;
        }

        @Override
        public void addSpanIdToRequest(SpanId spanId) {
            //记录传输到远程服务
            System.out.println(spanId);
            if (spanId != null) {
                this.spanId = spanId;
                System.out.println(String.format("trace_id=%s, parent_id=%s, span_id=%s", spanId.traceId, spanId.parentId, spanId.spanId));
            }
        }

        @Override
        public Collection<KeyValueAnnotation> requestAnnotations() {
            Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
            KeyValueAnnotation kv = KeyValueAnnotation.create("client-request", "1");
            collection.add(kv);
            return collection;
        }

        @Override
        public Endpoint serverAddress() {
            return null;
        }

    }

    static class ClientResponseAdapterImpl implements ClientResponseAdapter {

        @Override
        public Collection<KeyValueAnnotation> responseAnnotations() {
            Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
            KeyValueAnnotation kv = KeyValueAnnotation.create("client-response", "1");
            collection.add(kv);
            return collection;
        }

    }
}
