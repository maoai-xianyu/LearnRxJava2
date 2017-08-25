//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class GsonU {
    public GsonU() {
    }

    public static String string(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static <T> T getListByKey(String resource, String key, Type type) {
        Gson gson = new Gson();
        String value = JsonU.getString(resource, key);
        return !StringU.isEmpty(value) && !StringU.equals("[]", value.replace(" ", ""))?gson.fromJson(value, type):null;
    }

    public static <T> T getBeanByKey(String resource, String key, Type type) {
        Gson gson = new Gson();
        String value = JsonU.getString(resource, key);
        return StringU.isEmpty(value)?null:gson.fromJson(value, type);
    }

    public static <T> T convert(String str, Class<T> cls) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(str, cls);
    }

    public static <T> T convert(String str, Type type) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }
}
