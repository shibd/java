package bitemaps;

/**
 * @Auther: baozi
 * @Date: 2019/3/15 15:26
 * @Description:
 */
public class TestByte {


    public static void main(String[] args) {

        /**
         * byte 类型是1个字节（8bits）并不是bites位
         */
        byte[] tbytes = new byte[10];
        tbytes[0] = 5;

        for (byte tbyte : tbytes) {
            System.out.printf(String.valueOf(tbyte));
        }

    }
}
