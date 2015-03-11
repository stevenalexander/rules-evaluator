package com.example.rules.grammar.specification.bool;

import com.example.rules.IJsonPathParser;
import com.example.rules.grammar.specification.CompositeSpecification;

public class IsTrue extends CompositeSpecification {

    protected String jsonPathExpression ;

    public IsTrue(IJsonPathParser jsonPathParser, String jsonPathExpression ) {
        super(jsonPathParser);
        this.jsonPathExpression  = jsonPathExpression ;
    }

    @Override
    public boolean isSatisfiedBy(String jsonData) {
        return Boolean.parseBoolean(jsonPathParser.query(jsonData, jsonPathExpression ));
    }
}
