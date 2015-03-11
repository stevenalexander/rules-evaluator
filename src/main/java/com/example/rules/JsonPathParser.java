package com.example.rules;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonPathParser implements IJsonPathParser {

    public String query(String jsonData, String jsonPathExpression) {
        Object result = queryObject(jsonData, jsonPathExpression);

        return cleanBracketsIfSingleResultInArray(result);
    }

    public Object queryObject(String jsonData, String jsonPathExpression) {
        ReadContext readContext = getJsonContext(jsonData);
        JsonPath compiledPath = getCompiledJsonPath(jsonPathExpression);

        Object result = readContext.read(compiledPath);

        return result;
    }

    public List<String> queryArray(String jsonData, String jsonPathExpression) {
        List<String> results = new ArrayList<>();

        Object result = queryObject(jsonData, jsonPathExpression);

        if (isArrayResult(result)) {
            JSONArray resultArray = (JSONArray)result;
            for (Object resultFromArray : resultArray) {
                results.add(resultFromArray.toString());
            }

            return results;
        }

        throw new IllegalArgumentException("Invalid jsonPathExpression did not return array: " + jsonPathExpression);
    }

    private String cleanBracketsIfSingleResultInArray(Object possibleArrayResult) {
        String expressionResult = possibleArrayResult.toString();

        if (isArrayResult(possibleArrayResult)) {
            JSONArray resultArray = (JSONArray)possibleArrayResult;
            if (resultArray != null && resultArray.size() == 1) {
                expressionResult = resultArray.get(0).toString();
            }
        }

        return expressionResult;
    }

    private Boolean isArrayResult(Object possibleArrayResult) {
        return (possibleArrayResult.getClass().toString().contains("net.minidev.json.JSONArray"));
    }

    private ReadContext getJsonContext(String jsonData) {
        // TODO cache parsing JSON based on String hash to save repeat parsing
        return JsonPath.parse(jsonData);
    }

    private JsonPath getCompiledJsonPath(String jsonPathExpression) {
        // TODO cache compiled path to save performance on repeated paths
        return JsonPath.compile(jsonPathExpression);
    }
}
