package com.example.rules.grammar.specification.numeric;

import com.example.rules.IJsonPathParser;
import com.example.rules.grammar.specification.ArithmeticExpression;
import com.example.rules.grammar.specification.CompositeSpecification;

public abstract class NumericSpecificationExpression extends CompositeSpecification {

    protected ArithmeticExpression leftArithmeticExpression;
    protected ArithmeticExpression rightArithmeticExpression;

    public NumericSpecificationExpression(IJsonPathParser jsonPathParser, ArithmeticExpression leftArithmeticExpression , ArithmeticExpression rightArithmeticExpression) {
        super(jsonPathParser);
        this.leftArithmeticExpression  = leftArithmeticExpression;
        this.rightArithmeticExpression = rightArithmeticExpression;
    }
}
