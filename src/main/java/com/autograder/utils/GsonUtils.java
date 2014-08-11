package com.autograder.utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static Gson gson = createGson();

    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(DateUtils.ISO8601_FORMAT);

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

    public static <V> V fromFile(String path, String encoding, Class<V> classOfT) throws IOException {
        return gson.fromJson(readFile(path, encoding), classOfT);
    }

    public static <V> V fromFile(String path, String encoding, Type classOfT) throws IOException {
        return gson.fromJson(readFile(path, encoding), classOfT);
    }

    private static String readFile(String path, String encoding) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), encoding);
    }
}
