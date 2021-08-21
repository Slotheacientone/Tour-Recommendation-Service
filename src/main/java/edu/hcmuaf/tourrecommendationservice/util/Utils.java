package edu.hcmuaf.tourrecommendationservice.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class Utils {
    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }

}
