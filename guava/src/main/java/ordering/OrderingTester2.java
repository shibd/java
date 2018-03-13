package ordering;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import java.util.*;

/**
 * Created by baozi on 2018/3/5.
 */
public class OrderingTester2 {
    public static void main(String[] args) {
//        Ordering<Person> ordering = Ordering.from((Comparator<Person>)
//                (o1, o2) -> o1.getAge() - o2.getAge());


        Ordering<Person> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Person, Comparable>() {
            @Override
            public Comparable apply(Person input) {
                return input.getName();
            }
        });

        List<Person> list = Arrays.asList(new Person("baozi", 19), new Person("baodi", 24), null);
        Collections.sort(list, ordering.nullsFirst());
        System.out.println(list);
        System.out.println(ordering.nullsFirst().min(list));
        System.out.println(ordering.nullsFirst().max(list));
    }
}


class Person {

    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
