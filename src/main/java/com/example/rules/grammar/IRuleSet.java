package com.example.rules.grammar;

import com.example.rules.grammar.specification.CompositeSpecification;

import java.util.List;

public interface IRuleSet {
    List<CompositeSpecification> getRules();
    void addRule(CompositeSpecification rule);
    Boolean isSatisfiedBy(String jsonData);
}
