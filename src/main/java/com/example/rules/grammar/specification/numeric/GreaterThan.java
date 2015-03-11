package com.example.rules.grammar.specification.numeric;

import com.example.rules.IJsonPathParser;
import com.example.rules.grammar.specification.ArithmeticExpression;

public class GreaterThan extends NumericSpecificationExpression {

    public GreaterThan(IJsonPathParser jsonPathParser, ArithmeticExpression leftArithmeticExpression, ArithmeticExpression rightArithmeticExpression) {
        super(jsonPathParser, leftArithmeticExpression , rightArithmeticExpression);
    }

    @Override
    public boolean isSatisfiedBy(String jsonData) {
        return leftArithmeticExpression.calculate(jsonData) > rightArithmeticExpression.calculate(jsonData);
    }
}