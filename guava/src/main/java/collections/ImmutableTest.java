package collections;

import com.google.common.collect.ImmutableMap;

/**
 * Created by baozi on 2018/3/6.
 */
public class ImmutableTest {


    public static void main(String[] args) {
        ImmutableMap<String, String> immMap = ImmutableMap.of("baozi", "shuai");
        System.out.println(immMap.asMultimap());
    }
}
