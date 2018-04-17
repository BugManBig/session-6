package com.company.Json;

import java.lang.reflect.Array;
import java.util.Map;

public class JsonArrayFormatter implements JsonTypeFormatter<Object> {
    @Override
    public String format(Object object, JsonFormatter jsonFormatter, Map<String, Object> ctx) {
        String result = "[\n";
        OftenActions.shiftChange(ctx, 1);
        for (int i = 0; i < Array.getLength(object); i++) {
            result += OftenActions.getTrueShift(ctx) + jsonFormatter.generateNext(Array.get(object, i), ctx) + ",\n";
        }
        result = OftenActions.cutLastComma(result);
        OftenActions.shiftChange(ctx, -1);
        result += OftenActions.getTrueShift(ctx) + "]";
        return result;
    }
}
