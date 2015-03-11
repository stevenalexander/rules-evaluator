package com.example.rules;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import com.example.rules.grammar.*;

public class RuleSetCompiler implements IRuleSetCompiler {

    protected IJsonPathParser jsonPathParser;

    public RuleSetCompiler(IJsonPathParser jsonPathParser) {
        this.jsonPathParser = jsonPathParser;
    }

    public IRuleSet getCompiledRuleSet(String rule) {
        ANTLRInputStream input = new ANTLRInputStream(rule);
        RuleSetLexer lexer = new RuleSetLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        RuleSetParser parser = new RuleSetParser(tokens);

        RuleSetTreeBuilder ruleSetTreeBuilder = new RuleSetTreeBuilder(jsonPathParser);

        parser.addParseListener(ruleSetTreeBuilder);
        parser.setErrorHandler(new ExceptionThrowingErrorHandler());
        parser.rule_set();

        return ruleSetTreeBuilder.getRuleSet();
    }

    public String getParseTreeAsString(String rule) {
        ANTLRInputStream input = new ANTLRInputStream(rule);
        RuleSetLexer lexer = new RuleSetLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        RuleSetParser parser = new RuleSetParser(tokens);

        return parser.rule_set().toStringTree(parser);
    }
}
