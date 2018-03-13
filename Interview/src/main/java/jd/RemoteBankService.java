package jd;

/**
 * Created by baozi on 2018/2/7.
 */
public class RemoteBankService {

    boolean flag = false;

    boolean checkCredit(int uid) {

        try {
            System.out.println("银行信用验证中。。。");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("银行信用验证终止");
            return false;
        }

        if (Thread.interrupted()) {
            Thread.yield();
            System.out.println("银行信用验证终止");
            return false;
        }

        if (flag) {
            System.out.println("恭喜，银行信用验证成功");
            return true;
        } else {
            System.out.println("抱歉，银行信用验证失败");
            return false;
        }
    }
}
