package com.baozi.brave.bean;

import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by baozi on 2018/2/26.
 */
public class ClientResponseAdapterImpl implements ClientResponseAdapter {

    private Map<String, String> map;

    public ClientResponseAdapterImpl(Map map) {
        this.map = map;
    }

    public ClientResponseAdapterImpl() {
    }

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
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
