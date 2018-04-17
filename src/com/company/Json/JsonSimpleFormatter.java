package com.company.Json;

import java.util.Map;

public class JsonSimpleFormatter implements JsonTypeFormatter<Object> {
    @Override
    public String format(Object object, JsonFormatter jsonFormatter, Map<String, Object> ctx) {
        return object.toString();
    }
}
