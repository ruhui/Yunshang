package com.shidai.yunshang.networks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.shidai.yunshang.managers.ZZCService;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;

/**
 * 描述：调用接口初始化API
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/10 13:57
 **/
public class ApiClient {
    /* 测试URL */
    public static final String BASE_URL_TEST = "http://47.89.48.28:6001/";
    /* 超时时间*/
    private static final int DEFAULT_TIMEOUT = 5;

    private HashMap<String,  Subscription> hashMap = new HashMap<>();

    private Retrofit retrofit;
    private ZZCService apiService;

    //构造方法私有
    private ApiClient() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        httpClientBuilder.addInterceptor()

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL_TEST)
                .build();

        apiService = retrofit.create(ZZCService.class);
    }


    public Gson buildGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                .registerTypeAdapter(String.class, new IntegerDefault0Adapter())
//                .registerTypeAdapter(String.class, new IntegerDefault0Adapter())
                .create();
        return gson;
    }

    public static ZZCService getApiService() {

        return ApiClientHolder.INSTANCE.apiService;
    }


    private static class ApiClientHolder {
        public static ApiClient INSTANCE = new ApiClient();
    }


    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

}
