package bitemaps;

import java.util.Arrays;

/**
 * @Auther: baozi
 * @Date: 2019/3/15 15:30
 * @Description:
 */
public class BitMap { // Java 中 char 类型占 16bit，也即是 2 个字节
    public char[] bytes;
    public int nbits;

    public BitMap(int nbits) {
        this.nbits = nbits;
        this.bytes = new char[nbits/16+1];
    }

    public void set(int k) {
        if (k > nbits) return;
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        bytes[byteIndex] |= (1 << bitIndex);
    }

    public boolean get(int k) {
        if (k > nbits) return false;
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }

    public static void main(String[] args) {
        BitMap bitMap = new BitMap(15);
        System.out.println(bitMap.bytes.length);

        bitMap.set(15);
        System.out.print(Arrays.toString(bitMap.bytes));
    }
}

