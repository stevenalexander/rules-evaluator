package com.example.rules.grammar;

import org.antlr.v4.runtime.misc.NotNull;
import com.example.rules.IJsonPathParser;
import com.example.rules.grammar.specification.*;
import com.example.rules.grammar.specification.array.ArrayIncludesOne;
import com.example.rules.grammar.specification.bool.IsTrue;
import com.example.rules.grammar.specification.numeric.GreaterThan;
import com.example.rules.grammar.specification.numeric.LessThan;
import com.example.rules.grammar.specification.string.StringContains;
import com.example.rules.grammar.specification.string.StringEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RuleSetTreeBuilder extends RuleSetBaseListener {

    protected IJsonPathParser jsonPathParser;

    private RuleSet ruleSet = null;

    private Stack<CompositeSpecification> specifications = new Stack<>();

    private Stack<ArithmeticExpression> leftArithmeticExpressions = new Stack<>();
    private Stack<ArithmeticExpression> rightArithmeticExpressions = new Stack<>();
    private Stack<ArithmeticExpression> looseArithmeticExpressions = new Stack<>();

    private Stack<ArithmeticExpression> arithmeticExpressions = looseArithmeticExpressions;

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public RuleSetTreeBuilder(IJsonPathParser jsonPathParser) {
        this.jsonPathParser = jsonPathParser;
    }

    @Override
    public void enterRule_set(@NotNull RuleSetParser.Rule_setContext ctx) {
        assert ruleSet == null;
        assert specifications.empty();

        this.ruleSet = new RuleSet();
    }

    @Override
    public void exitRule_set(RuleSetParser.Rule_setContext ctx) {
        while(!specifications.isEmpty()) {
            ruleSet.addRule(specifications.pop());
        }
    }

    @Override
    public void exitLogicalExpressionAnd(RuleSetParser.LogicalExpressionAndContext ctx) {
        CompositeSpecification right = specifications.pop();
        CompositeSpecification left = specifications.pop();
        this.specifications.push(new AndSpecification(jsonPathParser, left, right));
    }

    @Override
    public void exitLogicalExpressionOr(RuleSetParser.LogicalExpressionOrContext ctx) {
        CompositeSpecification right = specifications.pop();
        CompositeSpecification left = specifications.pop();
        this.specifications.push(new OrSpecification(jsonPathParser, left, right));
    }

    @Override
    public void exitLogicalExpressionNot(RuleSetParser.LogicalExpressionNotContext ctx) {
        CompositeSpecification notCompositeSpecification = specifications.pop();
        specifications.push(new NotSpecification(jsonPathParser, notCompositeSpecification));
    }

    @Override
    public void exitTotalledNumericGreaterThanComparisonSpecificationExpression(RuleSetParser.TotalledNumericGreaterThanComparisonSpecificationExpressionContext ctx) {
        ArithmeticExpression left  = new TotalFromJsonPathExpression(jsonPathParser, ctx.jsonpath_expr().getText());
        ArithmeticExpression right = (ctx.right_arithmetic_expr() != null)? getArithmeticExpression(ctx.right_arithmetic_expr()) : getArithmeticExpression(ctx.numeric_expr());

        specifications.push(new GreaterThan(jsonPathParser, left, right));
    }

    @Override
    public void exitTotalledNumericLessThanComparisonSpecificationExpression(RuleSetParser.TotalledNumericLessThanComparisonSpecificationExpressionContext ctx) {
        ArithmeticExpression left  = new TotalFromJsonPathExpression(jsonPathParser, ctx.jsonpath_expr().getText());
        ArithmeticExpression right = (ctx.right_arithmetic_expr() != null)? getArithmeticExpression(ctx.right_arithmetic_expr()) : getArithmeticExpression(ctx.numeric_expr());

        specifications.push(new LessThan(jsonPathParser, left, right));
    }

    @Override
    public void exitNumericGreaterThanComparisonSpecificationExpression(RuleSetParser.NumericGreaterThanComparisonSpecificationExpressionContext ctx) {
        ArithmeticExpression left  = (ctx.left_arithmetic_expr()  != null)? getArithmeticExpression(ctx.left_arithmetic_expr())  : getArithmeticExpression(ctx.numeric_expr(0));
        ArithmeticExpression right = (ctx.right_arithmetic_expr() != null)? getArithmeticExpression(ctx.right_arithmetic_expr()) : getArithmeticExpression(ctx.numeric_expr().get(ctx.numeric_expr().size()-1));

        specifications.push(new GreaterThan(jsonPathParser, left, right));
    }

    @Override
    public void exitNumericLessThanComparisonSpecificationExpression(RuleSetParser.NumericLessThanComparisonSpecificationExpressionContext ctx) {
        ArithmeticExpression left  = (ctx.left_arithmetic_expr()  != null)? getArithmeticExpression(ctx.left_arithmetic_expr())  : getArithmeticExpression(ctx.numeric_expr(0));
        ArithmeticExpression right = (ctx.right_arithmetic_expr() != null)? getArithmeticExpression(ctx.right_arithmetic_expr()) : getArithmeticExpression(ctx.numeric_expr().get(ctx.numeric_expr().size() - 1));

        specifications.push(new LessThan(jsonPathParser, left, right));
    }

    @Override
    public void exitStringEqualsComparisonSpecificationExpression(RuleSetParser.StringEqualsComparisonSpecificationExpressionContext ctx) {
        specifications.push(new StringEquals(jsonPathParser, getValueExpression(ctx.value_expr()), ctx.string_comparison_value().getText()));
    }

    @Override
    public void exitStringContainsComparisonSpecificationExpression(RuleSetParser.StringContainsComparisonSpecificationExpressionContext ctx) {
        specifications.push(new StringContains(jsonPathParser, getValueExpression(ctx.value_expr()), ctx.string_comparison_value().getText()));
    }

    @Override
    public void exitBooleanIsTrueComparisonSpecificationExpression(RuleSetParser.BooleanIsTrueComparisonSpecificationExpressionContext ctx) {
        specifications.push(new IsTrue(jsonPathParser, getValueExpression(ctx.value_expr())));
    }

    @Override
    public void exitBooleanIsFalseComparisonSpecificationExpression(RuleSetParser.BooleanIsFalseComparisonSpecificationExpressionContext ctx) {
        specifications.push(new NotSpecification(jsonPathParser, new IsTrue(jsonPathParser, getValueExpression(ctx.value_expr()))));
    }

    @Override
    public void exitArrayIncludesOneComparisonSpecificationExpression(RuleSetParser.ArrayIncludesOneComparisonSpecificationExpressionContext ctx) {
        specifications.push(new ArrayIncludesOne(jsonPathParser, getValueExpression(ctx.value_expr()), getArray(ctx.string_array())));
    }

    @Override
    public void enterLeft_arithmetic_expr(RuleSetParser.Left_arithmetic_exprContext ctx) {
        leftArithmeticExpressions.clear();
        arithmeticExpressions = leftArithmeticExpressions;
    }

    @Override
    public void exitLeft_arithmetic_expr(RuleSetParser.Left_arithmetic_exprContext ctx) {
        arithmeticExpressions = looseArithmeticExpressions;
    }

    @Override
    public void enterRight_arithmetic_expr(RuleSetParser.Right_arithmetic_exprContext ctx) {
        rightArithmeticExpressions.clear();
        arithmeticExpressions = rightArithmeticExpressions;
    }

    @Override
    public void exitRight_arithmetic_expr(RuleSetParser.Right_arithmetic_exprContext ctx) {
        arithmeticExpressions = looseArithmeticExpressions;
    }

    @Override
    public void exitTotalledJsonPathExpression(RuleSetParser.TotalledJsonPathExpressionContext ctx) {
        this.arithmeticExpressions.push(new TotalFromJsonPathExpression(jsonPathParser, ctx.total_expr().jsonpath_expr().getText()));
    }

    @Override
    public void exitJsonPathExpression(RuleSetParser.JsonPathExpressionContext ctx) {
        this.arithmeticExpressions.push(new NumericField(jsonPathParser, ctx.getText()));
    }

    @Override
    public void exitNumericConstant(RuleSetParser.NumericConstantContext ctx) {
        this.arithmeticExpressions.push(new NumericConstant(Float.parseFloat(ctx.getText())));
    }

    @Override
    public void exitArithmeticExpressionMult(@NotNull RuleSetParser.ArithmeticExpressionMultContext ctx) {
        exitRealArithmeticExpression(ArithmeticOperatorType.multiply);
    }

    @Override
    public void exitArithmeticExpressionDiv(@NotNull RuleSetParser.ArithmeticExpressionDivContext ctx) {
        exitRealArithmeticExpression(ArithmeticOperatorType.divide);
    }

    @Override
    public void exitArithmeticExpressionPlus(@NotNull RuleSetParser.ArithmeticExpressionPlusContext ctx) {
        exitRealArithmeticExpression(ArithmeticOperatorType.add);
    }

    @Override
    public void exitArithmeticExpressionMinus(@NotNull RuleSetParser.ArithmeticExpressionMinusContext ctx) {
        exitRealArithmeticExpression(ArithmeticOperatorType.subtract);
    }

    protected List<String> getArray(RuleSetParser.String_arrayContext string_array) {
        String arrayString = string_array.getText()
            .replace("(", "")
            .replace(")","");

        return Arrays.asList(arrayString.split("\\s*,\\s*"));
    }

    protected ArithmeticExpression getArithmeticExpression(RuleSetParser.Left_arithmetic_exprContext left_arithmetic_exprContext) {
        if (leftArithmeticExpressions.size() == 1) {
            return leftArithmeticExpressions.pop();
        }
        throw new IllegalArgumentException("Could not determine ArithmeticExpression from arithmetic_exprContext: " + left_arithmetic_exprContext.getText());
    }

    protected ArithmeticExpression getArithmeticExpression(RuleSetParser.Right_arithmetic_exprContext right_arithmetic_exprContext) {
        if (rightArithmeticExpressions.size() == 1) {
            return rightArithmeticExpressions.pop();
        }
        throw new IllegalArgumentException("Could not determine ArithmeticExpression from arithmetic_exprContext: " + right_arithmetic_exprContext.getText());
    }

    protected ArithmeticExpression getArithmeticExpression(RuleSetParser.Numeric_exprContext numeric_exprContext) {
        if (looseArithmeticExpressions.size() > 0) {
            return looseArithmeticExpressions.remove(0);
        }
        throw new IllegalArgumentException("Could not determine ArithmeticExpression from numeric_exprContext: " + numeric_exprContext.getText());
    }

    protected String getValueExpression(RuleSetParser.Value_exprContext value_expr) {
        if (value_expr.jsonpath_expr() != null) {
            return getJsonPathExpression(value_expr.jsonpath_expr());
        }

        return value_expr.getText();
    }

    protected String getJsonPathExpression(RuleSetParser.Jsonpath_exprContext jsonpath_expr) {
        return jsonpath_expr.getText();
    }

    protected void exitRealArithmeticExpression(ArithmeticOperatorType operator) {
        ArithmeticExpression right = this.arithmeticExpressions.pop();
        ArithmeticExpression left = this.arithmeticExpressions.pop();
        RealArithmeticExpression expr = new RealArithmeticExpression(operator, left, right);
        this.arithmeticExpressions.push(expr);
    }
}
