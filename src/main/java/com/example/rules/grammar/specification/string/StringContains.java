package com.example.rules.grammar.specification.string;

import com.example.rules.IJsonPathParser;

public class StringContains extends StringSpecificationExpression {

    public StringContains(IJsonPathParser jsonPathParser, String jsonPathExpression , String value) {
        super(jsonPathParser, jsonPathExpression , value);
    }

    @Override
    public boolean isSatisfiedBy(String jsonData) {
        return jsonPathParser.query(jsonData, jsonPathExpression ).contains(value);
    }
}
