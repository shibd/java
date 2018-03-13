package collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;

/**
 * Created by baozi on 2018/3/6.
 */
public class MultimapTest {

    public static void main(String[] args) {

        ListMultimap<String, Integer> hashListMultimap =
                MultimapBuilder.hashKeys().arrayListValues().build();
        hashListMultimap.put("baozi", 1);
        hashListMultimap.put("baozi", 2);

        List<Integer> baozi = hashListMultimap.get("baozi");
        baozi.clear();
        baozi.add(3);
        baozi.add(4);

        hashListMultimap.putAll("shibaodi", ImmutableList.of(1, 2, 3, 4, 5));
        hashListMultimap.remove("shibaodi", 1);
        System.out.println(hashListMultimap);

        hashListMultimap.replaceValues("baozi", ImmutableList.of(33, 49));
        System.out.println(hashListMultimap);

        hashListMultimap.put(null, 1);
        //views
        System.out.println(hashListMultimap.values());
        System.out.println(hashListMultimap.keys().count("baozi"));
        System.out.println(hashListMultimap.keySet());
        System.out.println(hashListMultimap.entries());
        System.out.println(hashListMultimap.asMap());
        System.out.println(hashListMultimap.containsKey("baozi"));



    }
}
