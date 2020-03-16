package calculatorexample;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

public class CalculatorTest {

    @Test
    public void shouldJoinOperations() {
        Calculator calculator = new Calculator(BigDecimal.TEN);
        calculator.execute(Operation.parse("add 2"));
        calculator.execute(Operation.parse("multiply 3"));
        BigDecimal result = calculator.compute();

        assertThat(result, is(new BigDecimal(36)));
    }

    @Test
    public void shouldJoinOtherOperations() {
        Calculator calculator = new Calculator(BigDecimal.TEN);
        calculator.execute(Operation.parse("multiply 3"));
        calculator.execute(Operation.parse("add 2"));
        BigDecimal result = calculator.compute();

        assertThat(result, is(new BigDecimal(32)));
    }

    @Test
    public void shouldHandleSingleApply() {
        Calculator calculator = new Calculator(BigDecimal.TEN);
        BigDecimal result = calculator.compute();

        assertThat(result, is(BigDecimal.TEN));
    }
}
