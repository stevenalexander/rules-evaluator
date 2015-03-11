package com.example.rules.grammar.specification;

import com.example.rules.IJsonPathParser;
import com.example.rules.JsonPathParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotSpecificationTest {

    IJsonPathParser jsonPathParser = new JsonPathParser();

    ISpecification specification = mock(ISpecification.class);

    @Test
    public void isSatisfiedBy_true() {
        when(specification.isSatisfiedBy(anyString())).thenReturn(false);
        assertTrue((new NotSpecification(jsonPathParser, specification)).isSatisfiedBy(""));
    }

    @Test
    public void isSatisfiedBy_false() {
        when(specification.isSatisfiedBy(anyString())).thenReturn(true);
        assertFalse((new NotSpecification(jsonPathParser, specification)).isSatisfiedBy(""));
    }
}
