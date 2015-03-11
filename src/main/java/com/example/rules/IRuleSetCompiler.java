package com.example.rules;

import com.example.rules.grammar.IRuleSet;

public interface IRuleSetCompiler {
    IRuleSet getCompiledRuleSet(String rule);
}
