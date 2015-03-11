package com.example.rules.grammar.specification.string;

import com.example.rules.IJsonPathParser;
import com.example.rules.JsonPathParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringContainsTest {

    IJsonPathParser jsonPathParser = new JsonPathParser();

    @Test
    public void isSatisfiedBy_true() {
        assertTrue((new StringContains(jsonPathParser, "codes", "C1")).isSatisfiedBy("{\"codes\": \"C1,C2,C3\"}"));
    }

    @Test
    public void isSatisfiedBy_false() {
        assertFalse((new StringContains(jsonPathParser, "codes", "NOPE")).isSatisfiedBy("{\"codes\": \"C1,C2,C3\"}"));
    }
}
