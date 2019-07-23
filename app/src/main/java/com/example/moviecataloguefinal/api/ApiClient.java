package com.example.moviecataloguefinal.api;

import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "7bbfe1287566337af9e03b8c19e40234";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w780";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String getLanguageKey() {
        if (Locale.getDefault().getLanguage().equals("in")) {
            return "id";
        } else {
            return "en";
        }
    }
}
