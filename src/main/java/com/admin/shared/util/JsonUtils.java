package com.admin.shared.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static <T> String toJson(T obj) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            System.err.println("Lỗi khi chuyển object sang JSON: " + e.getMessage());
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            System.err.println("Lỗi khi parse JSON: " + e.getMessage());
            return null;
        }
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            System.err.println("Lỗi khi parse JSON (Type): " + e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> toMap(Object obj) {
        try {
            return gson.fromJson(toJson(obj), new TypeToken<Map<String, Object>>() {}.getType());
        } catch (Exception e) {
            System.err.println("Lỗi khi convert object -> Map: " + e.getMessage());
            return null;
        }
    }

    public static <T> T convertObject(Object data, Class<T> clazz) {
        try {
            String json = gson.toJson(data);
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            System.err.println("Lỗi khi convert object sang " + clazz.getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }

    public static <T> List<T> convertToList(Object data, Class<T> clazz) {
        try {
            String json = gson.toJson(data);
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.err.println("Lỗi khi convert object sang List<" + clazz.getSimpleName() + ">: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
