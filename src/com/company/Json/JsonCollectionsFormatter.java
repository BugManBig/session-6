package com.company.Json;

import java.util.Collection;
import java.util.Map;

public class JsonCollectionsFormatter implements JsonTypeFormatter<Collection> {
    @Override
    public String format(Collection list, JsonFormatter jsonFormatter, Map<String, Object> ctx) {
        String result = "[\n";
        OftenActions.shiftChange(ctx, 1);
        Object[] array = list.toArray();
        for (Object elem : array) {
            result += OftenActions.getTrueShift(ctx) + jsonFormatter.generateNext(elem, ctx) + ",\n";
        }
        result = OftenActions.cutLastComma(result);
        OftenActions.shiftChange(ctx, -1);
        result += OftenActions.getTrueShift(ctx) + "]";
        return result;
    }
}
