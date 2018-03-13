package avoidingnull;


import java.util.Optional;

/**
 * Created by baozi on 2018/3/4.
 */
public class TestAvoidingNull {

    public static void main(String[] args) {
        testAvoiding(null);
        testAvoiding("google");

        PrdCashAccount prdCashAccount = null;
        System.out.println(Optional.ofNullable(prdCashAccount.getBlanceAmt()).orElse(0.0));
    }

    public static void testAvoiding(String str) {
        str = Optional.ofNullable(str).orElse("baidu");
        System.out.println("欢迎来到" + str);
    }
}

class PrdCashAccount {
    private double blanceAmt;

    public double getBlanceAmt() {
        return blanceAmt;
    }

    public void setBlanceAmt(double blanceAmt) {
        this.blanceAmt = blanceAmt;
    }
}
