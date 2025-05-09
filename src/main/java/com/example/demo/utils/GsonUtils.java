package com.example.demo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author feigebuge
 * @email 2508020102@qq.com
 */
public class GsonUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
