package com.example.rules;

import java.util.List;

public interface IJsonPathParser {
    Object queryObject(String jsonData, String jsonPathExpression);
    String query(String jsonData, String jsonPathExpression);
    List<String> queryArray(String jsonData, String jsonPathExpression);
}
