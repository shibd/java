package twogao;

/**
 * Created by baozi on 2018/2/23.
 * ﻿数组中可能有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1，2，3，2，2，2，5，4，2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2，如果没有，则输出0。
 */
public class Question5 {

    public static void swap(int[] a, int i, int j) {
        int tmp = a[j];
        a[j] = a[i];
        a[i] = tmp;
    }

    /**
     * 选择最左处数为flag,比他小的在左，比他大的在右
     *
     * @param a
     * @param left
     * @param right
     * @return 处理后flag的位置
     */
    public static int partition(int[] a, int left, int right) {
        int tail = left;
        int pivot = a[left];
        for (int i = left + 1; i <= right; i++) {
            if (pivot > a[i]) {
                swap(a, tail, i);
                tail++;
            }
        }
        return tail;
    }


    public static int moreThanHalfNum(int[] numbers) {

        int length = numbers.length;

        //1. 检查数组
        if (null == numbers || numbers.length == 0) {
            throw new RuntimeException("数组不合法");
        }

        //2. 当partition后返回等于中位坐标时，满足
        int middle = length >> 1;
        int start = 0;
        int end = length - 1;
        int index = partition(numbers, start, end);
        while (index != middle) {
            if (index > middle) {
                end = index - 1;
                index = partition(numbers, start, end);
            } else {
                start = index + 1;
                index = partition(numbers, start, end);
            }
        }

        int res = numbers[index];

        //3. 检查是否满足题目大于长度一般的条件
        int num = 0;
        for (int i = 0; i < length; i++) {
            if (numbers[i] == res) {
                num++;
            }
        }
        if (num < middle) {
            return 0;
        }
        return res;
    }


    public static void main(String[] args) {
//        int[] numbers = {1, 1, 1, 1, 2, 1, 3, 2, 1, 4, 6};
        int[] numbers = {1, 1, 2, 3, 6, 4, 2, 4, 4, 4, 4, 4};
        System.out.println(moreThanHalfNum(numbers));
    }
}
