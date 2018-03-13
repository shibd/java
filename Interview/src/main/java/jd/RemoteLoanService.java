package jd;

/**
 * Created by baozi on 2018/2/7.
 */
public class RemoteLoanService {
    boolean flag = true;

    boolean checkRisk(int uid) {

        try {
            System.out.println("不良贷款验证中。。。");
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("不良贷款验证终止");
            return false;
        }

        if (Thread.interrupted()) {
            System.out.println("不良贷款验证终止");
            return false;
        }

        if (flag) {
            System.out.println("恭喜，不良贷款验证成功");
            return true;
        } else {
            System.out.println("抱歉，不良贷款验证失败");
            return false;
        }
    }
}
