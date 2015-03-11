package com.example.rules.grammar.specification.bool;

import com.example.rules.IJsonPathParser;
import com.example.rules.JsonPathParser;
import com.example.rules.grammar.specification.NumericConstant;
import com.example.rules.grammar.specification.numeric.GreaterThan;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsTrueTest {

    IJsonPathParser jsonPathParser = new JsonPathParser();

    @Test
    public void isSatisfiedBy_true() {
        assertTrue((new IsTrue(jsonPathParser, "flag")).isSatisfiedBy("{\"flag\": \"true\"}"));
    }

    @Test
    public void isSatisfiedBy_false() {
        assertFalse((new IsTrue(jsonPathParser, "flag")).isSatisfiedBy("{\"flag\": \"false\"}"));
    }
}
