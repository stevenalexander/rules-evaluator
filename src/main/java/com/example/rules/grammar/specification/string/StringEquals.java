package com.example.rules.grammar.specification.string;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import com.example.rules.IJsonPathParser;

public class StringEquals extends StringSpecificationExpression {

    public StringEquals(IJsonPathParser jsonPathParser, String jsonPathExpression , String value) {
        super(jsonPathParser, jsonPathExpression , value);
    }

    @Override
    public boolean isSatisfiedBy(String jsonData) {
        return jsonPathParser.query(jsonData, jsonPathExpression).equals(value);
    }
}
