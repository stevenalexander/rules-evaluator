package com.example.rules.grammar.specification.string;

import com.example.rules.IJsonPathParser;
import com.example.rules.JsonPathParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringEqualsTest {

    IJsonPathParser jsonPathParser = new JsonPathParser();

    @Test
    public void isSatisfiedBy_true() {
        assertTrue((new StringContains(jsonPathParser, "code", "C1")).isSatisfiedBy("{\"code\": \"C1\"}"));
    }

    @Test
    public void isSatisfiedBy_false() {
        assertFalse((new StringContains(jsonPathParser, "code", "NOPE")).isSatisfiedBy("{\"code\": \"C1\"}"));
    }
}
