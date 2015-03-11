package com.example.rules.grammar;

import com.example.rules.grammar.specification.CompositeSpecification;

import java.util.*;

public class RuleSet implements IRuleSet {
    public final List<CompositeSpecification> rules;

    public RuleSet() {
        this.rules = new ArrayList<>();
    }

    public List<CompositeSpecification> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public void addRule(CompositeSpecification rule) {
        this.rules.add(rule);
    }

    public Boolean isSatisfiedBy(String jsonData)
    {
        Boolean result = true;
        for(CompositeSpecification rule:rules)
        {
            if(!rule.isSatisfiedBy(jsonData))
            {
                result = false;
            }
        }
        return result;
    }
}
