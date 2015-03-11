grammar RuleSet;

rule_set : single_rule (NEWLINE single_rule)* EOF ;

single_rule : logical_expr RULECOMMENT? ;

logical_expr : logical_expr 'and' logical_expr # LogicalExpressionAnd
             | logical_expr 'or' logical_expr  # LogicalExpressionOr
             | 'not' logical_expr              # LogicalExpressionNot
             | specification_expr              # SpecificationExpression
             ;

specification_expr :     'SUM(' jsonpath_expr ') greater than (' right_arithmetic_expr ')' # TotalledNumericGreaterThanComparisonSpecificationExpression
                   |     'SUM(' jsonpath_expr ') greater than ' numeric_expr               # TotalledNumericGreaterThanComparisonSpecificationExpression

                   | '(' left_arithmetic_expr ') greater than (' right_arithmetic_expr ')' # NumericGreaterThanComparisonSpecificationExpression
                   | '(' left_arithmetic_expr ') greater than ' numeric_expr               # NumericGreaterThanComparisonSpecificationExpression
                   |              numeric_expr ' greater than ' numeric_expr               # NumericGreaterThanComparisonSpecificationExpression
                   |              numeric_expr ' greater than (' right_arithmetic_expr ')' # NumericGreaterThanComparisonSpecificationExpression
                   |              numeric_expr ' greater than ' numeric_expr               # NumericGreaterThanComparisonSpecificationExpression

                   |     'SUM(' jsonpath_expr ') less than (' right_arithmetic_expr ')' # TotalledNumericLessThanComparisonSpecificationExpression
                   |     'SUM(' jsonpath_expr ') less than ' numeric_expr               # TotalledNumericLessThanComparisonSpecificationExpression

                   | '(' left_arithmetic_expr ') less than (' right_arithmetic_expr ')' # NumericLessThanComparisonSpecificationExpression
                   | '(' left_arithmetic_expr ') less than ' numeric_expr               # NumericLessThanComparisonSpecificationExpression
                   |              numeric_expr ' less than (' right_arithmetic_expr ')' # NumericLessThanComparisonSpecificationExpression
                   |              numeric_expr ' less than ' numeric_expr               # NumericLessThanComparisonSpecificationExpression

                   | value_expr 'equals' string_comparison_value    # StringEqualsComparisonSpecificationExpression
                   | value_expr 'contains' string_comparison_value  # StringContainsComparisonSpecificationExpression

                   | value_expr 'is true'                           # BooleanIsTrueComparisonSpecificationExpression
                   | value_expr 'is false'                          # BooleanIsFalseComparisonSpecificationExpression

                   | value_expr 'includes one' string_array         # ArrayIncludesOneComparisonSpecificationExpression
                   ;

left_arithmetic_expr : arithmetic_expr;
right_arithmetic_expr : arithmetic_expr;

arithmetic_expr
 : arithmetic_expr '*' arithmetic_expr # ArithmeticExpressionMult
 | arithmetic_expr '/' arithmetic_expr # ArithmeticExpressionDiv
 | arithmetic_expr '+' arithmetic_expr # ArithmeticExpressionPlus
 | arithmetic_expr '-' arithmetic_expr # ArithmeticExpressionMinus
 | numeric_expr                        # ArithmeticExpressionNumericEntity
 ;

numeric_expr : total_expr    # TotalledJsonPathExpression
             | jsonpath_expr # JsonPathExpression
             | IDENTIFIER    # JsonPathExpression
             | NUMERIC_VALUE # NumericConstant
             | INT           # NumericConstant
             ;

value_expr : jsonpath_expr
           | IDENTIFIER
           ;

total_expr : 'SUM(' jsonpath_expr ')' ;

jsonpath_expr : jsonpath_dotnotation_expr
              ;

// This is standard JsonPath using Dot notation
jsonpath_dotnotation_expr : '$.' dotnotation_expr ('.' dotnotation_expr)* ;

dotnotation_expr : identifierWithQualifier
                 | IDENTIFIER
                 ;

identifierWithQualifier : IDENTIFIER '[]'
                        | IDENTIFIER '[*]'
                        | IDENTIFIER '[' INT ']'
                        | IDENTIFIER '[?(' query_expr ')]'
                        ;

query_expr : query_expr ('&&' query_expr)+
           | query_expr ('||' query_expr)+
           | '@.' IDENTIFIER
           | '@.' IDENTIFIER '>' INT
           | '@.' IDENTIFIER '<' INT
           | '@.length-' INT
           | '@.' IDENTIFIER '==' INT
           | '@.' IDENTIFIER '==\'' IDENTIFIER '\''
           ;

string_comparison_value  : INT | NUMERIC_VALUE | IDENTIFIER ;

string_array : '(' IDENTIFIER (',' IDENTIFIER)* ')' ;


INT           : '0' | [1-9][0-9]* ;
NUMERIC_VALUE : '-'?[0-9]+('.'[0-9]+)? ;
IDENTIFIER    : [a-zA-Z_][a-zA-Z_0-9]* ;
RULECOMMENT   : '#' ~[\r\n]*;
NEWLINE       : '\r'? '\n';

WS : [ \r\t\u000C\n]+ -> skip ;