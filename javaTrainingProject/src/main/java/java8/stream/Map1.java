package java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Map1 {
    public static void main(String[] args) {
        // map v peek

        List<Customer> customers = Customer.getAllEmps();
        List<Customer> result;

        result = customers.stream()
                .map(c -> {
                    c.setSalary(0L);
                    return c;
                }).peek(System.out::println)
                .collect(Collectors.toCollection(ArrayList::new));

        // albo tak
        result.clear();
        result = customers.stream()
//                .peek(c -> c.setSalary(4L))
                .peek(c -> {
                            c.setSalary(4L);
                            System.out.println(c);
                        }
                ).collect(Collectors.toList());

        System.out.println(result);
    }
}
