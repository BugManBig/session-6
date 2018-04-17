package com.company.Json;

import java.util.Map;

public class OftenActions {
    public static String cutLastComma(String string) {
        if (string.charAt(string.length() - 2) == ',') {
            return string.substring(0, string.length() - 2) + "\n";
        }
        return string.substring(0, string.length() - 3) + "\n";
    }

    public static String getTrueShift(Map<String, Object> ctx) {
        String result = "";
        int shiftCount = (int) ctx.get("shiftCount");
        String shiftType = (String) ctx.get("shiftType");
        for (int i = 0; i < shiftCount; i++) {
            result += shiftType;
        }
        return result;
    }

    public static void shiftChange(Map<String, Object> ctx, int changeCount) {
        int shiftCount = (int) ctx.get("shiftCount");
        ctx.remove("shiftCount");
        ctx.put("shiftCount", shiftCount + changeCount);
    }
}
