package com.autograder.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static Gson gson = createGson();

    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        return builder.create();
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <V> V fromJson(String json, Class<V> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <V> V fromJson(String json, Type classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
