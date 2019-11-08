
package other.calculator;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public enum Operation {

    ADD(BigDecimal::add),
    SUBTRACT(BigDecimal::subtract),
    MULTIPLY(BigDecimal::multiply),
    DIVIDE(BigDecimal::divide);

    //    @FunctionalInterface
//    public interface BinaryOperator<T> extends BiFunction<T,T,T> {
    private final BinaryOperator<BigDecimal> command;

    Operation(BinaryOperator<BigDecimal> command) {
        this.command = command;
    }

    // pozniejsze wywolanie wyrazenie lambda, wykonanie funkcji z opoznieniem
    // apply tzn wykonaj, tutaj na koncu
    public BigDecimal apply(BigDecimal value1, BigDecimal value2) {
        return command.apply(value1, value2);
    }

    public static Function<BigDecimal, BigDecimal> parse(String line) {
        String[] tokens = line.split(" ");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Line (" + line + ") has illegal format!");
        }
        BigDecimal operand = new BigDecimal(tokens[1]);
        // przekazujemy wyrazenie lambda dalej, opozniamy
        // zwracamy funkcje gdzie argument to BD i return to BD
        // przy compute calculator wracamy do tej funkcji
        return x -> Operation.valueOf(tokens[0].toUpperCase()).apply(x, operand);
    }

}
