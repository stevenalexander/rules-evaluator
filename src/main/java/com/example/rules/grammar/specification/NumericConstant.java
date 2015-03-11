package com.example.rules.grammar.specification;

public class NumericConstant implements ArithmeticExpression {
    private final float value;

    public NumericConstant(float value) {
        this.value = value;
    }

    public float calculate(String jsonData) {
        return value;
    }
}
