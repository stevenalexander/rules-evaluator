package com.example.rules.grammar.specification;

import com.example.rules.IJsonPathParser;
import com.example.rules.JsonPathParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrSpecificationTest {

    IJsonPathParser jsonPathParser = new JsonPathParser();

    ISpecification left = mock(ISpecification.class);
    ISpecification right = mock(ISpecification.class);

    @Test
    public void isSatisfiedBy_true() {
        when(left.isSatisfiedBy(anyString())).thenReturn(true);
        when(right.isSatisfiedBy(anyString())).thenReturn(true);
        assertTrue((new OrSpecification(jsonPathParser, left, right)).isSatisfiedBy(""));
    }

    @Test
    public void isSatisfiedBy_oneFalse() {
        when(left.isSatisfiedBy(anyString())).thenReturn(false);
        when(right.isSatisfiedBy(anyString())).thenReturn(true);
        assertTrue((new OrSpecification(jsonPathParser, left, right)).isSatisfiedBy(""));
    }

    @Test
    public void isSatisfiedBy_allFalse() {
        when(left.isSatisfiedBy(anyString())).thenReturn(false);
        when(right.isSatisfiedBy(anyString())).thenReturn(false);
        assertFalse((new OrSpecification(jsonPathParser, left, right)).isSatisfiedBy(""));
    }
}
