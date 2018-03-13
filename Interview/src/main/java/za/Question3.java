package za;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baozi on 2018/2/5.
 */
public class Question3 {

    public static void main(String[] args) {
        List<String> a = new ArrayList<String>();
        a.add("baozi");
        a.add("wudi");
        a.add("shuai");
        a.add("baole");

        List<String> b = new ArrayList<String>();
        b.add("baozi");
        b.add("wudi");
        b.add("shuaiqi");
        b.add("kubile");

        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (a.get(i).equals(b.get(j))) {
                    a.remove(i);
                    b.remove(j);
                }
            }
        }
        System.out.println(a);
        System.out.println(b);
    }

    /**
     * 去除集合a集合b相同的元素
     * @param a
     * @param b
     * @param <T> Object判断相等使用equals即可
     */
    public <T> void removeEquals(List<T> a, List<T> b) {

    }
}
