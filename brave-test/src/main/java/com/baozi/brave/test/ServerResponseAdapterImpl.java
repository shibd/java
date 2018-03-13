package com.baozi.brave.test;

import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerResponseAdapter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by baozi on 2018/2/26.
 */
public class ServerResponseAdapterImpl implements ServerResponseAdapter {

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        Collection<KeyValueAnnotation> collection = new ArrayList<KeyValueAnnotation>();
            KeyValueAnnotation kv = KeyValueAnnotation.create("radioid", "165646485468486364");
            collection.add(kv);
        return collection;
    }

}
