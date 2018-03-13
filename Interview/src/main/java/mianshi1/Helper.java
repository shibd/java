package mianshi1;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by baozi on 13/03/2018.
 */
public class Helper {

    private static final ExecutorService pool = Executors.newFixedThreadPool(4);

    public static String[] buildNumArray(int max) {
        String[] numStr = new String[max];
        for (int i = 0; i < max; i++) {
            numStr[i] = Integer.toString(i + 1);
        }
        return numStr;
    }

    public static String[] buildCharArray(int max) {
        String[] charStr = new String[max];
        for (int i = 0; i < max; i++) {
            charStr[i] = String.valueOf((char) ('a' + i));
        }
        return charStr;
    }

    public static void run(Runnable runnable) {
        pool.submit(runnable);
    }

    public static void shutDown() {
        pool.shutdown();
    }

    public static void printStr(String... str) {
        if (str == null) {
            return ;
        }
        for (String string : str) {
            System.out.print(string);
        }
    }
}
