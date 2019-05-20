package alibaba;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 19:35
 * @Description:
 * 两个整数求交集
 */
public class SortArrayIntersection2 {


    public static List<Integer> intersection(int[] array1, int[] array2) {

        List<Integer> results = new ArrayList<Integer>();

        int length1 = array1.length;
        int length2 = array2.length;
        int i = 0;
        int j = 0;
        while (i < length1 && j < length2) {

            if (array1[i] == array2[j]) {
                results.add(array1[i]);
                i++;
                j++;
            } else if (array1[i] < array2[j]) {
                i++;
            } else {
                j++;
            }
        }
        return results;
    }


    public static void main(String[] args) {

        int[] a = {1, 2, 3, 4, 5, 6, 200};
        int[] b = {3, 6, 9, 200};

        System.out.println(intersection(a, b));
    }
}
