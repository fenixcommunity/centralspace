package other.calculator;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Use Collections.emptyList() if you want to make sure that the returned list is never modified.
//    List <String> list = Collections.emptyList();
    // Kolekcje to FIFO, dajemy 1,2,3 a wyciagamy 1,2,3
    public static void main(String[] args) throws IOException {

        File file = new File("output.txt");
        // apend false, ma usuwac poprzednia tresc
        // FileWriter, i Print aby wpisac dane do pliku
        FileWriter writer = new FileWriter(file, false);
        PrintWriter output = new PrintWriter(writer);
        output.println("add 2");
        output.println("multiply 3");
        output.println("apply 10");
        // wynik ((10 + 2) * 3) = 36
        output.close();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            reader.lines().forEach(System.out::println);
//            jak damy to, to strumien bedzie pusty bo daliśmy ForEach, gdy damy filter to bedzie ok
            // reader.lines()  to stream na Readerze
            // toCollection(LinkedList::new) bo mozna dac list ale trzeba zrobic rzutowanie
            LinkedList<String> fileLines = reader.lines().collect(Collectors.toCollection(LinkedList::new));
            Calculator calculator = InputParser.parse(fileLines);
            System.out.println(calculator.compute());
        } finally {
            file.delete();
        }


    }


}
