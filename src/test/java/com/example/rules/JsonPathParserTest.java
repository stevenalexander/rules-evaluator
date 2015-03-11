package com.example.rules;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonPathParserTest {

    IJsonPathParser jsonPathParser = new JsonPathParser();

    final String sampleJson = "{\n" +
        " \"applicationArea\":9.0,\n" +
        " \"status\": \"approved\",\n" +
        " \"options\": [\n" +
        "        {\n" +
        "            \"code\": \"AB1\",\n" +
        "            \"area\": 3\n" +
        "        },\n" +
        "        {\n" +
        "            \"code\": \"AB8\",\n" +
        "            \"area\": 2\n" +
        "        }\n" +
        "  ]" +
    "}";

    @Test
    public void query_shouldReturnString() {
        assertEquals("approved", jsonPathParser.query(sampleJson, "$.status"));
    }

    @Test
    public void query_shouldReturnStringWhenArrayWithOneEntry() {
        assertEquals("AB1", jsonPathParser.query(sampleJson, "$.options[?(@.area == 3)].code"));
    }

    @Test
    public void queryObject_shouldReturnRawObject() {
        assertEquals("net.minidev.json.JSONArray", jsonPathParser.queryObject(sampleJson, "$.options[?(@.area == 3)]").getClass().getName());
    }

    @Test
    public void queryArray_shouldReturnStringArray() {
        assertEquals("{code=AB1, area=3}", jsonPathParser.queryArray(sampleJson, "$.options[*]").get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void queryArray_shouldThrowIllegalArgumentExceptionWhenNotArray() {
        jsonPathParser.queryArray(sampleJson, "$.status");
        fail("Should have exploded by now");
    }
}
