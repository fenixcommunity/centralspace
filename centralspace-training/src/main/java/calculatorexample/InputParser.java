package calculatorexample;

import java.math.BigDecimal;
import java.util.LinkedList;

public class InputParser {

    public static Calculator parse(LinkedList<String> fileLines) {
        // ostatni element LinkedList
        String lastLine = fileLines.peekLast();
        String[] tokens = lastLine.split(" ");

        if (!tokens[0].toUpperCase().equals("APPLY")) {
            throw new IllegalArgumentException("Last line (" + lastLine + ") doesn't contain APPLY instruction!");
        }

        Calculator calculator = new Calculator(new BigDecimal(tokens[1]));

        // dzielenie zakresu listy subList().stream()
        // Objects.requireNonNull(mapper);
        fileLines.subList(0, fileLines.size() - 1).stream()
                .map(Operation::parse)
                // Stream <Functional
                .forEach(calculator::execute);

        return calculator;
    }

}
