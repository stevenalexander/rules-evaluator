package com.example.rules.grammar.specification.string;

import com.example.rules.IJsonPathParser;
import com.example.rules.grammar.specification.CompositeSpecification;

public abstract class StringSpecificationExpression extends CompositeSpecification {

    protected String jsonPathExpression ;
    protected String value;

    public StringSpecificationExpression(IJsonPathParser jsonPathParser, String jsonPathExpression , String value) {
        super(jsonPathParser);
        this.jsonPathExpression  = jsonPathExpression ;
        this.value = value;
    }
}
