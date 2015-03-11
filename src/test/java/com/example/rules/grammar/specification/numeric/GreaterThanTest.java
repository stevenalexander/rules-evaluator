package com.example.rules.grammar.specification.numeric;

import com.example.rules.IJsonPathParser;
import com.example.rules.JsonPathParser;
import com.example.rules.grammar.specification.NumericConstant;
import org.junit.Test;

import static org.junit.Assert.*;

public class GreaterThanTest {

    IJsonPathParser jsonPathParser = new JsonPathParser();

    @Test
    public void isSatisfiedBy_true() {
        assertTrue((new GreaterThan(jsonPathParser, new NumericConstant(1), new NumericConstant(0))).isSatisfiedBy(""));
    }

    @Test
    public void isSatisfiedBy_false() {
        assertFalse((new GreaterThan(jsonPathParser, new NumericConstant(0), new NumericConstant(1))).isSatisfiedBy(""));
    }
}
