package com.example.rules.grammar.specification;

import com.example.rules.IJsonPathParser;

import java.util.List;

public class TotalFromJsonPathExpression implements ArithmeticExpression {

    protected IJsonPathParser jsonPathParser;
    private String jsonPathExpression;

    public TotalFromJsonPathExpression(IJsonPathParser jsonPathParser, String jsonPathExpression ) {
        this.jsonPathParser = jsonPathParser;
        this.jsonPathExpression  = jsonPathExpression ;
    }

    public float calculate(String jsonData) {
        List<String> expressionResults = jsonPathParser.queryArray(jsonData, jsonPathExpression);

        float total = 0;
        for(String expressionResult : expressionResults) {
            total += Float.parseFloat(expressionResult);
        }

        return total;
    }
}
