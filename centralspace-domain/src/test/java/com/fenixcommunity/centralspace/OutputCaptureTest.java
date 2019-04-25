package com.fenixcommunity.centralspace;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

public class OutputCaptureTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Test
    public void shouldShowStringConsoleOutput() {
        final String consoleString = "shouldShowStringConsoleOutput";
        System.out.println(consoleString);
        assertThat(capture.toString(), containsString(consoleString));
    }
}
