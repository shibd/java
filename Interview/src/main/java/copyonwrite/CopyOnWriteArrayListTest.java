package copyonwrite;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {

        final List<String> list = new CopyOnWriteArrayList<String>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    list.add(i + "");
                }
            }
        });

//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                list.clear();
//            }
//        });

        t1.start();

//        t2.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(list.toString());
    }
}

