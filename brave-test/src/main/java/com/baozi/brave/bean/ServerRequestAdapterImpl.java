package com.baozi.brave.bean;

import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerRequestAdapter;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.TraceData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * Created by baozi on 2018/2/26.
 */
public class ServerRequestAdapterImpl implements ServerRequestAdapter {

    Random randomGenerator = new Random();
    SpanId spanId;
    String spanName;
    Map<String, String> map;

    public ServerRequestAdapterImpl(String spanName, Map map) {
        this.spanName = spanName;
        long startId = randomGenerator.nextLong();
        SpanId spanId = SpanId.builder().spanId(startId).traceId(startId).parentId(startId).build();
        this.spanId = spanId;
        this.map = map;
    }

    public ServerRequestAdapterImpl(String spanName, SpanId spanId, Map map) {
        this.spanName = spanName;
        this.spanId = spanId;
        this.map = map;
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
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> e : map.entrySet()) {
                KeyValueAnnotation kv = KeyValueAnnotation.create(e.getKey(), e.getValue());
                collection.add(kv);
            }
        }
        return collection;
    }
}
