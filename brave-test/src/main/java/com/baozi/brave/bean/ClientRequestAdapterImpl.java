package com.baozi.brave.bean;

import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.twitter.zipkin.gen.Endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by baozi on 2018/2/26.
 */
public class ClientRequestAdapterImpl implements ClientRequestAdapter {

    String spanName;
    SpanId spanId;
    Map<String, String> map;

    public ClientRequestAdapterImpl(String spanName) {
        this.spanName = spanName;
    }

    public ClientRequestAdapterImpl(String spanName, Map map) {
        this.spanName = spanName;
        this.map = map;
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
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> e : map.entrySet()) {
                KeyValueAnnotation kv = KeyValueAnnotation.create(e.getKey(), e.getValue());
                collection.add(kv);
            }
        }
        return collection;
    }

    @Override
    public Endpoint serverAddress() {
        return null;
    }

}
