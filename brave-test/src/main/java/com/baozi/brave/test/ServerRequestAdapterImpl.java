package com.baozi.brave.test;

import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerRequestAdapter;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.TraceData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by baozi on 2018/2/26.
 */
public class ServerRequestAdapterImpl implements ServerRequestAdapter {

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
            KeyValueAnnotation kv = KeyValueAnnotation.create("radioid", "165646485468486364");
            collection.add(kv);
        return collection;
    }

}
