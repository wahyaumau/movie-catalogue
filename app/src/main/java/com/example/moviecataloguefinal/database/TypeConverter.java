package com.example.moviecataloguefinal.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class TypeConverter {
    @android.arch.persistence.room.TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @android.arch.persistence.room.TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @android.arch.persistence.room.TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @android.arch.persistence.room.TypeConverter
    public static String fromArrayListString(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);

    }
}
