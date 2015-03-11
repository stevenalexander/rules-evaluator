package com.example.rules.grammar.specification;

import com.example.rules.IJsonPathParser;

public class NotSpecification extends CompositeSpecification {
    ISpecification specification;

    public NotSpecification(IJsonPathParser jsonPathParser, ISpecification specification)  {
        super(jsonPathParser);
        this.specification = specification;
    }

    public boolean isSatisfiedBy(String jsonData)   {
        return !this.specification.isSatisfiedBy(jsonData);
    }
}
