package com.autograder.utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceUtils {
    public static String readString(Class<?> clazz, String name) throws IOException {
        return readString(clazz, name, "UTF-8");
    }

    public static String readString(Class<?> clazz, String name, String encoding) throws IOException {
        URI uri;

        try {
            uri = clazz.getClassLoader().getResource(name).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = Files.readAllBytes(Paths.get(uri));

        return new String(bytes, encoding);
    }

    public static int readInteger(Class<?> clazz, String name) throws IOException {
        return Integer.parseInt(readString(clazz, name));
    }

    public static <V> V readJsonAs(Class<?> clazz, String name, Class<V> classOfT) throws IOException {
        return GsonUtils.fromJson(readString(clazz, name), classOfT);
    }

    public static <V> V readJsonAs(Class<?> clazz, String name, Type typeOfT) throws IOException {
        return GsonUtils.fromJson(readString(clazz, name), typeOfT);
    }
}
