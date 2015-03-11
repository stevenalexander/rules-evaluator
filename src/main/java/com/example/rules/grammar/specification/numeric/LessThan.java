package com.example.rules.grammar.specification.numeric;

import com.example.rules.IJsonPathParser;
import com.example.rules.grammar.specification.ArithmeticExpression;

public class LessThan extends NumericSpecificationExpression {

    public LessThan(IJsonPathParser jsonPathParser, ArithmeticExpression leftArithmeticExpression, ArithmeticExpression rightArithmeticExpression) {
        super(jsonPathParser, leftArithmeticExpression , rightArithmeticExpression);
    }

    @Override
    public boolean isSatisfiedBy(String jsonData) {
        return leftArithmeticExpression.calculate(jsonData) < rightArithmeticExpression.calculate(jsonData);
    }
}
