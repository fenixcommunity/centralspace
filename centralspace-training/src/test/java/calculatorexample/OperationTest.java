package calculatorexample;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

public class OperationTest {

    @Test
    public void shouldBeAbleToAdd() {
        assertThat(Operation.ADD.apply(BigDecimal.TEN, BigDecimal.ONE), is(new BigDecimal(11)));
    }

    @Test
    public void shouldBeAbleToSubtract() {
        assertThat(Operation.SUBTRACT.apply(BigDecimal.TEN, BigDecimal.ONE), is(new BigDecimal(9)));
    }

    @Test
    public void shouldBeAbleToMultiply() {
        assertThat(Operation.MULTIPLY.apply(BigDecimal.TEN, new BigDecimal(2)), is(new BigDecimal(20)));
    }

    @Test
    public void shouldBeAbleToDivide() {
        assertThat(Operation.DIVIDE.apply(BigDecimal.TEN, new BigDecimal(2)), is(new BigDecimal(5)));
    }

    @Test
    public void shouldParseDivision() {
        Function<BigDecimal, BigDecimal> curriedFunction = Operation.parse("divide 10");
        BigDecimal result = curriedFunction.apply(new BigDecimal(20));
        assertThat(result, is(new BigDecimal(2)));
    }

    @Test
    public void shouldParseMultiplication() {
        Function<BigDecimal, BigDecimal> curriedFunction = Operation.parse("MULTIPLY 10");
        BigDecimal result = curriedFunction.apply(new BigDecimal(20));
        assertThat(result, is(new BigDecimal(200)));
    }

    @Test
    public void shouldParseSubtraction() {
        Function<BigDecimal, BigDecimal> curriedFunction = Operation.parse("SUBTRACT 5");
        BigDecimal result = curriedFunction.apply(new BigDecimal(15));
        assertThat(result, is(new BigDecimal(10)));
    }

    @Test
    public void shouldParseAddition() {
        Function<BigDecimal, BigDecimal> curriedFunction = Operation.parse("add 5");
        BigDecimal result = curriedFunction.apply(new BigDecimal(15));
        assertThat(result, is(new BigDecimal(20)));
    }


}
