package objects;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * Created by baozi on 2018/3/5.
 */
public class CompareTester {

    public static void main(String[] args) {

        Person p1 = new Person("shi", "baozi", 4001);
        Person p2 = new Person("shi", "hongwei", 4002);

        System.out.println(p1.compareTo(p2));
    }
}

class Person  {

    private String lastName;
    private String firstName;
    private int zipCode;

    public Person(String lastName, String firstName, int zipCode) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.zipCode = zipCode;
    }

//    public int compareTo(Person other) {
//        int cmp = lastName.compareTo(other.lastName);
//        if (cmp != 0) {
//            return cmp;
//        }
//        cmp = firstName.compareTo(other.firstName);
//        if (cmp != 0) {
//            return cmp;
//        }
//        return Integer.compare(zipCode, other.zipCode);
//    }

    public int compareTo(Person that) {
        return ComparisonChain.start()
                .compare(this.lastName, that.lastName)
                .compare(this.firstName, that.firstName)
                .compare(this.zipCode, that.zipCode)
                .result();
    }
}

