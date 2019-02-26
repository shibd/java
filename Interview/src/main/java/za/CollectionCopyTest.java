package za;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionCopyTest {

    public static void main(String[] args) {

        List<Oba> list1 = new ArrayList<Oba>();
        list1.add(new Oba("wudi"));
        list1.add(new Oba("xiao"));
        list1.add(new Oba("baozi"));

        List<Oba> list2 = new ArrayList<Oba>(list1);
        list1.clear();

        System.out.println(list2);


    }
}

class Oba {

    String name;

    public Oba(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
