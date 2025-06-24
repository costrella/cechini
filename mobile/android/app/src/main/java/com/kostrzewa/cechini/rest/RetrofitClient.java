package com.kostrzewa.cechini.rest;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kostrzewa.cechini.BuildConfig;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance;
    private CechiniAPI service;

    public static RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

//    //todo to remove
//    public static RetrofitClient getInstance() {
////        if (instance == null) {
//            instance = new RetrofitClient(null);
////        }
//        return instance;
//    }

    RetrofitClient(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://costrella.ovh/api/")
//                .baseUrl("http://10.0.2.2:8080/api/")

                //https://costrella.ovh/api/"
                //.baseUrl("http://194.42.111.53:8089/api/")
                //http://10.0.2.2:8080/api/
                //"http://192.168.0.104:8080/api/"
//                .baseUrl(BuildConfig.SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
                .client(
                        new OkHttpClient().newBuilder()
                                .cookieJar(new SessionCookieJar(context)).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(CechiniAPI.class);
    }

    private class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }

    public CechiniAPI getService() {
        return service;
    }
}
