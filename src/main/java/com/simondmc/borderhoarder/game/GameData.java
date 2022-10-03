package com.simondmc.borderhoarder.game;

import com.simondmc.borderhoarder.data.DataHandler;

import java.util.HashMap;
import java.util.Map;

public class GameData {

    private static final Map<String, Object> data = new HashMap<>();

    public static Map<String, Object> getAllData() {
        return data;
    }

    public static void set(String key, Object value) {
        data.put(key, value);
        DataHandler.saveData();
    }

    public static boolean getBoolean(String key) {
        return data.containsKey(key) && data.get(key) instanceof Boolean && (boolean) data.get(key);
    }

    public static int getInteger(String key) {
        return data.containsKey(key) && data.get(key) instanceof Integer ? (int) data.get(key) : 0;
    }

    public static String getString(String key) {
        return data.containsKey(key) && data.get(key) instanceof String ? (String) data.get(key) : "";
    }

    public static double getDouble(String key) {
        return data.containsKey(key) && data.get(key) instanceof Double ? (double) data.get(key) : 0;
    }

    public static void clear() {
        data.clear();
        DataHandler.saveData();
    }
}
