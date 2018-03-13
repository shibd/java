package com.baozi.brave.test;

import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.twitter.zipkin.gen.Endpoint;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by baozi on 2018/2/26.
 */
public class ClientRequestAdapterImpl implements ClientRequestAdapter {

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
