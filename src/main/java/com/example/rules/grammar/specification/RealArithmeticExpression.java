package com.example.rules.grammar.specification;

public class RealArithmeticExpression implements ArithmeticExpression {
    private final ArithmeticOperatorType operator;
    private final ArithmeticExpression left;
    private final ArithmeticExpression right;

    public RealArithmeticExpression(ArithmeticOperatorType operator, ArithmeticExpression left, ArithmeticExpression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public float calculate(String jsonData) {
        switch (operator) {
            case add:      return left.calculate(jsonData) + right.calculate(jsonData);
            case subtract: return left.calculate(jsonData) - right.calculate(jsonData);
            case multiply: return left.calculate(jsonData) * right.calculate(jsonData);
            case divide:   return left.calculate(jsonData) / right.calculate(jsonData);
        }

        throw new RuntimeException("Could not calculate");
    }
}
