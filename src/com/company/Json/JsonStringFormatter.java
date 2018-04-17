package com.company.Json;

import java.util.Map;

public class JsonStringFormatter implements JsonTypeFormatter<String> {
    @Override
    public String format(String s, JsonFormatter jsonFormatter, Map<String, Object> ctx) {
        return "\"" + s + "\"";
    }
}
