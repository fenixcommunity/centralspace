package java8.stream;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Other {
    public static void main(String[] args) throws Exception {
        basicFunction();
        function();
        doubleFunction(d -> d + " now string");
        unaryOperator(i -> Integer.valueOf(i.toString() + "5"));
        biFunction((i1, i2) -> Integer.valueOf(i1 + i2));
        booleanSupplier(() -> "as".contains("a"));
        //interface BiConsumer<T, U> ; void accept(T t, U u)
        consumer((x) -> out.println(x.toLowerCase()));
        doubleConsumer();
        binaryOperator((d1, d2) -> d1 + d2);
        //interface BiPredicate<T, U> ; boolean test(T t, U u)
        predicate();
        supplier(() -> 8.1 * 2);
        mapExample();
        arrayExample();
        optionalExample();
        otherMethods();
        objectMethods();
        fileMethods();
    }

    static void fileMethods() throws Exception {
// 1 write
        String[] words = {
                "hello",
                "refer",
                "world",
                "level"
        };
        try (PrintWriter pw = new PrintWriter(
                Files.newBufferedWriter(Paths.get("/path")))) {
            Stream.of(words).forEach(pw::println);
        }
// 2 read
        Stream<String> stream = Files.lines(Paths.get("/path"));
        int length = 5;
        stream.filter(s -> s.length() == length)
                .filter(s -> s.compareToIgnoreCase(
                        new StringBuilder(s).reverse().toString()) == 0)
                .collect(Collectors.toList());

    }
    static void objectMethods() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        List<Customer> list = Customer.getAllEmps();
        Customer customer = null;

        list.stream().skip(2).limit(3).findFirst().ifPresent(out::println);

        list.stream().sorted((c1,c2) -> c1.getName().compareTo(c2.getName())).findFirst().ifPresent(out::println);
        //albo
        list.stream().sorted(Comparator.comparing(Customer::getName)).findFirst().ifPresent(out::println);

        boolean allEven = list.stream().allMatch(i -> i.getSalary() > 2);
        boolean oneEven = list.stream().anyMatch(i -> i.getSalary()  > 2);
        boolean noneMultipleOfThree = list.stream().noneMatch(i -> i.getSalary()  > 2);

        Optional<List<Integer>> optionalIntegers = list.subList(0, 2).stream().
                filter(c -> c.getName().equals("MAX")).map(Customer::getNumbers).findFirst();
        out.println(optionalIntegers.orElse(new ArrayList<>()));

        Map<String, List<Customer>> map = list.parallelStream().collect(Collectors.groupingBy(Customer::getDesignation));
        map.forEach((designation, c) -> System.out.format("designation %s: %s\n", designation, c));

        Map<Boolean, List<Customer>> mapOther = list.parallelStream().collect(Collectors.partitioningBy(c -> c.getName().contains("PI")));
        mapOther.forEach((b, s) -> System.out.format("\nBoolean %b: %s\n", b, s));

        Double avg = list.stream().collect(Collectors.averagingLong(Customer::getSalary));
        LongSummaryStatistics lss = list.stream().collect(Collectors.summarizingLong(Customer::getSalary));
        out.println(avg);
        out.println(lss);

        out.println(list.stream().map(Customer::getName).collect(Collectors.joining(",", "START ", " STOP")));

        // gdy wiemy, że unique na 100 %
        Map<String, Long> map2 = list.stream().collect(Collectors.toMap(
                Customer::getDesignation,
                Customer::getSalary));
        out.println(map2);
        // inaczej gdy są unique
        Map<Long, String> map3 = list.stream().collect(Collectors.toMap(
                Customer::getSalary,
                Customer::getDesignation,
                (value1, value2) -> value1 + "/" + value2
        ));
        out.println(map3);

        Collector<Customer, StringJoiner, String> collectors = Collector.of(
                () -> new StringJoiner("/"),
                (stringJoiner, cust) -> stringJoiner.add(cust.getName()),
                StringJoiner::merge,
                StringJoiner::toString
        );
        out.println(list.stream().collect(collectors));

//     tutaj zwraca listę a w poniższym stream Address   list.stream().map(c -> c.getAddress()).forEach(...);
        // zeby nie było null dajemy Objects::nonNull

        list.stream().filter(c -> Objects.nonNull(c.getAddress()))
                .flatMap(c -> c.getAddress().stream())
                .forEach(a -> out.println(a.getCity()));

        //reduce dobre na szukanie wartości po boolean
        list.stream().map(Customer::getSalary).distinct().reduce((s1, s2) -> s1 > s2 ? s1 : s2).ifPresent(out::println);

        // list.forEach(funkcjaPodnoszącaCoś(c));

        // .map(employeeRepository::findById).collet(...

        // empList.stream().toArray(Employee[]::new)

        // List<List<String>> list  to:
        // list.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    static void optionalExample() {
        // nie nadużywamy w getach
        // private Optional<HashMap<String, Integer>> getData() {
        //    return Optional.ofNullable(data); }
        List<Customer> list = Customer.getAllEmps();
        out.println(list.stream().findFirst().isPresent());

        Optional<Customer> empty = list.subList(1, 2).stream().filter(c -> c.getName().equals("OTHER")).findAny();
        out.println(empty.isPresent());
        // nie do końca dobre getOrElseThrowNoSuchElementException
        if (empty.isPresent()) {
            empty.get();
        }
        //lub
        out.println(list.stream().anyMatch(c -> c.getName().equals("OTHER")));

        out.println(empty.orElse(new Customer(0, "OTHER", 10L, "NIKT")));
        out.println(empty.orElseGet(() -> Customer.getAllEmps().get(0)));
//        out.println(empty.orElseThrow(() -> new NullPointerException("No")));

        Optional<Customer> customerOptional = Optional.of(new Customer(0, "OTHER", 10L, "NIKT"));
        // najlepsze zastosowanie Optional, zamiast ifPresent
        String input = "COŚ"; // null
        out.println(Optional.ofNullable(input)
                .map(String::trim)
                // lub możemu rzucić wyjątek
                .orElse("PUSTO"));

        // zamiast
        if (list.get(0) != null) {
            String name = list.get(0).getName();
            if (name != null)
                out.println(name.toUpperCase());
        }
        // to to, get 0 to wynik, 1 to null
        out.println(Optional.ofNullable(list.get(0))
                .map(Customer::getNumbers).filter(i -> i.get(0) > 3).map(String::valueOf)
                .orElse("NOT FOUND"));

    }

    static void otherMethods() {
        Thread thread = new Thread(() -> out.println("NEW THREAD"));
        thread.start();

        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);  // a1

        Stream.of("a1", "a2", "a3").filter(s -> s.startsWith("3")).findFirst().orElse("NIC");

        IntStream.range(0, 7).forEach(out::println);

        int[] table = {1, 4, -2, 0, 4};
        out.println(IntStream.of(table).allMatch(i -> i > -3));
        IntStream.of(table).sorted().forEach(out::println);
        IntStream.of(table).average().ifPresent(out::println);
        IntStream.of(table).distinct().forEachOrdered(i -> out.println("distinct " + i));
        out.println(IntStream.of(table).limit(3).boxed().peek(out::print).map(String::valueOf).filter(s -> !s.equals("5")).count());
        out.println(IntStream.of(table).mapToObj(String::valueOf).noneMatch(s -> s.equals("0")));
        // summaryStatistics
        IntSummaryStatistics ss = IntStream.of(table).summaryStatistics();
        out.println(String.format("%d , %f , %d , %d", ss.getMax(), ss.getAverage(), ss.getCount(), ss.getSum()));
    }

    static void arrayExample() throws NullPointerException {
        List<Customer> list = Customer.getAllEmps();

        list.replaceAll(c -> {
            c.setName(c.getName() + " example");
            return c;
        });
        out.println(list);

        Long sum = list.stream().map(Customer::getSalary).peek(out::println).reduce(0L, (s1, s2) -> s1 + s2);
        out.println(sum);

        list.stream().sorted(Comparator.comparingLong(Customer::getId).reversed()).forEach(out::println);
        list.stream().sorted(Comparator.comparing(c1 -> c1.getName())).forEach(out::println);
        // lub Customer::getName
    }

    static void mapExample() {
        Map<Customer, Long> map = Customer.getAllEmpsMap();

        map.replaceAll((customer, bonus) -> customer.getSalary() < 200 ? 270 : customer.getSalary());
        out.println(map);

        Optional<Long> o = map.entrySet()
                .stream()
                .filter( e -> e.getKey().getName().contains("PI"))
                .map(Map.Entry::getValue)
                .findFirst();

        map.values().forEach(System.out::println);

        map.entrySet()
                .stream()
                .filter(x -> x.getValue() > 0.5)
                .forEach(x -> System.out.printf("%s %d",
                        x.getKey(), x.getValue()));

        Set<Map.Entry<Customer, Long>> set = map.entrySet()
                .stream()
                .filter(x -> x.getKey().getName().startsWith("P"))
                .collect(Collectors.toCollection(HashSet::new));
    }

    static void biFunction(BiFunction<String, String, Integer> biFunction) {
        // interface BiFunction<T, U, R>
        // R apply(T t, U u);
        out.println(biFunction.apply("5", "22"));
    }

    static void unaryOperator(UnaryOperator<Integer> unaryOperator) {
        // interface UnaryOperator<T> extends Function<T, T>
        // T apply(T t);
        // Double,Long,Int-UnaryOperator (bez typu)
        // ma compose i andThem jak w Function
        out.println(unaryOperator.apply(10));
    }


    static void supplier(Supplier<Double> supplier) {
        //interface Supplier<T>
        // T get();
        // Double,Long,Int-Supplier (bez typu)
        out.println(supplier.get());

        Supplier<Stream<String>> supplierOther = () -> Stream.of("a", "b");
        out.println("supplier " + supplierOther.get().count());
    }

    static void predicate() {
        //public interface Predicate<T>
        // boolean test(T t);
        // Double,Long,Int-Predicate (bez typu)
        Predicate<String> firstMethod = s -> s.contains("est");
        Predicate<String> secondMethod = s -> s.length() == 4;
        out.println(firstMethod.and(secondMethod).negate().test("test"));
        Predicate<Integer> thirdMethod = Predicate.isEqual(5);
        out.println(thirdMethod.test(6));
    }

    static void binaryOperator(BinaryOperator<Double> binaryOperator) {
        // interface BinaryOperator<T> extends BiFunction<T,T,T>
        // T apply(T t1, T t2);
        // Double,Long,Int-BinaryOperator (bez typu)
        out.println(binaryOperator.apply(1.2, 1.1));
    }

    static void doubleFunction(DoubleFunction<String> doubleFunction) {
        //public interface DoubleFunction<R>
        // R apply(double value);
        out.println(doubleFunction.apply(1.7));
    }

    static void doubleConsumer() {
        // public interface DoubleConsumer
        //void accept(double value);
        DoubleConsumer d = (x) -> out.println(x * x);
        d.andThen(d).andThen(d).accept(0.5);
    }

    static void consumer(Consumer<String> consumer) {
        // public interface Consumer<T>
        //void accept(T t)
        // Double,Long,Int-Consumer<T>
        Consumer<String> lastMethod = s -> out.println(s.toUpperCase());
        consumer.andThen(lastMethod);
        consumer.accept("Ab");
    }

    static void booleanSupplier(BooleanSupplier booleanSupplier) {
        // boolean getAsBoolean()
        out.println(booleanSupplier.getAsBoolean());
    }


    @FunctionalInterface
    private interface FunctionalBasic<T> {
        boolean check(T check);

        // lub static
        default public Long returnLong() {
            return 0L;
        }
    }

    static void basicFunction() {
        FunctionalBasic<String> firstMethod = x -> x.equals("FFF");
        out.println(firstMethod.check("DDD"));
        out.println(firstMethod.check("FFF"));
    }

    static void function() {
        // public interface Function<T, R>
        //R apply(T t);
        // Double,Long,Int-Function<R>
        Function<Integer, String> firstMethod = String::valueOf;
        Function<String, Integer> secondMethod = s -> Integer.valueOf(s) * 10;
        //compose - firstMethod a potem secondMethod
        Function<Integer, Integer> composeMethod = secondMethod.compose(firstMethod);
        out.println(composeMethod.apply(5).getClass());
        //andThem - secondMethod a potem firstMethod
        Function<String, String> andThenMethod = secondMethod.andThen(firstMethod);
        out.println(andThenMethod.apply("10").getClass());
    }
}
