package com.example.rules.grammar.specification;

import com.example.rules.IJsonPathParser;

public abstract class CompositeSpecification implements ISpecification {

    protected IJsonPathParser jsonPathParser;

    public CompositeSpecification(IJsonPathParser jsonPathParser) {
        this.jsonPathParser = jsonPathParser;
    }

    public abstract boolean isSatisfiedBy(String jsonData);

    public ISpecification and(ISpecification specification) {
        return new AndSpecification(jsonPathParser, this, specification);
    }

    public ISpecification or(ISpecification specification) {
        return new OrSpecification(jsonPathParser, this, specification);
    }

    public ISpecification not(ISpecification specification) {
        return new NotSpecification(jsonPathParser, specification);
    }
}
