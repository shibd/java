package com.baozi.brave.test;

import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by baozi on 2018/2/26.
 */
public  class ClientResponseAdapterImpl implements ClientResponseAdapter {

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
            KeyValueAnnotation kv = KeyValueAnnotation.create("radioname", "火星人1");
            collection.add(kv);
        return collection;
    }

}
