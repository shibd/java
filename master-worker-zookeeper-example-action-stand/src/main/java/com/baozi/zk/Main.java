package com.baozi.zk;

import java.util.*;

/**
 * Created by baozi on 29/03/2018.
 */
public class Main {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
//        List<String> list = Arrays.asList("a", "b", "c", "d");

        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).equals("b")) {
                list.remove(i);
            }
        }

        Map<String, Date> dataMap = Collections.synchronizedMap(new HashMap<String, Date>());
        dataMap.put("date", new Date());

        for (String s : list) {

        }

        System.out.println(list);
    }
}
