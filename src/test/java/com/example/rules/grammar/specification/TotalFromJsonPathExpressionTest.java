package com.example.rules.grammar.specification;

import com.example.rules.IJsonPathParser;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TotalFromJsonPathExpressionTest {

    IJsonPathParser jsonPathParser = mock(IJsonPathParser.class);

    @Test
    public void calculate() {
        when(jsonPathParser.queryArray("data", "expression")).thenReturn(new ArrayList<String>() {{
            add("1");
            add("2");
        }});
        assertEquals(3f, (new TotalFromJsonPathExpression(jsonPathParser, "expression")).calculate("data"), 0f);
    }
}
