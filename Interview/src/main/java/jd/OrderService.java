package jd;

import java.util.concurrent.CountDownLatch;

/**
 * Created by baozi on 2018/2/7.
 */
public class OrderService {


    public boolean canOrder(final int uid) {

        final CountDownLatch counter = new CountDownLatch(3);
        final Boolean[] running = {Boolean.TRUE};
        final Thread[] t = new Thread[3];

        t[0] = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!new RemoteBankService().checkCredit(uid)) {
                    running[0] = false;
                    t[1].interrupt();
                    t[2].interrupt();
                    counter.countDown();
                    counter.countDown();
                }
                counter.countDown();
            }
        });
        t[1] = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!new RemoteLoanService().checkRisk(uid)) {
                    running[0] = false;
                    t[0].interrupt();
                    t[2].interrupt();
                    counter.countDown();
                    counter.countDown();
                }
                counter.countDown();
            }
        });
        t[2] = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!new RemotePassportService().checkAuth(uid)) {
                    running[0] = false;
                    t[0].interrupt();
                    t[1].interrupt();
                    counter.countDown();
                    counter.countDown();
                }
                counter.countDown();
            }
        });

        for (Thread tt : t) {
            tt.start();
        }

        try {
            counter.await();
            if (!running[0]) {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        boolean canOrder = new OrderService().canOrder(12345);
        if (canOrder) {
            System.out.println("恭喜，下单成功");
        } else {
            System.out.println("抱歉，下单失败");
        }
    }
}
