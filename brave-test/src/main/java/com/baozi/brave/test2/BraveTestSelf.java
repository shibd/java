package com.baozi.brave.test2;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.twitter.zipkin.gen.Endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static com.github.kristofa.brave.IdConversion.convertToLong;

public class BraveTestSelf {

    private static HttpSpanCollector collector = null;
    private static Brave brave = null;
    private static Brave brave2 = null;

    private static void braveInit(){
        collector = HttpSpanCollector.create("http://localhost:9411/", new EmptySpanCollectorMetricsHandler());

        brave = new Brave.Builder("tomcat").spanCollector(collector).build();
        brave2 = new Brave.Builder("dubbo").spanCollector(collector).build();
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

//        final BlockingQueue<Task> queue = new ArrayBlockingQueue<Task>(10);
//        Thread thread = new Thread(){
//            public void run() {
//                while (true) {
//                    try {
//                        Task task = queue.take();
//                        dcHandle(task.name, task.spanId);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        thread.start();

        {
            ServerRequestInterceptor serverRequestInterceptor = brave.serverRequestInterceptor();
            ServerResponseInterceptor serverResponseInterceptor = brave.serverResponseInterceptor();
            ClientRequestInterceptor clientRequestInterceptor = brave.clientRequestInterceptor();
            ClientResponseInterceptor clientResponseInterceptor = brave.clientResponseInterceptor();

            ServerRequestInterceptor serverRequestInterceptor2 = brave2.serverRequestInterceptor();
            ServerResponseInterceptor serverResponseInterceptor2 = brave2.serverResponseInterceptor();
            ClientRequestInterceptor clientRequestInterceptor2 = brave2.clientRequestInterceptor();
            ClientResponseInterceptor clientResponseInterceptor2 = brave2.clientResponseInterceptor();


            ServerRequestAdapterImpl s1 = new ServerRequestAdapterImpl("OMS");
            s1.setTraceId("777");
            s1.setSpanIdd("1");
            //开始

            serverRequestInterceptor.handle(s1);

            //1. OMS
            clientRequestInterceptor.handle(new ClientRequestAdapterImpl("oms-指令创建"));

            {
                Thread.sleep(100);
                ServerRequestAdapterImpl s2 = new ServerRequestAdapterImpl("PKS");
                s2.setTraceId("777");
                s2.setSpanIdd("2");
                s2.setParentId("1");
                serverRequestInterceptor2.handle(s2);

//                clientRequestInterceptor2.handle(new ClientRequestAdapterImpl("测试客户端"));
//                clientResponseInterceptor2.handle(new ClientResponseAdapterImpl());

                Thread.sleep(4111);
                serverResponseInterceptor2.handle(new ServerResponseAdapterImpl());
            }

            //业务逻辑
            clientResponseInterceptor.handle(new ClientResponseAdapterImpl());

//            //2. T2
//            clientRequestInterceptor.handle(new ClientRequestAdapterImpl("t2-指令创建"));
//            Thread.sleep(1011);
//            //业务逻辑
//            clientResponseInterceptor.handle(new ClientResponseAdapterImpl());
//
//            //3. PKS
//            clientRequestInterceptor.handle(new ClientRequestAdapterImpl("pks-指令创建"));
//            Thread.sleep(10);
//            //业务逻辑
//            clientResponseInterceptor.handle(new ClientResponseAdapterImpl());

            //结束
            serverResponseInterceptor.handle(new ServerResponseAdapterImpl());
        }
        Thread.sleep(3000);
    }

    public static void dcHandle(String spanName, SpanId spanId){
//        ServerRequestInterceptor serverRequestInterceptor = brave2.serverRequestInterceptor();
//        ServerResponseInterceptor serverResponseInterceptor = brave2.serverResponseInterceptor();
//        serverRequestInterceptor.handle(new ServerRequestAdapterImpl(spanName, spanId));
//        serverResponseInterceptor.handle(new ServerResponseAdapterImpl());
    }


    static class ServerRequestAdapterImpl implements ServerRequestAdapter {

        Random randomGenerator = new Random();
        String spanName;
        String traceId;
        String spanIdd;
        String parentId;


        ServerRequestAdapterImpl(String spanName){
            this.spanName = spanName;
            long startId = randomGenerator.nextLong();
            SpanId spanId = SpanId.builder().spanId(startId).traceId(startId).parentId(startId).build();
        }

        ServerRequestAdapterImpl(String spanName, SpanId spanId){
            this.spanName = spanName;
        }

        public String getTraceId() {
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }

        public String getSpanIdd() {
            return spanIdd;
        }

        public void setSpanIdd(String spanIdd) {
            this.spanIdd = spanIdd;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public TraceData getTraceData() {
            if (traceId != null && spanIdd != null) {
                SpanId span = getSpanId2(traceId, spanIdd, parentId);
                return TraceData.builder().sample(true).spanId(span).build();
            }
            long startId = randomGenerator.nextLong();
            SpanId spanId = SpanId.builder().spanId(startId).traceId(startId).parentId(startId).build();
            return TraceData.builder().build();
        }

        static SpanId getSpanId2(String traceId, String spanId, String parentSpanId) {
            return SpanId.builder()
                    .traceId(convertToLong(traceId))
                    .spanId(convertToLong(spanId))
                    .parentId(parentSpanId == null ? null : convertToLong(parentSpanId)).build();
        }

        @Override
        public String getSpanName() {
            return spanName;
        }

        @Override
        public Collection<KeyValueAnnotation> requestAnnotations() {
            Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
//            KeyValueAnnotation kv = KeyValueAnnotation.create("radioid", "165646485468486364");
//            collection.add(kv);
            return collection;
        }

    }

    static class ServerResponseAdapterImpl implements ServerResponseAdapter {

        @Override
        public Collection<KeyValueAnnotation> responseAnnotations() {
            Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
//            KeyValueAnnotation kv = KeyValueAnnotation.create("radioid", "165646485468486364");
//            collection.add(kv);
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
//            KeyValueAnnotation kv = KeyValueAnnotation.create("radioid", "165646485468486364");
//            collection.add(kv);
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
//            KeyValueAnnotation kv = KeyValueAnnotation.create("radioname", "火星人1");
//            collection.add(kv);
            return collection;
        }

    }

}
