package collections.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import java.util.List;

/**
 * Created by baozi on 2018/3/6.
 */
public class IterablesTest {
    public static void main(String[] args) {

        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6)
        );

        Integer last = Iterables.getLast(concatenated);
        System.out.println(last);

    }
}
