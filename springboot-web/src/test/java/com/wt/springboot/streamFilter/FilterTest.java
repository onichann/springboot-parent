package com.wt.springboot.streamFilter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FilterTest {

    @Test
    public void filterTest(){
        List<Person> persons = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();//用来临时存储person的id
        persons.add(new Person(1, "name1", 10));
        persons.add(new Person(2, "name2", 21));
        persons.add(new Person(5, "name5", 55));
        persons.add(new Person(3, "name3", 34));
        persons.add(new Person(1, "name1", 10));

        List<Person> personList = persons.stream().filter(// 过滤去重
                v -> {
                    boolean flag = !ids.contains(v.getId());
                    if(flag) ids.add(v.getId());
                    return flag;
                }
        ).sorted(Comparator.comparingInt(Person::getId)).collect(Collectors.toList());
        System.out.println(personList);
        System.out.println(ids);
        List<Person> unique = persons.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getId))),
                        ArrayList::new));
        System.out.println(unique);
        List<Person> collect = new ArrayList<>(persons.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getId)))));
        System.out.println(collect);
    }
}
