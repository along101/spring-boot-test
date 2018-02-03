package com.yzl.test.java8.stream;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    /**
     * stream的生成
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        //从 Collection 和数组
        Collection<String> c = Arrays.asList("1", "2", "3");
        System.out.println("Collection.stream...........");
        c.stream().map(s -> s + "a").forEach(System.out::println);
        System.out.println("Collection.parallelStream...........");
        c.parallelStream().forEach(System.out::println);

        System.out.println("Arrays.stream...........");
        Arrays.stream(new int[]{1, 2, 3, 4, 5}).forEach(System.out::println);
        System.out.println("Stream.of...........");
        Stream.of(1, 2, 3, 4, 5, 6).forEach(System.out::println);

        //从 BufferedReader
        String s = "one\ntwo\n3\n4\n6";
        StringReader sReader = new StringReader(s);
        BufferedReader reader = new BufferedReader(sReader);
        System.out.println("BufferedReader.lines...........");
        reader.lines().forEach(System.out::println);

        //静态工厂
        System.out.println("IntStream.rang...........");
        IntStream.range(0, 10).forEach(System.out::println);
        System.out.println("Files.walk...........");
        Files.walk(Paths.get(".")).forEach(System.out::println);

        //其他
        System.out.println("Random.ints...........");
        new Random().ints(10, 0, 10).forEach(System.out::println);
        System.out.println("BitSet.stream...........");
        BitSet.valueOf("abc".getBytes()).stream().forEach(System.out::println);
        System.out.println("Pattern.splitAsStream...........");
        Pattern.compile(",").splitAsStream("a,b,c").forEach(System.out::println);
        //JarFile.stream()
        //new JarFile("").stream().forEach(jarEntry -> {   });
    }

    /**
     * stream自己生成
     */
    @Test
    public void test2() {
        //Stream.generate
        System.out.println("Stream.generate..........");
        Random seed = new Random();
        Stream.generate(seed::nextInt).limit(10).forEach(System.out::println);
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).limit(10).forEach(System.out::println);

        //Stream.iterate
        System.out.println("Stream.iterate..........");
        Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));
    }

    /**
     * map/flatMap
     */
    @Test
    public void test3() {
        List<String> list1 = IntStream.range(0, 10).mapToObj(i -> i + "toMap").collect(Collectors.toList());
        System.out.println(list1);

        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1, 1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );

        //一对多
        List<String> list2 = inputStream.flatMap(Collection::stream).map(i -> i + "toMap").collect(Collectors.toList());
        System.out.println(list2);
    }

    /**
     * filter
     */
    @Test
    public void test4() {
        //留下偶数
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
        System.out.println(Arrays.asList(evens));
    }


    /**
     * limit/skip
     */
    @Test
    public void test5() {
        IntStream.range(0, 100).skip(10).limit(5).forEach(System.out::println);
    }

    /**
     * sorted
     */
    @Test
    public void test6() {
        new Random(10).ints(10, 0, 100).sorted().forEach(System.out::println);
    }

    /**
     * min/max/distinct
     */
    @Test
    public void test7() {
        System.out.println(new Random(10).ints(10, 0, 5).max());
        System.out.println(new Random(10).ints(10, 0, 5).min());

        List<String> list = new Random(10).ints(10, 0, 5)
                .distinct().mapToObj(x -> x + "").collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * Match
     */
    @Test
    public void test8() {
        boolean b1 = new Random(10).ints(10, 0, 5).boxed().allMatch(x -> x > 3);
        System.out.println("b1: " + b1);

        boolean b2 = new Random(10).ints(10, 0, 5).boxed().anyMatch(x -> x > 3);
        System.out.println("b2: " + b2);


        boolean b3 = new Random(10).ints(10, 0, 5).boxed().noneMatch(x -> x > 3);
        System.out.println("b3: " + b3);
    }

    /**
     * Collectors.toList
     */
    @Test
    public void test9() {
        List<Integer> list = new Random(10).ints(10, 0, 5).boxed().collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * reduce
     */
    @Test
    public void test10() {
        Random r = new Random();
        List<Person> peoples = IntStream.range(0, 10).boxed().map(i -> new Person("name" + i, r.nextInt(5), r.nextInt(100))).collect(Collectors.toList());
        //求value和
        Person sum = peoples.stream().reduce(new Person("", 0, 0), (p1, p2) -> {
            p1.setValue(p1.getValue() + p2.getValue());
            return p1;
        });
        System.out.println(sum.getValue());
    }

    /**
     * groupingBy
     */
    @Test
    public void test11() {
        Random r = new Random(100);
        List<Person> peoples = IntStream.range(0, 10).boxed().map(i -> new Person("name" + i, r.nextInt(5), r.nextInt(100))).collect(Collectors.toList());
        //按照age分组
        Map<Integer, List<Person>> map1 = peoples.stream().collect(Collectors.groupingBy(p -> p.age));
        System.out.println(map1);

        //按照age分组，求个数
        Map<Integer, Long> map2 = peoples.stream().collect(Collectors.groupingBy(p -> p.age, Collectors.counting()));
        System.out.println(map2);
        //按照age分组，求最大value
        Map<Integer, Optional<Person>> map3 = peoples.stream().collect(Collectors.groupingBy(p -> p.age, Collectors.maxBy(Comparator.comparingInt(Person::getValue))));
        System.out.println(map3);

        //分组求和
        Map<Integer, Person> groupSum = peoples.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.reducing(null, (p1, p2) -> {
            if (p1 == null) {
                p1 = new Person("", 0, 0);
            }
            p1.setValue(p1.getValue() + p2.getValue());
            p1.setAge(p2.getAge());
            return p1;
        })));
        System.out.println(groupSum);

    }


    static class Person {
        String name;
        int age;
        int value;

        public Person(String name, int age, int value) {
            this.name = name;
            this.age = age;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", value=" + value +
                    '}';
        }
    }
}
