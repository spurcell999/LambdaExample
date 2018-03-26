import com.purcell.Book;
import com.purcell.Calculate;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Main {
    /**
     * • Predicate - takes one argument, use test method returns a Boolean
     * • Consumer - accepts single argument with no return value
     * • Function - accepts one argument and produces a result
     * • Supplier - represents a supplier of results
     * • UnaryOperator - single argument with a return value
     * • BinaryOperator - takes two arguments and returns one
     *
     * @param args
     */

    public static void main(String[] args) {

        Predicate<String> stringLen = (s) -> s.length() < 10;
        System.out.println(stringLen.test("Apples") + " - Applies is less than 10");

        Consumer<String> consumerStr = (s) -> System.out.println(s.toLowerCase());
        consumerStr.accept("ABCDEFGHIKKKJKJKFK;IOP");

        Function<Integer, String> converter = (num) -> Integer.toString(num);
        System.out.println("length of 26: " + converter.apply(26).length());

        Supplier<String> s = () -> "Java is fun";
        System.out.println(s.get());

        BinaryOperator<Integer> add = (a, b) -> a + b;
        System.out.println("add 10 + 25: " + add.apply(10, 25));

        UnaryOperator<String> str = (msg) -> msg.toUpperCase();
        System.out.println(str.apply("This is my message in upper case"));

        IntFunction<String> intToString = num -> Integer.toString(num);
        System.out.println("expected value 3, actual value: " + intToString.apply(123).length());

        IntFunction<String> intToString2 = Integer::toString;
        System.out.println("expected value 4, actual value: " + intToString2.apply(4567).length());

        Function<String, BigInteger> newBigInt = BigInteger::new;
        System.out.println("expected value: 123456789, actual value: " + newBigInt.apply("123456789"));

        Consumer<String> print = System.out::println;
        print.accept("coming to you directory from a lambda....");

        UnaryOperator<String> makeGreeting = "Hello, "::concat;
        System.out.println(makeGreeting.apply("Peggy"));

        // try using calculate interface
        Calculate cadd = (a, b) -> a + b;
        Calculate difference = (a, b) -> Math.abs(a - b);
        Calculate divide = (a, b) -> (b != 0 ? a / b : 0);


        System.out.println(cadd.calc(3, 2));
        System.out.println(difference.calc(5, 10));
        System.out.println(divide.calc(12, 3));

        /** collections examples */

        List<String> names = asList("Paul", "Jane", "Michelle", "Sam");

        // prior to java 8 lambdas sort
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        // first iteration with lambda
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });

        // now remove the data types and allow the compile to infer the type
        Collections.sort(names, (a, b) -> b.compareTo(a));

        Book book1 = new Book("Miss foobars home for peculiar children", "Ranson", "Riggs", 382);
        Book book2 = new Book("Harry Potter and The Sorcorers Pone", "JK", "Rowlinglib", 411);
        Book book3 = new Book("The Cat in the Hat", "Dr", "Seuss", 45);

        List<Book> books = asList(book1, book2, book3);
        int total = books.stream().collect(Collectors.summingInt(Book::getPages));
        System.out.println(total);

        // use .collect to aggregate author first names into a list
        // and a .mpa to get the last name of the author
        List<String> list = books.stream().map(Book::getAuthorLName).collect(toList());
        System.out.println(list);

        // create a list with duplicates
        List<Book> dupBooks = asList(book1, book2, book3, book1, book2);
        System.out.println("before removing duplicates: ");
        System.out.println(dupBooks.toString());

        // remove duplicates by using a set
        Collection<Book> noDups = new HashSet<>(dupBooks);
        System.out.println("after removing duplicates: ");
        System.out.println(noDups.toString());

        // example of using Set to eliminate dups and sort automatically
        Set<Integer> numbers = new HashSet<>(asList(4, 3, 3, 3, 2, 1, 1, 1));
        System.out.println(numbers.toString());

        // streams

        Arrays.asList("red", "green", "blue")
                .stream()
                .sorted()
                .findFirst()
                .ifPresent(System.out::println);

        Stream.of("apple", "pear", "banana", "cherry", "apricot")
                .filter(fruit -> {
                    return fruit.startsWith("a"); // predicate
                })

                .forEach(fruit -> System.out.println("starts with A: " + fruit));


        List<String> collected = Stream.of("java", " Rocks")
                .map(string -> string.toUpperCase())
                .collect(toList());
        System.out.println(collected.toString());

        /* primitives */
        IntStream.range(1, 4).forEach(System.out::println);

        // find the average of the numbers squared
        Arrays.stream(new int[]{1, 2, 3, 4})
                .map(n -> n * n)
                .average()
                .ifPresent(System.out::println);

        // map doubles to ints
        Stream.of(1.5, 2.3, 3.7)
                .mapToInt(Double::intValue)
                .forEach(System.out::println);
    }
}

