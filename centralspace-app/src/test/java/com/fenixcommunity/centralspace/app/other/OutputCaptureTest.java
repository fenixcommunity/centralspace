package com.fenixcommunity.centralspace.app.other;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

class OutputCaptureTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Test
    void shouldShowStringConsoleOutput() {
        final String consoleString = "shouldShowStringConsoleOutput";
        System.out.println(consoleString);
        assertThat(capture.toString()).contains(consoleString);
    }
}
