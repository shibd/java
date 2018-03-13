package za;

/**
 * Created by baozi on 2018/2/5.
 */
public class Question1_1 {

    public static void main(String[] args) {
        Integer a = new Integer(10000);
        Integer b = a;
        b = new Integer(111111);

        System.out.println("a:" + a);
        System.out.println("b:" + b);
    }
}
