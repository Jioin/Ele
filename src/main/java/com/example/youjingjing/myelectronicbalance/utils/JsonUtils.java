package com.example.youjingjing.myelectronicbalance.utils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by youjingjing on 2017/7/6.
 */

public class JsonUtils {

    private static Gson mGson = new Gson();

    /*将对象转换为json字符串*/
    public static <T> String serialize(T object)throws JsonSyntaxException {
        return mGson.toJson(object);
    }

    /*将json字符串转换为对象*/
    public static <T> T deserialize(String json,Class<T> cls)throws JsonSyntaxException{
        return mGson.fromJson(json,cls);
    }

    /*将json对象转换为实体对象*/
    public static <T> T deserialize(JsonObject json, Class<T> cls)throws JsonSyntaxException{
        return mGson.fromJson(json,cls);
    }
    /*将json字符串转换为对象*/
    public static <T> T deserialize(String json,Type type) throws JsonSyntaxException{
        return mGson.fromJson(json,type);
    }
}
