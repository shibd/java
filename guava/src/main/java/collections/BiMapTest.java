package collections;

import com.google.common.collect.HashBiMap;

/**
 * Created by baozi on 2018/3/6.
 */
public class BiMapTest {

    public static void main(String[] args) {

        HashBiMap<String, Integer> userId = HashBiMap.create();
        userId.put("Bob", 42);

//        String s = userId.inverse().get(42);
//        System.out.println(s);

        userId.put("Bob", 42);

    }
}
