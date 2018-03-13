package za;

/**
 * Created by baozi on 13/03/2018.
 */
public class Test {

    public static char firstMaxNumChar(char[] pStr) {

        int hash[] = new int[256];

        for (int i = 0; i < pStr.length; i++) {
            hash[pStr[i]]++;
        }

        int n = hash[pStr[0]];
        char res = pStr[0];
        for (int i = 1; i < pStr.length; i++) {
            if (hash[pStr[i]] > n) {
                n = hash[pStr[i]];
                res = pStr[i];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        char[] str = "aa33##hh##ssssssss".toCharArray();
        System.out.println(firstMaxNumChar(str));
    }
}
