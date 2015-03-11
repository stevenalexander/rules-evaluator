package com.example.rules.grammar.specification;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RealArithmeticExpressionTest {

    ArithmeticExpression left = mock(ArithmeticExpression.class);
    ArithmeticExpression right = mock(ArithmeticExpression.class);

    @Test
    public void calculate_multiply() {
        when(left.calculate(anyString())).thenReturn(2f);
        when(right.calculate(anyString())).thenReturn(3f);
        assertEquals(6f, (new RealArithmeticExpression(ArithmeticOperatorType.multiply, left, right)).calculate(""), 0f);
    }

    @Test
    public void calculate_divide() {
        when(left.calculate(anyString())).thenReturn(4f);
        when(right.calculate(anyString())).thenReturn(2f);
        assertEquals(2f, (new RealArithmeticExpression(ArithmeticOperatorType.divide, left, right)).calculate(""), 0f);
    }

    @Test
    public void calculate_add() {
        when(left.calculate(anyString())).thenReturn(1f);
        when(right.calculate(anyString())).thenReturn(2f);
        assertEquals(3f, (new RealArithmeticExpression(ArithmeticOperatorType.add, left, right)).calculate(""), 0f);
    }

    @Test
    public void calculate_subtract() {
        when(left.calculate(anyString())).thenReturn(2f);
        when(right.calculate(anyString())).thenReturn(1f);
        assertEquals(1f, (new RealArithmeticExpression(ArithmeticOperatorType.subtract, left, right)).calculate(""), 0f);
    }
}
