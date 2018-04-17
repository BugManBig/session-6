package com.company.Json;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class JsonFormatterImpl implements JsonFormatter {
    private Map<Class, JsonTypeFormatter> types = new HashMap<>();

    public JsonFormatterImpl() {
        types.put(Date.class, new JsonDateFormatter());
        types.put(GregorianCalendar.class, new JsonDateFormatter());
        types.put(Object.class, new JsonObjectFormatter());
        types.put(Integer.class, new JsonSimpleFormatter());
        types.put(Double.class, new JsonSimpleFormatter());
        types.put(String.class, new JsonStringFormatter());
        types.put(ArrayList.class, new JsonCollectionsFormatter());
        types.put(HashSet.class, new JsonCollectionsFormatter());
    }

    @Override
    public String marshall(Object obj) {
        String result = "";
        Map<String, Object> ctx = new HashMap<>();
        ctx.put("shiftCount", 0);
        ctx.put("shiftType", "    ");
        result += generateNext(obj, ctx);
        return result;
    }

    @Override
    public String generateNext(Object object, Map<String, Object> ctx) {
        if (object == null) {
            return "";
        }
        if (object.getClass().isArray()) {
            return new JsonArrayFormatter().format(object, this, ctx);
        }
        if (!types.containsKey(object.getClass())) {
            return types.get(Object.class).format(object, this, ctx);
        }
        return types.get(object.getClass()).format(object, this, ctx);
    }

    @Override
    public <T> boolean addType(Class<T> clazz, JsonTypeFormatter<T> format) {
        int typesSize = types.size();
        types.put(clazz, format);
        return types.size() > typesSize;
    }
}
