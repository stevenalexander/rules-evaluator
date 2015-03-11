# Rules Evaluator

A library for evaluating rules written in a limited domain natural-ish english grammar against JSON data. Uses [ANTLR4](http://www.antlr.org/) for defining the rule grammar and parsing, [JsonPath](https://github.com/jayway/JsonPath)
for defining how to select data out of JSON.

## Usage

Include the library into your application, compile and use the RuleSetCompiler:

```
RuleSetCompiler ruleSetCompiler = new RuleSetCompiler(new JsonPathParser());
String rule = "status equals approved";
String jsonData = "{\"status\": \"approved\"}";
Boolean result = ruleSetCompiler.getCompiledRuleSet(rule).isSatisfiedBy(jsonData); // Returns true
```

To run tests:

```
./sbt test
```

After updating the grammar (RuleSet.g4) you must compile to re-generate ANTLR classes:

```
./sbt compile
```

## Introduction

While investigating how to handle complex business rules in a project a colleague of mine came up the idea for this and
I created this library as a proof of concept.

The problem its trying to solve is quite common:

* An application needs to evaluate data against a large number of complex/simple business rules
* The business rules are mostly concerned with a limited set of values within a single business domain
* The business rules need to be maintained and are updated regularly (with mostly small changes)
* The users who define and maintain the rules are non-technical and cannot code to implement rule changes

Normally a problem like this is solved by either custom code or adding a large Rules Engine product, but both of these
have a number of downsides.

Custom code disadvantages:

- Requires custom code for each business rule
- Rules cannot be changed without code release
- Rules cannot be maintained by non-technical users

Rules Engine disadvantages:

- Requires installation and maintenance of a new complex product (e.g. Drools)
- Requires developer up-skilling to use correctly
- Rules cannot be maintained by non-technical users (in practise)

Bad experiences in the past with large Rule Engine products discouraged us from using one, and in practise we would not
be needing anything like the full set of features it provides. Custom code would quickly become a maintenance nightmare,
and would add barriers between our users and the implementation.

The rules themselves were normally defined in english in documents and spreadsheets, so why not use something
that's closer to their "natural" state? The users aren't idiots, they use Excel formulas to calculate all this manually,
why couldn't we find a compromise closer to what they understood?

Enter [ANTLR](http://www.antlr.org/), an open source Java based language parser. It's used in a lot of places to convert
things from one well defined language to another, such as in Hibernate to generate SQL from HQL. You can use it to
define a grammar, generate parsers and apply them against text to validate it against the grammar and build a tree
structure that matches the elements in your grammar.

The idea was we could use ANTLR to define a limited domain English grammar for our business rules that covered
everything we needed inside our small business domain. That way we could allow users to write rules in almost natural
English that we could parse and convert to executable business rules in code. That way the users can define the rules
close to their normal way and maintain them on the system when they need to be updated.

e.g.

In our grammar we define a specification, with a rule being one or more specifications, as something like:

```
value_expr 'equals' string_comparison_value # StringEqualsComparisonSpecificationExpression
```

So when ANTLR parses the string "status equals approved", it can identify:

* "status" as the value_expr
* "approved" as the string_comparison_value
* The specification as type StringEqualsComparisonSpecificationExpression

This is can be easily parsed and used to build a Rule expression out of Java objects that can be evaluated against a
set of data (i.e. evaluating json data "{\"status\": \"approved\"}" gives true).

The grammar can be made to parse complex statements, allowing definition of complex business rules out of a
series of simple specifications in the grammar.

e.g.

```
(applicationArea / totalAvailableArea * 100 ) greater than 50 and options contains 'GRASSLAND'
```

As the rules are simply strings, they can be persisted and edited using a CRUD UI, web based or otherwise. The UI can
use knowledge of the grammar to aid users when editing rules, validating against the grammar, testing against known data
and auto-completing for valid syntax. If necessary, rules can be versioned to maintain audit trails and published to
control when they come into effect.

This approach has it's own set of disadvantages:

* Have to code business specific grammar and rule specification logic covering required rules
* Grammar cannot cover all possible scenarios without excessive code
* Requires users to learn the grammar and understand how it is applied to the data used in the system

I believe this approach is a good fit for when the set of business rules you are dealing with is well known and applied
to similar data sets, changes frequently in small repetitive ways and there's a requirement for users to be able to
quickly test and apply changes. Giving the users who understand the rules the best the ability to directly edit and test
gives them extremely useful functionality and avoids the need for defining Rule requirements documentation and long
periods of testing for each time the rules are updated.

## ImplementationDetails

I'd recommend reading up about [ANTLR](http://www.antlr.org/) before driving into the code, as you need to understand
the grammar and how it parses rules to understand how the tree builder constructs the expressions and applies data to
it.

ANTLR4 is included in the project via [sbt-antlr4](https://github.com/ihji/sbt-antlr4). The ANTLR grammar file is
located at `src/main/antlr4/RuleSet.g4` and generated ANTLR classes based on that grammar are in
`/Users/stevena/source/test/rules-evaluator/target/scala-2.11/classes/com/example/rules`. The generated parser is used
in the `RuleSetCompiler` and a listener, `RuleSetTreeBuilder`, is attached to it to react to events when parsing Rules.

`RuleSetTreeBuilder` has a number of methods that are fired when the parser enters and exits identified tokens and
labelled elements from the grammar, such as `enterRule_set` and `exitArithmeticExpressionPlus`. The logic inside these
methods build the logical rule expressions that can be applied to the data. Classes for specifications are under the
package `com.example.rules.grammar.specification`.

[JsonPath](https://github.com/jayway/JsonPath), a JSON implementation of XPath, is used to allow complex queries of the
JSON for the cases when the data being evaluated isn't simple.

e.g.

```
$.options[?(@.code=='G1')].area equals 3
SUM($.options[*].area) greater than 4
```

The grammar can be expanded to include specific business evaluations, rather than generic operations, based on knowledge
of business domain and data. This allows the grammar to be more english readable instead of generic formulas. In the
same way custom expressions to extract or process the data, e.g. `GRASS options area` instead of
`$.options[?(@.code=='G1' || @.code=='G2')].area`.

## Useful Links

* [ANTLR4 site](http://www.antlr.org/)
* [ANTLR4 book](https://pragprog.com/book/tpantlr2/the-definitive-antlr-4-reference)
* [Mathematical calculation example](https://github.com/ivanyu/logical-rules-parser-antlr)
* [Generate grammar diagrams](https://github.com/bkiers/rrd-antlr4)
* [Basic evaluator example](https://github.com/heatherjc07/business-rules)