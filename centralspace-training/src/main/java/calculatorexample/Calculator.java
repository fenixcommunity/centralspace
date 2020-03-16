package calculatorexample;

import java.math.BigDecimal;
import java.util.function.Function;

public class Calculator implements InterfaceUml {

    private final BigDecimal initialValue;
    private Function<BigDecimal, BigDecimal> linkedOperations = Function.identity();

    public Calculator(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    // tutaj przekazujemy do lancucha funkcji
    public void execute(Function<BigDecimal, BigDecimal> operation) {
        // tutaj przekazujemy funkcje np MULTIPLY
        linkedOperations = linkedOperations.andThen(operation);
    }

    @Override
    public BigDecimal compute() {
        return linkedOperations.apply(initialValue);
    }

}
