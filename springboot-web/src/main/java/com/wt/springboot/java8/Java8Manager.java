package com.wt.springboot.java8;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wt.springboot.java8.pojo.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8Manager {
    public static List<Integer> test(List<Transaction> transactions){
        return transactions.parallelStream()
                .filter(v -> "obj".equals(v.getType()))
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Transaction> transactionList=Arrays.asList(new Transaction().setId(1).setType("obj").setValue("value1"),
                new Transaction().setId(3).setType("obj").setValue("sss"),
                new Transaction().setType("obj2").setId(2).setValue("ee"));
        System.out.println(JSON.toJSONString(test(transactionList), SerializerFeature.WriteMapNullValue));

        int sum=transactionList.parallelStream().mapToInt(Transaction::getId).sum();
        System.out.println(sum);
    }

    //构造流的几种常见方法
    @Test
    public void test(){
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String [] strArray = new String[] {"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        stream=list.parallelStream();
    }

    //数值流的构造
    @Test
    public void test2(){
        IntStream.of(1,2,3).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    //流转换为其它数据结构
    public void test3(Stream<String> stream,Stream<Set> setStream,Stream<Stack> stackStream){
        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = setStream.collect(Collectors.toSet());
        Stack stack1 = stackStream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining());
    }

    @Test
    public void test4(){
        String o=Stream.of("a", "b", "c").collect(Collectors.joining(","));
        System.out.println(o);
    }

    //单词转换为大写
    @Test
    public void test5(){
        List<String> list=Arrays.stream(new String[]{"a","1","b"}).map(String::toUpperCase).collect(Collectors.toList());
        list.forEach(System.out::println);
    }

    //平方数
    @Test
    public void test6(){
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().
                map(n -> n * n).
                collect(Collectors.toList());
        Consumer<? super Integer> consumer = (Consumer<Integer>) integer -> {
            integer=integer*integer;
            System.out.println(integer);
        };
        squareNums.forEach(consumer);
    }

    // 一对多
    @Test
    public void test7(){
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        outputStream.forEach(System.out::println);
    }

    //留下偶数
    @Test
    public void test8(){
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens =Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        Stream.of(evens).forEach(System.out::println);
    }

    //把单词挑出来
    public void test9(BufferedReader reader){
        List<String> output = reader.lines().
                flatMap(line -> Stream.of(line.split("#"))).
                filter(word -> word.length() > 0).
                collect(Collectors.toList());
    }

    //peek 常用于debugger
    @Test
    public void test10(){
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
    }

    // Optional 的两个用例
    @Test
    public void test11(){
        String text="abc", text2=null;
        Optional.ofNullable(text).ifPresent(System.out::println);
        Optional.ofNullable(text2).ifPresent(System.out::println);
        Integer i=Optional.ofNullable(text).map(String::length).orElse(-1);
        System.out.println(i);
        System.out.println(Optional.ofNullable(text2).isPresent());
    }

    //reduce 的用例
    @Test
    public void test12(){
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);

        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);

        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).orElse(0);
        System.out.println(sumValue);

        sumValue=Arrays.asList(1,2,3,4,5).parallelStream().reduce(5, (integer, integer2) -> integer + integer2, (integer, integer2) -> integer+integer2-5);
        System.out.println(sumValue);

        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
    }

    @Test
    public void test13(){
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
//        persons.stream().map(person -> person.getName()).limit(10).skip(3).collect(Collectors.toList());
        List<String> list=persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(list);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class Person{
        private int id;
        private String name;

        public String getName() {
            System.out.println(name);
            return name;
        }
    }

    //先截取后排序
    @Test
    public void test14(){
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().sorted(Comparator.comparing(Person::getName)).limit(2).collect(Collectors.toList());
        System.out.println(personList2);
        personList2.forEach(Person::getName);
        System.out.println("------------------------");
        List<Person> collect = persons.stream().limit(4).sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());
        System.out.println(collect);
    }

    //找出最长一行的长度
    @Test
    public void test15() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
        int longest = br.lines().
                mapToInt(String::length).
                max().
                orElse(0);
        br.close();
        System.out.println(longest);

        //找出全文的单词，转小写，并排序
        List<String> words = br.lines().
                flatMap(line -> Stream.of(line.split(" "))).
                filter(word -> word.length() > 0).
                map(String::toLowerCase).
                distinct().
                sorted().
                collect(Collectors.toList());
        br.close();
        System.out.println(words);
    }

    //使用 Match
    @Test
    public void test16(){
        boolean f=Arrays.stream(new int[]{1,2,12,31,21,31,45,77}).allMatch(i->i>10);
        boolean f1=Arrays.stream(new int[]{1,2,12,31,21,31,45,77}).anyMatch(i->i>10);
        boolean f2=Arrays.stream(new int[]{1,2,12,31,21,31,45,77}).noneMatch(i->i>10);
        System.out.println(f);
        System.out.println(f1);
        System.out.println(f2);
    }

//    通过实现 Supplier 接口，你可以自己来控制流的生成。这种情形通常用于随机数、常量的 Stream，
//    或者需要前后元素间维持着某种状态信息的 Stream。把 Supplier 实例传递给 Stream.generate()
//    生成的 Stream，默认是串行（相对 parallel 而言）但无序的（相对 ordered 而言）。
//    由于它是无限的，在管道中，必须利用 limit 之类的操作限制 Stream 大小

    //自己生成流
//    生成 10 个随机整数
    @Test
    public void test17(){
        Stream.generate(new Random()::nextInt).limit(10).forEach(System.out::println);
        System.out.println("----------");
        IntStream.generate(()-> (int) (System.nanoTime()%100)).limit(10).forEach(System.out::println);
    }

    // 自实现 Supplier
    @Test
    public void test18(){
        Stream.generate(new Supplier<Person>() {
            private int index=0;
            @Override
            public Person get() {
                return new Person(index++,"name"+index);
            }
        }).limit(10).
                forEach(p -> System.out.println(p.getName() + ", " + p.getId()));
    }

    /**
     * Stream.iterate
     iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
     然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
     与 Stream.generate 相仿，在 iterate 时候管道必须有 limit 这样的操作来限制 Stream 大小。
     */
    @Test
    public void test19(){
        Stream.iterate(0, n -> n + 3).limit(10). forEach(x -> System.out.print(x + " "));
    }

    //按照年龄归组
    @Test
    public void test20(){
        Map<Integer, List<Person>> personGroups =
                Stream.generate(() -> new Person(new Random().nextInt(100), "name"))
                        .limit(100).collect(Collectors.groupingBy(Person::getId));
        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }

    }

    //按照未成年人和成年人归组
    @Test
    public void test21(){
        Map<Boolean, List<Person>> children = Stream.generate(() -> new Person(new Random().nextInt(100), "name"))
                .limit(100).collect(Collectors.partitioningBy(p -> p.getId() < 18));
        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());
    }

    //将Stream对象放入Supplier中后，后面可以多次取用
    Supplier supplier= ()-> Stream.of("a","b").filter(s->s.startsWith("a"));
    Supplier<Stream<String>> streamSupplier=new Supplier<Stream<String>>() {
        @Override
        public Stream<String> get() {

            return Stream.of("a","b").filter(s->s.startsWith("a"));
        }
    };
}