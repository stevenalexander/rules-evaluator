package com.example.rules.grammar.specification;

import com.example.rules.IJsonPathParser;

public class NumericField implements ArithmeticExpression {

    protected IJsonPathParser jsonPathParser;
    private String jsonPathExpression ;

    public NumericField(IJsonPathParser jsonPathParser, String jsonPathExpression ) {
        this.jsonPathParser = jsonPathParser;
        this.jsonPathExpression  = jsonPathExpression ;
    }

    public float calculate(String jsonData) {
        String fieldValueString = jsonPathParser.query(jsonData, jsonPathExpression);

        if (fieldValueString != null && !fieldValueString.isEmpty()) {
            return Float.parseFloat(fieldValueString);
        }

        throw new IllegalArgumentException("Unable to calculate with field name " + jsonPathExpression );
    }
}
