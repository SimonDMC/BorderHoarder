package com.simondmc.borderhoarder.util;

import java.util.Map;
import java.util.Objects;

public class DataTypeUtil {

    public static String joinStringArray(String[] array, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    // https://stackoverflow.com/a/2904266
    public static <T, String> T getKeyByCaseInsensitiveString(Map<T, String> map, String value) {
        for (Map.Entry<T, String> entry : map.entrySet()) {
            if (value.toString().equalsIgnoreCase((java.lang.String) entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
