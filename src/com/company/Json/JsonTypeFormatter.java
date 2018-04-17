package com.company.Json;

import java.util.Map;

public interface JsonTypeFormatter<T> {
    String format(T t, JsonFormatter jsonFormatter, Map<String, Object> ctx);
}
