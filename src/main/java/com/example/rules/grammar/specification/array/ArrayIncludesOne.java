package com.example.rules.grammar.specification.array;

import com.example.rules.IJsonPathParser;
import com.example.rules.grammar.specification.CompositeSpecification;

import java.util.List;

public class ArrayIncludesOne extends CompositeSpecification {

    protected String jsonPathExpression ;
    protected List<String> values;

    public ArrayIncludesOne(IJsonPathParser jsonPathParser, String jsonPathExpression , List<String> values) {
        super(jsonPathParser);
        this.jsonPathExpression = jsonPathExpression;
        this.values = values;
    }

    public boolean isSatisfiedBy(String jsonData) {
        List<String> jsonPathExpressionResults = jsonPathParser.queryArray(jsonData, jsonPathExpression);

        for(String value : values) {
            if (jsonPathExpressionResults.contains(value)) {
                return true;
            }
        }

        return false;
    }
}
