package com.fenixcommunity.centralspace.utilities.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class StringToolTest {
    @Test
    public void whenSubstituted_thenCorrect() {
        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("name", "Max");
        substitutes.put("college", "PK");
        String templateString = "My name is ${name} and I am a student at the ${college}.";
        String result = StringTool.substituteTemplateString(templateString, substitutes);

        assertEquals("My name is Max and I am a student at the PK.", result);
    }

    @Test
    public void whenGetSimilaritiesOfStrings_thenCorrect() {
        assertEquals(StringTool.getSimilaritiesOfStrings("Max", "Maks"), 2);
        assertEquals(StringTool.getSimilaritiesOfStrings("LLx", "Maks"), 0);
        assertEquals(StringTool.getSimilaritiesOfStrings("aax", "Maks"), 1);
    }
}